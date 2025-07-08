package api.vitaport.health.wearablemodule.usecases.rentedWearable;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReturnRentedWearableUsecase {

    private final IRentedWearableRepository rentedWearableRepository;

    public ReturnRentedWearableUsecase(IRentedWearableRepository rentedWearableRepository){
        this.rentedWearableRepository = rentedWearableRepository;
    }

    public RentedWearable execute(UUID rentedWearableId){
        try {
            RentedWearable rentedWearable = rentedWearableRepository.getReferenceById(rentedWearableId);
            rentedWearable.returnWearable(LocalDateTime.now());
            return rentedWearableRepository.save(rentedWearable);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.GBDQ, "cant get rented wearable by uuid");
        }
    }
}
