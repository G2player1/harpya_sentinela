package api.vitaport.health.healthmodule.usecases.sse;

import api.vitaport.health.healthmodule.usecases.healthdata.dto.CreatedHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addSseEmitter(SseEmitter sseEmitter){
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
    }

    public void sendHealthData(ReadHealthDataDTO readHealthDataDTO){
        for (SseEmitter sseEmitter : emitters){
            try {
                sseEmitter.send(readHealthDataDTO);
            } catch (IOException e) {
                sseEmitter.complete();
                emitters.remove(sseEmitter);
            }
        }
    }
}
