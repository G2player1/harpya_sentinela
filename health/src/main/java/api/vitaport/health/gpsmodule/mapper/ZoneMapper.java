package api.vitaport.health.gpsmodule.mapper;

import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.usecases.zone.dto.CreatedZoneDTO;
import api.vitaport.health.gpsmodule.usecases.zone.dto.ReadZoneDTO;
import api.vitaport.health.gpsmodule.usecases.zone.dto.RegisterZoneDTO;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneMapper {
    public CreatedZoneDTO mapToCreatedDTO(Zone zone) {
        return new CreatedZoneDTO(zone);
    }

    public Zone mapToEntity(RegisterZoneDTO registerZoneDTO, Polygon area) {
        return new Zone(registerZoneDTO.name(), registerZoneDTO.description(), area);
    }

    public ReadZoneDTO mapToReadDTO(Zone zone) {
        return new ReadZoneDTO(zone);
    }

    public List<ReadZoneDTO> mapToReadDTO(List<Zone> zones) {
        return zones.stream().map(ReadZoneDTO::new).toList();
    }
}
