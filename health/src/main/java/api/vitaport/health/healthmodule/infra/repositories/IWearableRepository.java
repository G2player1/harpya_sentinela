package api.vitaport.health.healthmodule.infra.repositories;

import api.vitaport.health.healthmodule.domain.models.wearable.Wearable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IWearableRepository extends JpaRepository<Wearable, UUID> {
}
