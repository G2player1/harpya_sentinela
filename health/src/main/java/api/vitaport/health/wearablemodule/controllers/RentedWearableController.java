package api.vitaport.health.wearablemodule.controllers;

import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.mappers.RentedWearableMapper;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.GetRentedWearablesInUseUsecase;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.RegisterRentedWearableUsecase;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.ReturnRentedWearableUsecase;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.CreatedRentedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.ReadRentedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;
import org.apache.kafka.common.message.ReadShareGroupStateSummaryRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wearable/rental")
public class RentedWearableController {

    private final RentedWearableMapper rentedWearableMapper;
    private final RegisterRentedWearableUsecase registerRentedWearableUsecase;
    private final ReturnRentedWearableUsecase returnRentedWearableUsecase;
    private final GetRentedWearablesInUseUsecase getRentedWearablesInUseUsecase;

    @Autowired
    public RentedWearableController(RentedWearableMapper rentedWearableMapper, RegisterRentedWearableUsecase registerRentedWearableUsecase,
                                    ReturnRentedWearableUsecase returnRentedWearableUsecase, GetRentedWearablesInUseUsecase getRentedWearablesInUseUsecase){
        this.rentedWearableMapper = rentedWearableMapper;
        this.registerRentedWearableUsecase = registerRentedWearableUsecase;
        this.returnRentedWearableUsecase = returnRentedWearableUsecase;
        this.getRentedWearablesInUseUsecase = getRentedWearablesInUseUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedRentedWearableDTO> registerRental(RegisterRentedWearableDTO registerRentedWearableDTO,
                                                                   UriComponentsBuilder uriBuilder){
        RentedWearable rentedWearable = registerRentedWearableUsecase.execute(registerRentedWearableDTO);
        CreatedRentedWearableDTO createdRentedWearableDTO = rentedWearableMapper.mapToCreatedDTO(rentedWearable);
        URI uri = uriBuilder.path("/wearable/rental?uuid={uuid}").buildAndExpand(createdRentedWearableDTO.rented_wearable_id()).toUri();
        return ResponseEntity.created(uri).body(createdRentedWearableDTO);
    }

    @PostMapping(value = "/return", params = {"uuid"})
    public ResponseEntity<ReadRentedWearableDTO> returnRentedWearable(@Param("uuid") UUID rentedWearableId){
        RentedWearable rentedWearable = returnRentedWearableUsecase.execute(rentedWearableId);
        ReadRentedWearableDTO readRentedWearableDTO = rentedWearableMapper.mapToReadDTO(rentedWearable);
        return ResponseEntity.ok(readRentedWearableDTO);
    }

    @GetMapping("/rented/list")
    public ResponseEntity<List<ReadRentedWearableDTO>> getInUseRentedWearables(){
        List<RentedWearable> rentedWearables = getRentedWearablesInUseUsecase.execute();
        List<ReadRentedWearableDTO> readRentedWearableDTOList = rentedWearableMapper.mapToReadDTOList(rentedWearables);
        return ResponseEntity.ok(readRentedWearableDTOList);
    }

}
