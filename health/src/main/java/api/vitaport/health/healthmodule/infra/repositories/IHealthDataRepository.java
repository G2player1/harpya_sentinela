package api.vitaport.health.healthmodule.infra.repositories;

import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IHealthDataRepository extends JpaRepository<HealthData, UUID> {

    @Query(value = "SELECT DISTINCT ON (employee_id) * FROM health_data ORDER BY employee_id, timestamp DESC",nativeQuery = true)
    List<HealthData> getAllLastHealthDataByEmployee();

    @Query(value = "SELECT * FROM health_data WHERE employee_id = :employee_id ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    HealthData findLatestByEmployeeId(@Param("employee_id") UUID employee_id);

    @Query(value = "select * from health_data where employee_id = :employee_id", nativeQuery = true)
    List<HealthData> findAllByEmployee(@Param("employee_id") UUID employee_id);

    @Query(value = "select * from health_data where employee_id = :employee_id", nativeQuery = true)
    Page<HealthData> findAllByEmployee(@Param("employee_id") UUID employee_id, Pageable pageable);

    @Query(" SELECT h FROM HealthData h WHERE h.employee.id = :employeeId AND h.timestamp = :timestamp")
    Optional<HealthData> findByEmployeeIdAndTimestamp(@Param("employeeId") UUID employeeId, @Param("timestamp") LocalDateTime timestamp);
}
