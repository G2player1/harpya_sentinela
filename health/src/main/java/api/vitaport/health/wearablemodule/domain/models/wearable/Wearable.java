package api.vitaport.health.wearablemodule.domain.models.wearable;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "wearable")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Wearable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(name = "identification", nullable = false, unique = true)
    private String identification;
    @Column(name = "ip", nullable = false, unique = true)
    private String ip;
    @Enumerated(EnumType.STRING)
    @Column(name = "wearable_type", nullable = false, length = 30)
    private WearableType wearableType;
    @Setter
    @OneToOne(mappedBy = "wearable", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private RentedWearable rentedWearable;

    public Wearable(String identification, String ip, WearableType wearableType) {
        this.identification = identification;
        this.ip = ip;
        this.wearableType = wearableType;
    }
}
