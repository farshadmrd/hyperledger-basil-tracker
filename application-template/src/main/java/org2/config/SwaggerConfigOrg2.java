package org2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Operation;

import java.util.List;

@Configuration
@Profile("org2")
public class SwaggerConfigOrg2 {

    @Bean
    public OpenAPI basilTrackerOrg2OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Basil Tracker API - Supermarket (Org2)")
                        .description("API for tracking basil plants on the blockchain - READ ONLY ACCESS. " +
                                "Supermarket organization can only view basil plants, not create, update or delete them.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Hyperledger Basil Tracker")
                                .email("support@basiltracker.com")
                                .url("https://github.com/yourusername/hyperledger-basil-tracker"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:8092").description("Supermarket API (READ ONLY)")
                ));
    }
}