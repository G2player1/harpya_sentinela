package api.vitaport.health.wearablemodule.controllers;

import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.mappers.WearableMapper;
import api.vitaport.health.wearablemodule.usecases.wearable.GetWearableUsecase;
import api.vitaport.health.wearablemodule.usecases.wearable.RegisterWearableUsecase;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.CreatedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.ReadWearableDTO;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.RegisterWearableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("wearable")
public class WearableController {


    private final WearableMapper wearableMapper;
    private final RegisterWearableUsecase registerWearableUsecase;
    private final GetWearableUsecase getWearableUsecase;

    @Autowired
    public WearableController(WearableMapper wearableMapper, RegisterWearableUsecase registerWearableUsecase,
                              GetWearableUsecase getWearableUsecase){
        this.wearableMapper = wearableMapper;
        this.registerWearableUsecase = registerWearableUsecase;
        this.getWearableUsecase = getWearableUsecase;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<CreatedWearableDTO> registerWearable(RegisterWearableDTO registerWearableDTO, UriComponentsBuilder uriBuilder){
        Wearable wearable = registerWearableUsecase.execute(registerWearableDTO);
        CreatedWearableDTO createdWearableDTO = wearableMapper.mapToCreatedDTO(wearable);
        URI uri = uriBuilder.path("/wearable?uuid={uuid}").buildAndExpand(createdWearableDTO.wearable_id()).toUri();
        return ResponseEntity.created(uri).body(createdWearableDTO);
    }

    @GetMapping(value = "/", params = {"uuid"})
    public ResponseEntity<ReadWearableDTO> getWearableByUUID(@Param("uuid")UUID wearable_id){
        Wearable wearable = getWearableUsecase.getWearableById(wearable_id);
        ReadWearableDTO readWearableDTO = wearableMapper.mapToReadDTO(wearable);
        return ResponseEntity.ok(readWearableDTO);
    }

    @GetMapping(value = "/identification", params = {"identification"})
    public ResponseEntity<ReadWearableDTO> getWearableByIdentification(@Param("identification") String identification){
        Wearable wearable = getWearableUsecase.getWearableByIdentification(identification);
        ReadWearableDTO readWearableDTO = wearableMapper.mapToReadDTO(wearable);
        return ResponseEntity.ok(readWearableDTO);
    }

    @GetMapping(value = "/ip", params = {"ip"})
    public ResponseEntity<ReadWearableDTO> getWearableByIp(@Param("ip") String ip){
        Wearable wearable = getWearableUsecase.getWearableByIp(ip);
        ReadWearableDTO readWearableDTO = wearableMapper.mapToReadDTO(wearable);
        return ResponseEntity.ok(readWearableDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReadWearableDTO>> getWearableList(){
        List<Wearable> wearables = getWearableUsecase.getWearableList();
        List<ReadWearableDTO> wearableDTOList = wearableMapper.mapToReadDTOList(wearables);
        return ResponseEntity.ok(wearableDTOList);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ReadWearableDTO>> getWearablePage(Pageable pageable){
        Page<Wearable> wearables = getWearableUsecase.getWearablePage(pageable);
        Page<ReadWearableDTO> wearableDTOList = wearableMapper.mapToReadDTOPage(wearables);
        return ResponseEntity.ok(wearableDTOList);
    }
}
