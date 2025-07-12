package api.vitaport.health.gpsmodule.usecases.zone;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import api.vitaport.health.gpsmodule.mapper.RestrictedEmployeeMapper;
import api.vitaport.health.gpsmodule.mapper.ZoneMapper;
import api.vitaport.health.gpsmodule.usecases.exceptions.RegisterZoneException;
import api.vitaport.health.gpsmodule.usecases.zone.dto.RegisterZoneDTO;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.employee.GetEmployeeDataUsecase;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterZoneUsecase {

    private final ZoneMapper zoneMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private final RestrictedEmployeeMapper restrictedEmployeeMapper;
    private final IZoneRepository zoneRepository;
    private final GetEmployeeDataUsecase getEmployeeDataUsecase;

    @Autowired
    public RegisterZoneUsecase(ZoneMapper zoneMapper, RestrictedEmployeeMapper restrictedEmployeeMapper, IZoneRepository zoneRepository, GetEmployeeDataUsecase getEmployeeDataUsecase){
        this.zoneMapper = zoneMapper;
        this.restrictedEmployeeMapper = restrictedEmployeeMapper;
        this.zoneRepository = zoneRepository;
        this.getEmployeeDataUsecase = getEmployeeDataUsecase;
    }

    public Zone execute(RegisterZoneDTO registerZoneDTO) {
        try {
            List<Employee> employees = registerZoneDTO.employees().stream().map(getEmployeeDataUsecase::execute).toList();
            Coordinate[] coordinates = registerZoneDTO.points().stream().map(point -> new Coordinate(point.longitude(),point.latitude())).toArray(Coordinate[]::new);
            LinearRing shell = geometryFactory.createLinearRing(coordinates);
            Polygon area = geometryFactory.createPolygon(shell, null);
            area.setSRID(4326);
            Zone zone = zoneMapper.mapToEntity(registerZoneDTO,area);
            List<RestrictedEmployee> restrictedEmployees = employees.stream().map(employee -> restrictedEmployeeMapper.mapToEntity(zone,employee)).toList();
            zone.updateRestrictedEmployees(restrictedEmployees);
            return zoneRepository.save(zone);
        } catch (IllegalArgumentException e){
            throw new RegisterZoneException(400, ErrorEnum.LAPI, e.getMessage());
        } catch (JpaSystemException e){
            throw new RegisterZoneException(503, ErrorEnum.LAPI, e.getMessage());
        }
    }
}
