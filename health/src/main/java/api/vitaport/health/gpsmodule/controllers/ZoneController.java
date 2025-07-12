package api.vitaport.health.gpsmodule.controllers;

import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.mapper.ZoneMapper;
import api.vitaport.health.gpsmodule.usecases.zone.AddRestrictedEmployeeUsecase;
import api.vitaport.health.gpsmodule.usecases.zone.GetZoneByNameUsecase;
import api.vitaport.health.gpsmodule.usecases.zone.GetZonesUsecase;
import api.vitaport.health.gpsmodule.usecases.zone.RegisterZoneUsecase;
import api.vitaport.health.gpsmodule.usecases.zone.dto.CreatedZoneDTO;
import api.vitaport.health.gpsmodule.usecases.zone.dto.ReadZoneDTO;
import api.vitaport.health.gpsmodule.usecases.zone.dto.RegisterZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("zone")
public class ZoneController {

    private final ZoneMapper zoneMapper;
    private final RegisterZoneUsecase registerZoneUsecase;
    private final GetZoneByNameUsecase getZoneByNameUsecase;
    private final GetZonesUsecase getZonesUsecase;
    private final AddRestrictedEmployeeUsecase addRestrictedEmployeeUsecase;

    @Autowired
    public ZoneController(ZoneMapper zoneMapper, RegisterZoneUsecase registerZoneUsecase, GetZoneByNameUsecase getZoneByNameUsecase,
                          GetZonesUsecase getZonesUsecase, AddRestrictedEmployeeUsecase addRestrictedEmployeeUsecase){
        this.zoneMapper = zoneMapper;
        this.registerZoneUsecase = registerZoneUsecase;
        this.getZoneByNameUsecase = getZoneByNameUsecase;
        this.getZonesUsecase = getZonesUsecase;
        this.addRestrictedEmployeeUsecase = addRestrictedEmployeeUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<CreatedZoneDTO> registerZone(@RequestBody RegisterZoneDTO registerZoneDTO, UriComponentsBuilder uriBuilder){
        Zone zone = registerZoneUsecase.execute(registerZoneDTO);
        CreatedZoneDTO createdZoneDTO = zoneMapper.mapToCreatedDTO(zone);
        URI uri = uriBuilder.path("/zone?uuid={uuid}").buildAndExpand(createdZoneDTO.zone_id()).toUri();
        return ResponseEntity.created(uri).body(createdZoneDTO);
    }

    @PutMapping(value = "/employee/add", params = {"zoneName","employeeId"})
    public ResponseEntity<ReadZoneDTO> addRestrictedEmployee(@Param("zoneName") String zoneName, @Param("uuid") UUID employeeId){
        Zone zone = addRestrictedEmployeeUsecase.execute(zoneName,employeeId);
        ReadZoneDTO readZoneDTO = zoneMapper.mapToReadDTO(zone);
        return ResponseEntity.ok(readZoneDTO);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<ReadZoneDTO> getZoneByName(@Param("name") String name){
        Zone zone = getZoneByNameUsecase.execute(name);
        ReadZoneDTO readZoneDTO = zoneMapper.mapToReadDTO(zone);
        return ResponseEntity.ok(readZoneDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ReadZoneDTO>> getZones(){
        List<Zone> zones = getZonesUsecase.execute();
        List<ReadZoneDTO> readZoneDTOList = zoneMapper.mapToReadDTO(zones);
        return ResponseEntity.ok(readZoneDTOList);
    }
}
