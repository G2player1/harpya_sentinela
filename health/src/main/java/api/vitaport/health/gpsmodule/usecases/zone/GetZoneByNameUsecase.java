package api.vitaport.health.gpsmodule.usecases.zone;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetZoneByNameUsecase {

    private final IZoneRepository zoneRepository;

    @Autowired
    public GetZoneByNameUsecase(IZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    public Zone execute(String name){
        try {
            return zoneRepository.getReferenceByName(name);
        } catch (EntityNotFoundException | NoResultException e) {
            throw new CannotGetEntityDataException(400, ErrorEnum.GBDQ, "cant get zone by his name");
        }
    }
}
