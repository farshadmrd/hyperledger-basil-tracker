package org1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Controller to provide station types for the frontend
 */
@RestController
@RequestMapping("/api/station-types")
@CrossOrigin // Enable CORS for frontend access
public class StationTypesController {

    /**
     * Static list of available station types in the supply chain with unique IDs
     */
    private final List<Map<String, Object>> stationTypes = Arrays.asList(
            createStationType("GRN", "Greenhouse"),
            createStationType("DST", "Distributor"),
            createStationType("LOG", "Logistic company"),
            createStationType("RWH", "Retailer warehouse"),
            createStationType("SPM", "Supermarket")
    );
    
    /**
     * Helper method to create a station type with ID and name
     * @param id The unique identifier for the station type
     * @param name The display name of the station type
     * @return A map containing the ID and name
     */
    private Map<String, Object> createStationType(String id, String name) {
        Map<String, Object> stationType = new HashMap<>();
        stationType.put("id", id);
        stationType.put("name", name);
        return stationType;
    }

    /**
     * Get all available station types
     * @return List of station types with IDs and names
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getStationTypes() {
        return ResponseEntity.ok(stationTypes);
    }
}