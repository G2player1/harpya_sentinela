package api.vitaport.health.gpsmodule.infra.repositories;

import api.vitaport.health.gpsmodule.domain.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IZoneRepository extends JpaRepository<Zone, UUID> {
    Zone getReferenceByName(String name);

    @Query(value = "SELECT * FROM zone WHERE ST_Contains(area, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326))", nativeQuery = true)
    List<Zone> findZonesContainingPoint(@Param("longitude") Double longitude, @Param("latitude") Double latitude);
}
