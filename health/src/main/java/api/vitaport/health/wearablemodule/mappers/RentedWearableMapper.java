package api.vitaport.health.wearablemodule.mappers;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.CreatedRentedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.ReadRentedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.ReadWearableDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentedWearableMapper {

    public RentedWearable mapToEntity(Employee employee, Wearable wearable, LocalDateTime startDatetime){
        return new RentedWearable(wearable,employee,startDatetime);
    }

    public CreatedRentedWearableDTO mapToCreatedDTO(RentedWearable rentedWearable){
        return new CreatedRentedWearableDTO(rentedWearable);
    }

    public ReadRentedWearableDTO mapToReadDTO(RentedWearable rentedWearable){
        return new ReadRentedWearableDTO(rentedWearable);
    }

    public List<ReadRentedWearableDTO> mapToReadDTOList(List<RentedWearable> rentedWearables){
        return rentedWearables.stream().map(ReadRentedWearableDTO::new).toList();
    }

    public Page<ReadRentedWearableDTO> mapToReadDTOPage(Page<RentedWearable> rentedWearables){
        return rentedWearables.map(ReadRentedWearableDTO::new);
    }
}
