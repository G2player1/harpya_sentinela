package api.vitaport.health.healthmodule.usecases.healthdata;

import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IHealthDataRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetEmployeeHealthDataListUsecase {

    private final IHealthDataRepository healthDataRepository;

    @Autowired
    public GetEmployeeHealthDataListUsecase(IHealthDataRepository healthDataRepository){
        this.healthDataRepository = healthDataRepository;
    }

    public List<HealthData> execute(UUID employee_id){
        List<HealthData> healthDataList;
        try {
            return healthDataRepository.findAllByEmployee(employee_id);
        } catch (EntityNotFoundException | NoResultException e) {
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ, "cant get list of health data by employee uuid");
        }
    }
}
