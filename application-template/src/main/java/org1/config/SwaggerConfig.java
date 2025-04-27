package org1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI basilTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Basil Tracker API")
                        .description("API for tracking basil plants on the blockchain")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Hyperledger Basil Tracker")
                                .email("support@basiltracker.com")
                                .url("https://github.com/yourusername/hyperledger-basil-tracker"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:8090").description("Local development server")
                ));
    }
}