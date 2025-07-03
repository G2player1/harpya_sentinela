package api.vitaport.health.usermodule.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSecretKeys {

    @Value("${api.vitaport.token_issuer}")
    public String ISSUER;
    @Value("${api.vitaport.token-gen-pass}")
    public String tokenGenPass;
}
