package org2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
@Profile("org2")
public class SpringDocConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8092").description("Supermarket API Server (READ ONLY)")))
                .info(new Info()
                        .title("Basil Tracker API - Supermarket (Org2)")
                        .version("1.0")
                        .description("READ ONLY access for Supermarket organization"));
    }
}