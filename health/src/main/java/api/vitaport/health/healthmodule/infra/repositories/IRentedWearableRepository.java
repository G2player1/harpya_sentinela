package api.vitaport.health.healthmodule.infra.repositories;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.domain.models.wearable.RentedWearable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRentedWearableRepository extends JpaRepository<RentedWearable, UUID> {

    @Query("""
        SELECT rw.employee
        FROM RentedWearable rw
        WHERE rw.wearable.uuid = :wearableId
          AND rw.inUse = true
        """)
    Employee findCurrentEmployeeByWearableId(@Param("wearableId") UUID wearableId);
}
