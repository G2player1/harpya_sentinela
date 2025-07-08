package api.vitaport.health.gpsmodule.domain.models;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "restricted_employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RestrictedEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "zone_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Zone zone;
    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Employee employee;
    @Column(name = "active", nullable = false)
    private Boolean active;

    public RestrictedEmployee(Zone zone, Employee employee) {
        this.zone = zone;
        this.employee = employee;
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }
}
