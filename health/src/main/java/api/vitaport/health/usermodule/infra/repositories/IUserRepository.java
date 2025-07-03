package api.vitaport.health.usermodule.infra.repositories;

import api.vitaport.health.usermodule.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    UserDetails findUserByEmail(String email);
    @Query(value = "select * from users where users.email = :email",nativeQuery = true)
    User getUserByEmail(String email);
    @Query(value = "select * from users where users.name = :username",nativeQuery = true)
    User getUserByUsername(String username);
}
