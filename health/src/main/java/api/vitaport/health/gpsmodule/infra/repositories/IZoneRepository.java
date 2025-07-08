package api.vitaport.health.gpsmodule.infra.repositories;

import api.vitaport.health.gpsmodule.domain.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IZoneRepository extends JpaRepository<Zone, UUID> {
    Zone getReferenceByName(String name);
}
