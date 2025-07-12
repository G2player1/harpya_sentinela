package api.vitaport.health.gpsmodule.controllers;

import api.vitaport.health.commonmodule.infra.config.GlobalSecretKeys;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.CheckEmployeeLocUsecase;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.EmployeeLocSSEService;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("gps")
public class GPSController {

    private final GlobalSecretKeys globalSecretKeys;
    private final EmployeeLocSSEService employeeLocSSEService;
    private final CheckEmployeeLocUsecase checkEmployeeLocUsecase;

    @Autowired
    public GPSController(GlobalSecretKeys globalSecretKeys, EmployeeLocSSEService employeeLocSSEService, CheckEmployeeLocUsecase checkEmployeeLocUsecase){
        this.globalSecretKeys = globalSecretKeys;
        this.employeeLocSSEService = employeeLocSSEService;
        this.checkEmployeeLocUsecase = checkEmployeeLocUsecase;
    }

    @PostMapping("/send-location")
    public ResponseEntity<?> receiveLocation(@RequestBody EmployeeLocDTO employeeLocDTO){
        checkEmployeeLocUsecase.execute(employeeLocDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscriber(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", globalSecretKeys.clientUrl);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "*");
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        employeeLocSSEService.addSseEmitter(sseEmitter);
        return sseEmitter;
    }
}
