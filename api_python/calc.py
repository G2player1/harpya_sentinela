import json
import time
from paho.mqtt import client as mqtt_client
import numpy as np
import scipy.signal as signal
from kafka import KafkaProducer
import kafka.errors

SAMPLE_SIZE = 100
WINDOW_SIZE = 5

class Processor:
    def __init__(self):
        self.ecg_buffer = []
        self.red_raw = []
        self.ir_raw = []
        self.wearable_id_buffer = []
        self.temp_buffer = []
        self.fall_buffer = []
        self.timestamp_buffer = []

        self.rr_intervals = []
        self.bpm_window = []
        self.spo2_window = []
        self.rmssd_window = []
        self.sdnn_window = []

        # Retry para conexão com Kafka
        for attempt in range(10):
            try:
                self.producer = KafkaProducer(
                    bootstrap_servers='kafka:9092',
                    value_serializer=lambda v: json.dumps(v).encode('utf-8')
                )
                print("Conectado ao Kafka!")
                break
            except kafka.errors.NoBrokersAvailable:
                print(f"[Kafka] Broker não disponível. Tentativa {attempt + 1}/10...")
                time.sleep(5)
        else:
            raise Exception("Falha ao conectar ao Kafka após várias tentativas.")

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

    def processar_dados(self, dados):
        self.ecg_buffer += dados.get('ecg', [])
        self.red_raw += dados.get('red', [])
        self.ir_raw += dados.get('ir', [])
        self.wearable_id_buffer += dados.get('wearable_id', [])
        self.temp_buffer += dados.get('temp', [])
        self.fall_buffer += dados.get('fall', [])
        self.timestamp_buffer += dados.get('timestamp', [])

        if len(self.ecg_buffer) >= SAMPLE_SIZE:
            ecg_array = self.bandpass_filter(np.array(self.ecg_buffer[:SAMPLE_SIZE]))
            red_array = np.array(self.red_raw[:SAMPLE_SIZE])
            ir_array = np.array(self.ir_raw[:SAMPLE_SIZE])

            peaks = self.detect_peaks(ecg_array)

            self.rr_intervals.clear()
            for i in range(1, len(peaks)):
                rr = (peaks[i] - peaks[i - 1]) / 100.0
                self.rr_intervals.append(rr)

            beat_count = len(self.rr_intervals)
            rr_mean = np.mean(self.rr_intervals) if beat_count > 0 else 0
            rmssd = np.sqrt(np.mean(np.diff(self.rr_intervals) ** 2)) if beat_count > 1 else 0
            sdnn = np.std(self.rr_intervals) if beat_count > 1 else 0
            bpm = 60.0 / rr_mean if rr_mean > 0 else 0
            amplitude = np.max(ecg_array) - np.min(ecg_array)
            spo2 = self.calcular_spo2(red_array, ir_array)

            qualidade = self.avaliar_qualidade(
                beat_count, amplitude,
                max(self.rr_intervals, default=0), min(self.rr_intervals, default=0), rmssd
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
                self.producer.send('health.data', frame_json)

            # Limpa os buffers processados
            self.ecg_buffer = self.ecg_buffer[SAMPLE_SIZE:]
            self.red_raw = self.red_raw[SAMPLE_SIZE:]
            self.ir_raw = self.ir_raw[SAMPLE_SIZE:]
            self.wearable_id_buffer = self.wearable_id_buffer[frame_count:]
            self.temp_buffer = self.temp_buffer[frame_count:]
            self.fall_buffer = self.fall_buffer[frame_count:]
            self.timestamp_buffer = self.timestamp_buffer[frame_count:]

            print(f"{frame_count} frames enviados via Kafka — Consistência: {consistencia}")

def on_connect(client, userdata, flags, rc, properties=None):
    if rc == 0:
        print("Conectado ao broker MQTT com sucesso")
        client.subscribe("esp32/health", qos=2)
    else:
        print(f"Falha na conexão MQTT, código: {rc}")

def on_message(client, userdata, msg, properties=None):
    try:
        dados = json.loads(msg.payload.decode())
        userdata.processar_dados(dados)
    except Exception as e:
        print("Erro no processamento MQTT:", e)

def main():
    processor = Processor()

    client = mqtt_client.Client(
        client_id="PythonHealthConsumer",
        userdata=processor,
        protocol=mqtt_client.MQTTv5,
        transport="tcp"
    )

    client.username_pw_set(username="usuario", password="Enos123#")
    client.on_connect = on_connect
    client.on_message = on_message

    # 🛠️ Ajuste de host para Mosquitto
    client.connect("mosquitto", 1883, keepalive=60)
    client.loop_forever()

if __name__ == "__main__":
    main()
