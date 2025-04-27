package org2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller to customize the Swagger UI for Org2
 */
@Controller
@Profile("org2")
public class SwaggerRedirectController {
    
    @Value("${server.port:8092}")
    private String port;
    
    /**
     * Override the default Swagger UI path to include custom query parameters
     * that set the server URL correctly
     */
    @GetMapping("/swagger")
    public RedirectView redirectToSwaggerUI() {
        // Redirect to the Swagger UI with custom query parameters to set the server URL
        return new RedirectView("/swagger-ui/index.html?url=/v3/api-docs&serverUrl=http://localhost:" + port);
    }
}