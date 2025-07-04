package api.vitaport.health.usermodule.domain.models.user;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true,length = 250)
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 250)
    private String email;
    @Column(name = "password", nullable = false, length = 250)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 250)
    private UserRole userRole;
    @Column(name = "active", nullable = false)
    private Boolean active;
    @Setter
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Employee employee;

    public User(String name,String email,String password, UserRole userRole){
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.userRole = userRole;
        this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (this.userRole) {
            case UserRole.EMPLOYEE -> {return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));}
            case UserRole.DATA_ANALYST -> {return List.of(new SimpleGrantedAuthority("ROLE_DATA_ANALYST"));}
            case UserRole.SYSTEM_MANAGER -> {return List.of(new SimpleGrantedAuthority("ROLE_SYSTEM_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_DATA_ANALYST"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE"));}
            default -> {return null;}
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
