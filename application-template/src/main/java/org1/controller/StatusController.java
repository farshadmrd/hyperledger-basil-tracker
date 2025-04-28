package org1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getBasilStatuses() {
        // Create a list of available statuses for basil plants
        List<Map<String, Object>> statuses = Arrays.asList(
            Map.of(
                "id", "active",
                "name", "Active",
                "description", "Basil plant is actively growing"
            ),
            Map.of(
                "id", "dormant",
                "name", "Dormant",
                "description", "Basil plant is in a dormant state"
            ),
            Map.of(
                "id", "harvested",
                "name", "Harvested",
                "description", "Basil has been harvested and is being processed"
            ),
            Map.of(
                "id", "in_transit",
                "name", "In Transit",
                "description", "Basil is being transported between locations"
            ),
            Map.of(
                "id", "diseased",
                "name", "Diseased",
                "description", "Basil plant has developed a disease"
            ),
            Map.of(
                "id", "created",
                "name", "Created",
                "description", "Basil plant has just been created in the system"
            )
        );
        
        return ResponseEntity.ok(statuses);
    }
}