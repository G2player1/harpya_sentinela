import asyncio
import json
from gmqtt import Client as MQTTClient
from aiokafka import AIOKafkaProducer
import numpy as np
import scipy.signal as signal
from concurrent.futures import ThreadPoolExecutor

BROKER_HOST = 'mosquitto'
BROKER_PORT = 1883
MQTT_TOPIC = 'esp32/health'
KAFKA_TOPIC = 'health.data'
KAFKA_BOOTSTRAP = 'kafka:9092'

SAMPLE_SIZE = 100
WINDOW_SIZE = 5

executor = ThreadPoolExecutor(max_workers=4)

class Processor:
    def __init__(self, kafka_producer):
        self.kafka_producer = kafka_producer
        self.reset_buffers()
        self.bpm_window, self.spo2_window, self.rmssd_window, self.sdnn_window = [], [], [], []

    def reset_buffers(self):
        self.ecg_buffer = []
        self.red_raw = []
        self.ir_raw = []
        self.wearable_id_buffer = []
        self.temp_buffer = []
        self.fall_buffer = []
        self.timestamp_buffer = []

    def bandpass_filter(self, signal_data, fs=100.0, lowcut=0.5, highcut=40.0, order=4):
        nyq = 0.5 * fs
        low = lowcut / nyq
        high = highcut / nyq
        b, a = signal.butter(order, [low, high], btype='band')
        return signal.filtfilt(b, a, signal_data)

    def detect_peaks(self, ecg):
        peaks, _ = signal.find_peaks(ecg, distance=20, height=np.mean(ecg) + np.std(ecg))
        return peaks

    def calcular_spo2(self, red_signal, ir_signal):
        red_mean = np.mean(red_signal)
        ir_mean = np.mean(ir_signal)
        ac_red = np.mean(np.abs(red_signal - red_mean))
        ac_ir = np.mean(np.abs(ir_signal - ir_mean))
        dc_red = np.mean(red_signal)
        dc_ir = np.mean(ir_signal)
        R = (ac_red / dc_red) / (ac_ir / dc_ir)
        spo2 = 110.0 - 25.0 * R
        return np.clip(spo2, 0, 100)

    def avaliar_qualidade(self, beat_count, amplitude, max_rr, min_rr, rmssd):
        if beat_count < 4 or amplitude < 200 or (max_rr - min_rr) > 1.0:
            return "ruim"
        elif amplitude < 600 or rmssd > 0.3:
            return "moderado"
        else:
            return "bom"

    def verificar_consistencia(self, bpm, spo2, rmssd, sdnn, amplitude, qualidade):
        inconsistencias = []
        if spo2 < 90 and qualidade == "ruim":
            inconsistencias.append("SpO2 baixo com qualidade ruim")
        if bpm > 120 and rmssd > 0.3:
            inconsistencias.append("BPM alto com HRV elevado")
        if amplitude < 200 and bpm > 60 and qualidade == "moderado":
            inconsistencias.append("Amplitude baixa mas BPM normal")
        if sdnn > 0.5 and spo2 < 92:
            inconsistencias.append("SDNN alto com SpO2 baixo")
        return "inconsistente: " + "; ".join(inconsistencias) if inconsistencias else "consistente"

    def processar_amostras(self, dados):
        # Mesma lógica de adicionar buffers e processar quando atingir SAMPLE_SIZE
        self.ecg_buffer += dados.get('ecg', [])
        self.red_raw += dados.get('red', [])
        self.ir_raw += dados.get('ir', [])
        self.wearable_id_buffer += dados.get('wearable_id', [])
        self.temp_buffer += dados.get('temp', [])
        self.fall_buffer += dados.get('fall', [])
        self.timestamp_buffer += dados.get('timestamp', [])

        if len(self.ecg_buffer) < SAMPLE_SIZE:
            return []  # ainda não tem dados suficientes

        # Preparar dados para processamento
        ecg_array = self.bandpass_filter(np.array(self.ecg_buffer[:SAMPLE_SIZE]))
        red_array = np.array(self.red_raw[:SAMPLE_SIZE])
        ir_array = np.array(self.ir_raw[:SAMPLE_SIZE])

        peaks = self.detect_peaks(ecg_array)

        rr_intervals = []
        for i in range(1, len(peaks)):
            rr = (peaks[i] - peaks[i - 1]) / 100.0
            rr_intervals.append(rr)

        beat_count = len(rr_intervals)
        rr_mean = np.mean(rr_intervals) if beat_count > 0 else 0
        rmssd = np.sqrt(np.mean(np.diff(rr_intervals) ** 2)) if beat_count > 1 else 0
        sdnn = np.std(rr_intervals) if beat_count > 1 else 0
        bpm = 60.0 / rr_mean if rr_mean > 0 else 0
        amplitude = np.max(ecg_array) - np.min(ecg_array)
        spo2 = self.calcular_spo2(red_array, ir_array)

        qualidade = self.avaliar_qualidade(
            beat_count, amplitude,
            max(rr_intervals, default=0), min(rr_intervals, default=0), rmssd
        )

        self.bpm_window.append(bpm)
        self.spo2_window.append(spo2)
        self.rmssd_window.append(rmssd)
        self.sdnn_window.append(sdnn)

        if len(self.bpm_window) > WINDOW_SIZE:
            self.bpm_window.pop(0)
            self.spo2_window.pop(0)
            self.rmssd_window.pop(0)
            self.sdnn_window.pop(0)

        consistencia = self.verificar_consistencia(bpm, spo2, rmssd, sdnn, amplitude, qualidade)

        frame_count = min(SAMPLE_SIZE, len(self.wearable_id_buffer), len(self.temp_buffer),
                          len(self.fall_buffer), len(self.timestamp_buffer))

        frames = []
        for i in range(frame_count):
            frame_json = {
                "wearable_id": self.wearable_id_buffer[i],
                "rawRedPPG": float(self.red_raw[i]),
                "rawIRPPG": float(self.ir_raw[i]),
                "rawECG": float(self.ecg_buffer[i]),
                "heartRate": bpm,
                "spo2": spo2,
                "hrvSDNN": sdnn,
                "hrvRMSSD": rmssd,
                "fallDetected": self.fall_buffer[i],
                "skinTemp": self.temp_buffer[i],
                "timestamp": self.timestamp_buffer[i],
                "amplitude": amplitude,
                "quality": qualidade,
                "consistence": consistencia
            }
            frames.append(frame_json)

        # Limpar buffers
        self.ecg_buffer = self.ecg_buffer[SAMPLE_SIZE:]
        self.red_raw = self.red_raw[SAMPLE_SIZE:]
        self.ir_raw = self.ir_raw[SAMPLE_SIZE:]
        self.wearable_id_buffer = self.wearable_id_buffer[frame_count:]
        self.temp_buffer = self.temp_buffer[frame_count:]
        self.fall_buffer = self.fall_buffer[frame_count:]
        self.timestamp_buffer = self.timestamp_buffer[frame_count:]

        return frames

async def main():
    producer = AIOKafkaProducer(bootstrap_servers=KAFKA_BOOTSTRAP)
    await producer.start()
    processor = Processor(kafka_producer=producer)

    client = MQTTClient("python-health-consumer")

    async def on_connect(client, flags, rc, properties):
        print("Conectado MQTT")
        client.subscribe(MQTT_TOPIC, qos=2)

    async def on_message(client, topic, payload, qos, properties):
        try:
            dados = json.loads(payload.decode())
            # Processar em thread pool para não bloquear event loop
            frames = await asyncio.get_event_loop().run_in_executor(executor, processor.processar_amostras, dados)

            # Enviar frames para Kafka assincronamente
            for frame in frames:
                await producer.send_and_wait(KAFKA_TOPIC, json.dumps(frame).encode())

            if frames:
                print(f"{len(frames)} frames enviados para Kafka")
        except Exception as e:
            print("Erro:", e)

    client.on_connect = on_connect
    client.on_message = on_message

    await client.connect(BROKER_HOST, BROKER_PORT)
    await asyncio.Future()  # loop infinito

if __name__ == "__main__":
    asyncio.run(main())
