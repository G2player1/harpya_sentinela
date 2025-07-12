package api.vitaport.health.wearablemodule.usecases.rentedWearable;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetActualWearableUserUsecase {

    private final IRentedWearableRepository rentedWearableRepository;

    @Autowired
    public GetActualWearableUserUsecase(IRentedWearableRepository rentedWearableRepository){
        this.rentedWearableRepository = rentedWearableRepository;
    }

    public Employee execute(UUID wearable_id){
        try {
            return rentedWearableRepository.findCurrentEmployeeByWearableId(wearable_id);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ, "cant get employee by wearable employee_id");
        }
    }
}
