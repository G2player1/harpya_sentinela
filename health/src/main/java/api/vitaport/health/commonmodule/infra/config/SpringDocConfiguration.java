package api.vitaport.health.commonmodule.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfiguration {

    private GlobalSecretKeys globalSecretKeys;

    @Autowired
    public SpringDocConfiguration(GlobalSecretKeys globalSecretKeys){
        this.globalSecretKeys = globalSecretKeys;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("bearer-key",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .servers(List.of(new Server().url(globalSecretKeys.clientUrl).description("Default Server URL")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info()
                        .title("VitaPort.api")
                        .description("API Rest de coleta de dados de saude para funcionarios da industria")
                        .contact(new Contact()
                                .name("Enos Henrique Silva Machado")
                                .email("enoshenrique008@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://vitaport/index.html")));
    }

}
