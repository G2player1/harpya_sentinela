package api.vitaport.health.commonmodule.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalSecretKeys {

    @Value("${api.vitaport.client-url}")
    public String clientUrl;
}
