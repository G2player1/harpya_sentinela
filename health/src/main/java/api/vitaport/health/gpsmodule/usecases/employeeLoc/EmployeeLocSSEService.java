package api.vitaport.health.gpsmodule.usecases.employeeLoc;

import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.AlertEmployeeLocDTO;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EmployeeLocSSEService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addSseEmitter(SseEmitter sseEmitter){
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
    }

    public void sendEmployeeLoc(EmployeeLocDTO employeeLocDTO){
        for (SseEmitter sseEmitter : emitters){
            try {
                sseEmitter.send(employeeLocDTO);
            } catch (IOException e) {
                sseEmitter.complete();
                emitters.remove(sseEmitter);
            }
        }
    }

    public void sendAlertEmployeeLoc(AlertEmployeeLocDTO alertEmployeeLocDTO){
        for (SseEmitter sseEmitter : emitters){
            try {
                sseEmitter.send(alertEmployeeLocDTO);
            } catch (IOException e) {
                sseEmitter.complete();
                emitters.remove(sseEmitter);
            }
        }
    }
}
