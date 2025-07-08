package api.vitaport.health.wearablemodule.usecases.wearable;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.infra.repositories.IWearableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetWearableUsecase {

    private final IWearableRepository wearableRepository;

    @Autowired
    public GetWearableUsecase(IWearableRepository wearableRepository){
        this.wearableRepository = wearableRepository;
    }

    public Wearable getWearableById(UUID wearableId) {
        try {
            return wearableRepository.getReferenceById(wearableId);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get wearable data by uuid");
        }
    }

    public Wearable getWearableByIdentification(String identification) {
        try {
            return wearableRepository.getWearableByIdentification(identification);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get wearable data by identification");
        }
    }

    public Wearable getWearableByIp(String ip) {
        try {
            return wearableRepository.getWearableByIp(ip);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get wearable data by ip");
        }
    }

    public List<Wearable> getWearableList() {
        try {
            return wearableRepository.findAll();
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get wearable list from database");
        }
    }

    public Page<Wearable> getWearablePage(Pageable pageable) {
        try {
            return wearableRepository.findAll(pageable);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get wearable page from database");
        }
    }
}
