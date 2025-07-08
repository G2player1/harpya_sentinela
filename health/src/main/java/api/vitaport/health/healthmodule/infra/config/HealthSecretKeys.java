package api.vitaport.health.healthmodule.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthSecretKeys {

    @Value("${api.vitaport.kafka-host}")
    public String kafkaHost;
    @Value("${api.vitaport.kafka-port}")
    public String kafkaPort;
}
