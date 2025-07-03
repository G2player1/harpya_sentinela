package api.vitaport.health.healthmodule.infra.repositories;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = "SELECT e.* FROM employee e JOIN users u ON u.id = e.user_id WHERE u.email = :email", nativeQuery = true)
    Employee findEmployeeByEmail(@Param("email") String email);

    @Query(value = "select * from employee where registration_number = :reg", nativeQuery = true)
    Employee findEmployeeByRegistration(@Param("reg") String reg);
}
