package api.vitaport.health.healthmodule.domain.models.employee;

import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.usermodule.domain.models.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "registration_number", nullable = false, unique = true, length = 250)
    private String registrationNumber;
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;
    @Column(name = "full_name", nullable = false, unique = true, length = 250)
    private String fullName;
    @Column(name = "social_name", nullable = false, length = 30)
    private String socialName;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HealthData> healthDataList;
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RentedWearable> rentedWearables;
    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;


    public Employee(String cpf, String fullName, String registrationNumber, String socialName) {
        this.cpf = cpf;
        this.fullName = fullName;
        this.registrationNumber = registrationNumber;
        this.socialName = socialName;
        this.healthDataList = new ArrayList<>();
        this.rentedWearables = new ArrayList<>();
    }

    public void addHealthData(@NotNull(message = "Health data is null") HealthData healthData){
        this.healthDataList.add(healthData);
    }
}
