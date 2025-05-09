package org1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org1.model.Basil;
import org1.model.BasilStateUpdateRequest;
import org1.model.OwnershipTransferRequest;
import org1.service.FabricService;

import javax.annotation.PreDestroy;

@RestController
@RequestMapping("/api/basil")
public class BasilController {

    private final FabricService fabricService;
    
    @Autowired
    public BasilController(FabricService fabricService) {
        this.fabricService = fabricService;
    }
    
    @PostMapping
    public ResponseEntity<String> createBasil(@RequestBody Basil basil) {
        try {
            String result;
            
            // For backward compatibility, getLocation() maps to getStation()
            String station = basil.getStation() != null ? basil.getStation() : basil.getLocation();
            
            // Check if GPS, temperature and humidity are provided
            if (basil.getCurrentGps() != null && basil.getTemperature() != null && basil.getHumidity() != null) {
                // Full create with GPS, temperature, humidity
                result = fabricService.createBasil(
                    basil.getId(), 
                    station,
                    basil.getCurrentGps(),
                    basil.getTemperature(),
                    basil.getHumidity()
                );
            } else if (basil.getTemperature() != null && basil.getHumidity() != null) {
                // Create with temperature and humidity but no GPS
                result = fabricService.createBasil(
                    basil.getId(), 
                    station,
                    basil.getTemperature(),
                    basil.getHumidity()
                );
            } else {
                // Basic create with just ID and station
                result = fabricService.createBasil(basil.getId(), station);
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating basil: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<String> getBasil(@PathVariable String id) {
        try {
            String result = fabricService.getBasil(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving basil: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<String> getAllBasil() {
        try {
            String result = fabricService.getAllBasil();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving all basil: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testChaincodeFunctions() {
        try {
            // Test if standard functions from the asset-transfer-basic chaincode work
            String result = fabricService.testChaincodeConnectivity();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error testing chaincode: " + e.getMessage());
        }
    }
    
    // Delete a basil record
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBasil(@PathVariable String id) {
        try {
            String result = fabricService.deleteBasil(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting basil: " + e.getMessage());
        }
    }
    
    // Update basil state
    @PutMapping("/{id}/state")
    public ResponseEntity<String> updateBasilState(
            @PathVariable String id,
            @RequestBody BasilStateUpdateRequest updateRequest) {
        try {
            String result = fabricService.updateBasilState(
                id, 
                updateRequest.getGps(), 
                updateRequest.getTimestamp(), 
                updateRequest.getTemperature(), 
                updateRequest.getHumidity(),
                updateRequest.getStatus()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating basil state: " + e.getMessage());
        }
    }
    
    // Get basil history
    @GetMapping("/{id}/history")
    public ResponseEntity<String> getBasilHistory(@PathVariable String id) {
        try {
            String result = fabricService.getBasilHistory(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving basil history: " + e.getMessage());
        }
    }
    
    // Transfer ownership
    @PutMapping("/{id}/ownership")
    public ResponseEntity<String> transferOwnership(
            @PathVariable String id,
            @RequestBody OwnershipTransferRequest transferRequest) {
        try {
            String result = fabricService.transferOwnership(
                id, 
                transferRequest.getOrgId(), 
                transferRequest.getName()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error transferring ownership: " + e.getMessage());
        }
    }
    
    @PreDestroy
    public void cleanup() {
        fabricService.closeConnection();
    }
}