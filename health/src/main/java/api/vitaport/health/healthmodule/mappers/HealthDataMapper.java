package api.vitaport.health.healthmodule.mappers;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.domain.models.healthdata.DataQuality;
import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.CreatedHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.RegisterHealthDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthDataMapper {

    public HealthData mapToEntity(Employee employee, RegisterHealthDataDTO registerHealthDataDTO){
        return new HealthData(employee, registerHealthDataDTO.rawRedPPG(), registerHealthDataDTO.rawIRPPG(), registerHealthDataDTO.rawECG(),
                registerHealthDataDTO.heartRate(), registerHealthDataDTO.spo2(), registerHealthDataDTO.hrvRMSSD(), registerHealthDataDTO.hrvSDNN(),
                registerHealthDataDTO.fallDetected(), registerHealthDataDTO.skinTemp(), registerHealthDataDTO.timestamp(), registerHealthDataDTO.amplitude(),
                DataQuality.fromString(registerHealthDataDTO.quality()), registerHealthDataDTO.consistence());
    }


    public CreatedHealthDataDTO mapToCreatedHealthData(HealthData healthData){
        return new CreatedHealthDataDTO(healthData);
    }

    public ReadHealthDataDTO mapToReadHealthData(HealthData healthData){
        return new ReadHealthDataDTO(healthData);
    }

    public List<ReadHealthDataDTO> mapToReadHealthData(List<HealthData> healthDataList){
        return healthDataList.stream().map(ReadHealthDataDTO::new).toList();
    }

    public Page<ReadHealthDataDTO> mapToReadHealthData(Page<HealthData> healthDataPage){
        return healthDataPage.map(ReadHealthDataDTO::new);
    }
}
