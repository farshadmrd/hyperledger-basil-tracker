package org1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getOrganizations() {
        // Create organization records with display name, ID, and access level
        List<Map<String, Object>> organizations = Arrays.asList(
            Map.of(
                "id", "Org1MSP", 
                "name", "Pittaluga & fratelli", 
                "accessLevel", "Full",
                "type", "Producer",
                "canCreate", true,
                "canUpdate", true,
                "canDelete", true,
                "canTransfer", true
            ),
            Map.of(
                "id", "Org2MSP", 
                "name", "Supermarket", 
                "accessLevel", "Limited Access",
                "type", "Retailer",
                "canCreate", false,
                "canUpdate", false,
                "canDelete", false,
                "canTransfer", false
            )
        );
        
        return ResponseEntity.ok(organizations);
    }
}