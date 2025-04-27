package org1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org1.model.Basil;
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
            String result = fabricService.createBasil(basil.getId(), basil.getLocation());
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
    
    @PreDestroy
    public void cleanup() {
        fabricService.closeConnection();
    }
}