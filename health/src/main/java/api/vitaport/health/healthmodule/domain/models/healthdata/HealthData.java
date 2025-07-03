package api.vitaport.health.healthmodule.domain.models.healthdata;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "health_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HealthData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "raw_red_ppg", nullable = false)
    private Double rawRedPPG;
    @Column(name = "raw_ir_ppg", nullable = false)
    private Double rawIRPPG;
    @Column(name = "raw_ecg", nullable = false)
    private Double rawECG;
    @Column(name = "heart_rate", nullable = false)
    private Double heartRate;
    @Column(name = "spo2", nullable = false)
    private Double spo2;
    @Column(name = "hrv_rmssd", nullable = false)
    private Double hrvRMSSD;
    @Column(name = "hrv_sdnn", nullable = false)
    private Double hrvSDNN;
    @Column(name = "fall_detected", nullable = false)
    private Boolean fallDetected;
    @Enumerated(EnumType.STRING)
    @Column(name = "stress_level", nullable = false)
    private StressLevel stressLevel;
    @Column(name = "skin_temperature", nullable = false)
    private Double skinTemp;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "amplitude", nullable = false)
    private Double amplitude;
    @Enumerated(EnumType.STRING)
    @Column(name = "quality", nullable = false)
    private DataQuality quality;
    @Column(name = "consistence", nullable = false)
    private String consistenceMessage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Employee employee;

    public HealthData(Employee employee, Double rawRedPPG, Double rawIRPPG, Double rawECG,
                      Double heartRate, Double spo2, Double hrvRMSSD, Double hrvSDNN, Boolean fallDetected,
                      Double skinTemp, LocalDateTime timestamp, Double amplitude, DataQuality quality,
                      String consistenceMessage) {
        this.employee = employee;
        this.rawRedPPG = rawRedPPG;
        this.rawIRPPG = rawIRPPG;
        this.rawECG = rawECG;
        this.heartRate = heartRate;
        this.spo2 = spo2;
        this.hrvRMSSD = hrvRMSSD;
        this.hrvSDNN = hrvSDNN;
        this.fallDetected = fallDetected;
        this.skinTemp = skinTemp;
        this.timestamp = timestamp;
        this.amplitude = amplitude;
        this.quality = quality;
        this.consistenceMessage = consistenceMessage;
        this.stressLevel = calculateStressLevel();
    }

    public StressLevel calculateStressLevel(){
        if (hrvRMSSD > 50 && hrvSDNN > 50) {
            return StressLevel.LOW;
        } else if (hrvRMSSD > 30 && hrvSDNN > 30) {
            return StressLevel.ALERT_STATE;
        } else {
            return StressLevel.HIGH_STRESS;
        }
    }
}
