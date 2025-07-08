package api.vitaport.health.wearablemodule.infra.repositories;

import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IWearableRepository extends JpaRepository<Wearable, UUID> {

    @Query(value = "select * from wearable w where w.identification = :identification", nativeQuery = true)
    Wearable getWearableByIdentification(@Param("identification") String identification);

    @Query(value = "select * from wearable w where w.ip = :ip", nativeQuery = true)
    Wearable getWearableByIp(@Param("ip") String ip);
}
