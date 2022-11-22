package com.example.mowers.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mowers API")
                        .description("Application to control mowers through an API.")
                        .version("v0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/softwarejimenez/mowers"));
    }
}
