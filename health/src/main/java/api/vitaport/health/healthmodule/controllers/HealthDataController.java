package api.vitaport.health.healthmodule.controllers;

import api.vitaport.health.commonmodule.infra.config.GlobalSecretKeys;
import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.mappers.HealthDataMapper;
import api.vitaport.health.healthmodule.usecases.healthdata.*;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.sse.HealthSSEService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("healthdata")
public class HealthDataController {

    private final GetEmployeeHealthDataListUsecase getEmployeeHealthDataListUsecase;
    private final GetEmployeeHealthDataPageUsecase getEmployeeHealthDataPageUsecase;
    private final GetHealthDataUsecase getHealthDataUsecase;
    private final GetLastEmployeeHealthDataUsecase getLastEmployeeHealthDataUsecase;
    private final GetLastEmployeeHealthDataListUsecase getLastEmployeeHealthDataListUsecase;
    private final HealthDataMapper healthDataMapper;
    private final HealthSSEService healthSSEService;
    private final GlobalSecretKeys globalSecretKeys;

    @Autowired
    public HealthDataController(GetHealthDataUsecase getHealthDataUsecase,
                                HealthSSEService healthSSEService,
                                GlobalSecretKeys globalSecretKeys,
                                HealthDataMapper healthDataMapper,
                                GetEmployeeHealthDataListUsecase getEmployeeHealthDataListUsecase,
                                GetEmployeeHealthDataPageUsecase getEmployeeHealthDataPageUsecase,
                                GetLastEmployeeHealthDataUsecase getLastEmployeeHealthDataUsecase,
                                GetLastEmployeeHealthDataListUsecase getLastEmployeeHealthDataListUsecase){
        this.getHealthDataUsecase = getHealthDataUsecase;
        this.healthSSEService = healthSSEService;
        this.healthDataMapper = healthDataMapper;
        this.globalSecretKeys = globalSecretKeys;
        this.getEmployeeHealthDataListUsecase = getEmployeeHealthDataListUsecase;
        this.getEmployeeHealthDataPageUsecase = getEmployeeHealthDataPageUsecase;
        this.getLastEmployeeHealthDataUsecase = getLastEmployeeHealthDataUsecase;
        this.getLastEmployeeHealthDataListUsecase = getLastEmployeeHealthDataListUsecase;
    }

    @GetMapping(value = "/", params = {"uuid"})
    public ResponseEntity<ReadHealthDataDTO> getHealthData(@Param("uuid") UUID id){
        HealthData healthData = getHealthDataUsecase.execute(id);
        ReadHealthDataDTO readHealthDataDTO = healthDataMapper.mapToReadHealthData(healthData);
        return ResponseEntity.ok(readHealthDataDTO);
    }

    @GetMapping(value = "/list", params = {"employee_id"})
    public ResponseEntity<List<ReadHealthDataDTO>> getEmployeeHealthDataList(@Param("employee_id") UUID employee_id){
        List<HealthData> healthDataList = getEmployeeHealthDataListUsecase.execute(employee_id);
        List<ReadHealthDataDTO> readHealthDataDTOList = healthDataMapper.mapToReadHealthData(healthDataList);
        return ResponseEntity.ok(readHealthDataDTOList);
    }

    @GetMapping(value = "/page", params = {"employee_id"})
    public ResponseEntity<Page<ReadHealthDataDTO>> getEmployeeHealthDataPage(@Param("employee_id") UUID employee_id, Pageable pageable){
        Page<HealthData> healthDataPage = getEmployeeHealthDataPageUsecase.execute(employee_id, pageable);
        Page<ReadHealthDataDTO> readHealthDataDTOPage = healthDataMapper.mapToReadHealthData(healthDataPage);
        return ResponseEntity.ok(readHealthDataDTOPage);
    }

    @GetMapping(value = "/last", params = {"employee_id"})
    public ResponseEntity<ReadHealthDataDTO> getLastEmployeeHealthData(@Param("employee_id") UUID employee_id){
        HealthData healthData = getLastEmployeeHealthDataUsecase.execute(employee_id);
        ReadHealthDataDTO readHealthDataDTO = healthDataMapper.mapToReadHealthData(healthData);
        return ResponseEntity.ok(readHealthDataDTO);
    }

    @GetMapping("/last/list")
    public ResponseEntity<List<ReadHealthDataDTO>> getLastEmployeeHealthDataList(){
        List<HealthData> healthDataList = getLastEmployeeHealthDataListUsecase.execute();
        List<ReadHealthDataDTO> readHealthDataDTOList = healthDataMapper.mapToReadHealthData(healthDataList);
        return ResponseEntity.ok(readHealthDataDTOList);
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscriber(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", globalSecretKeys.clientUrl);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "*");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        healthSSEService.addSseEmitter(sseEmitter);
        return sseEmitter;
    }
}
