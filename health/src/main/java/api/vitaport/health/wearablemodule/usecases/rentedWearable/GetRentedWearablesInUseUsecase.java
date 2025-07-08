package api.vitaport.health.wearablemodule.usecases.rentedWearable;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRentedWearablesInUseUsecase {

    private final IRentedWearableRepository rentedWearableRepository;

    @Autowired
    public GetRentedWearablesInUseUsecase(IRentedWearableRepository rentedWearableRepository){
        this.rentedWearableRepository = rentedWearableRepository;
    }

    public List<RentedWearable> execute(){
        try {
            return rentedWearableRepository.findRentedWearablesInUse();
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.GBDQ,"cant get list of rented wearables in use");
        }
    }
}
