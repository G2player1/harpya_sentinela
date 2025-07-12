package api.vitaport.health.gpsmodule.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeRegistration;
import org.locationtech.jts.geom.Polygon;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "zone")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter

public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(mappedBy = "zone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<RestrictedEmployee> restrictedEmployees;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Setter
    @Column(name = "area", columnDefinition = "geometry(Polygon,4326)")
    private Polygon area;
    @Column(name = "active", nullable = false)
    private Boolean active;

    public Zone(String name, String description, Polygon area){
        this.name = name;
        this.description = description;
        this.area = area;
        this.active = true;
    }

    public void addRestrictedEmployee(RestrictedEmployee restrictedEmployee){
        this.restrictedEmployees.add(restrictedEmployee);
    }

    public void updateRestrictedEmployees(List<RestrictedEmployee> restrictedEmployees){
        if (restrictedEmployees.isEmpty())
            throw new RuntimeException();
        this.restrictedEmployees = restrictedEmployees;
    }

    public void deactivate(){
        this.active = false;
    }
}
