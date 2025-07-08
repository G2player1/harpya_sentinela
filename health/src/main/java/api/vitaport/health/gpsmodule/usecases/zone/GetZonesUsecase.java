package api.vitaport.health.gpsmodule.usecases.zone;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetZonesUsecase {

    private final IZoneRepository zoneRepository;

    @Autowired
    public GetZonesUsecase(IZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> execute(){
        try {
            return zoneRepository.findAll();
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(501, ErrorEnum.GBDQ, "cant get list of zones");
        }
    }
}
