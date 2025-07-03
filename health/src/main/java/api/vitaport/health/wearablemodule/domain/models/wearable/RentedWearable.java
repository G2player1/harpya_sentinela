package api.vitaport.health.wearablemodule.domain.models.wearable;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rented_wearable")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RentedWearable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "wearable_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Wearable wearable;
    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Employee employee;
    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDatetime;
    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;
    @Column(name = "in_use", nullable = false)
    private Boolean inUse;

    public RentedWearable(Wearable wearable, Employee employee, LocalDateTime startDatetime){
        this.wearable = wearable;
        this.employee = employee;
        this.startDatetime = startDatetime;
        this.inUse = true;
    }

    public void returnWearable(LocalDateTime endDatetime){
        this.endDatetime = endDatetime;
        this.inUse = false;
    }
}
