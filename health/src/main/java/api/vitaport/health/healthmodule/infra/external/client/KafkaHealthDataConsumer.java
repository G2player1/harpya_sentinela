package api.vitaport.health.healthmodule.infra.external.client;

import api.vitaport.health.healthmodule.usecases.healthdata.RegisterHealthDataUsecase;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.RegisterHealthDataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthDataConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;
    private final Logger logger = LoggerFactory.getLogger(KafkaHealthDataConsumer.class);

    private final RegisterHealthDataUsecase registerHealthDataUsecase;


    @Autowired
    public KafkaHealthDataConsumer(RegisterHealthDataUsecase registerHealthDataUsecase) {
        this.registerHealthDataUsecase = registerHealthDataUsecase;
    }

    @KafkaListener(topics = "health.data", groupId = "vitaport-health-group", containerFactory = "HealthDataListenerContainerFactory")
    public void listen(String message) {
        try {
            RegisterHealthDataDTO dto = objectMapper.readValue(message, RegisterHealthDataDTO.class);
            logger.info("Data transfer object successfully received: {}",dto);
            registerHealthDataUsecase.execute(dto);
            logger.info("Data transfer object successfully saved");
        } catch (Exception e) {
            logger.error(" Falha ao processar dado: {}", e.getMessage(), e);
        }
    }
}
