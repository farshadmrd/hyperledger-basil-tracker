package org2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org2.service.FabricServiceOrg2;

import javax.annotation.PreDestroy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/basil")
@Tag(name = "Basil Controller - Supermarket (READ ONLY)", description = "API endpoints for Supermarket (Org2) - Read-only access")
public class BasilControllerOrg2 {

    private final FabricServiceOrg2 fabricService;
    
    @Autowired
    public BasilControllerOrg2(FabricServiceOrg2 fabricService) {
        this.fabricService = fabricService;
    }
    
    // Supermarket can only read data, not create/update/delete
    @Operation(
        summary = "Get a specific basil plant", 
        description = "Retrieves a basil plant by its ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Basil plant found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> getBasil(@PathVariable String id) {
        try {
            String result = fabricService.getBasil(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving basil: " + e.getMessage());
        }
    }
    
    @Operation(
        summary = "Get all basil plants", 
        description = "Retrieves all basil plants from the blockchain"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<String> getAllBasil() {
        try {
            String result = fabricService.getAllBasil();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving all basil: " + e.getMessage());
        }
    }
    
    @Operation(
        summary = "Get basil plant history", 
        description = "Retrieves the complete history of a basil plant"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "History retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/history")
    public ResponseEntity<String> getBasilHistory(@PathVariable String id) {
        try {
            String result = fabricService.getBasilHistory(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving basil history: " + e.getMessage());
        }
    }
    
    // Hide all modification operations from Swagger UI
    @io.swagger.v3.oas.annotations.Hidden
    @PostMapping
    public ResponseEntity<String> createBasil() {
        return ResponseEntity.status(403).body(
                "Supermarkets are not authorized to create basil plants");
    }
    
    @io.swagger.v3.oas.annotations.Hidden
    @PutMapping("/{id}/state")
    public ResponseEntity<String> updateBasilState(@PathVariable String id) {
        return ResponseEntity.status(403).body(
                "Supermarkets are not authorized to update basil state");
    }
    
    @io.swagger.v3.oas.annotations.Hidden
    @PutMapping("/{id}/ownership")
    public ResponseEntity<String> transferOwnership(@PathVariable String id) {
        return ResponseEntity.status(403).body(
                "Supermarkets are not authorized to transfer ownership");
    }
    
    @io.swagger.v3.oas.annotations.Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBasil(@PathVariable String id) {
        return ResponseEntity.status(403).body(
                "Supermarkets are not authorized to delete basil plants");
    }
    
    @PreDestroy
    public void cleanup() {
        fabricService.closeConnection();
    }
}