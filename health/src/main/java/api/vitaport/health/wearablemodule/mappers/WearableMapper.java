package api.vitaport.health.wearablemodule.mappers;

import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.domain.models.wearable.WearableType;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.CreatedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.ReadWearableDTO;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.RegisterWearableDTO;
import lombok.Locked;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WearableMapper {

    public Wearable mapToEntity(RegisterWearableDTO registerWearableDTO){
        return new Wearable(registerWearableDTO.identification(), registerWearableDTO.ip(), WearableType.fromString(registerWearableDTO.wearableType()));
    }

    public CreatedWearableDTO mapToCreatedDTO(Wearable wearable){
        return new CreatedWearableDTO(wearable);
    }

    public ReadWearableDTO mapToReadDTO(Wearable wearable){
        return new ReadWearableDTO(wearable);
    }

    public List<ReadWearableDTO> mapToReadDTOList(List<Wearable> wearables){
        return wearables.stream().map(ReadWearableDTO::new).toList();
    }

    public Page<ReadWearableDTO> mapToReadDTOPage(Page<Wearable> wearables){
        return wearables.map(ReadWearableDTO::new);
    }
}
