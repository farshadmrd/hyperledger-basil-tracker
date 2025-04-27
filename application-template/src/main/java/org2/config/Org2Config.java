package org2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("org2")
public class Org2Config {
    // This configuration class ensures that Org2-specific beans
    // are only loaded when the "org2" profile is active
}