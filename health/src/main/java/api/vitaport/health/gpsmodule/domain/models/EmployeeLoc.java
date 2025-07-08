package api.vitaport.health.gpsmodule.domain.models;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_localization")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmployeeLoc {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JoinColumn(name = "employee_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Employee employee;
    @JoinColumn(name = "point_id")
    @Column(name = "localization", columnDefinition = "geometry(PointZ, 4326)")
    private Point point;
    @Column(name = "datetime", nullable = false, unique = true)
    private LocalDateTime dateTime;

    public EmployeeLoc(Employee employee, Point point, LocalDateTime dateTime) {
        this.employee = employee;
        this.point = point;
        this.dateTime = dateTime;
    }
}
