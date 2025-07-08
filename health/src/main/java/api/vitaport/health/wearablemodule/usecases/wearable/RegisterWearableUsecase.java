package api.vitaport.health.wearablemodule.usecases.wearable;

import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.infra.repositories.IWearableRepository;
import api.vitaport.health.wearablemodule.mappers.WearableMapper;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.RegisterWearableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterWearableUsecase {

    private final WearableMapper wearableMapper;
    private final IWearableRepository wearableRepository;

    @Autowired
    public RegisterWearableUsecase(WearableMapper wearableMapper, IWearableRepository wearableRepository){
        this.wearableRepository = wearableRepository;
        this.wearableMapper = wearableMapper;
    }

    public Wearable execute(RegisterWearableDTO registerWearableDTO) {
        Wearable wearable = wearableMapper.mapToEntity(registerWearableDTO);
        return wearableRepository.save(wearable);
    }
}
