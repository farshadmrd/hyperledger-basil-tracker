package org2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org2.model.Organization;
import org2.service.FabricServiceOrg2;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/organizations")
@Tag(name = "Organizations - Supermarket", description = "API endpoints for organizations in the blockchain network (Supermarket view)")
public class OrganizationControllerOrg2 {

    private final FabricServiceOrg2 fabricService;
    
    @Autowired
    public OrganizationControllerOrg2(FabricServiceOrg2 fabricService) {
        this.fabricService = fabricService;
    }
    
    @Operation(
        summary = "Get all organizations", 
        description = "Retrieves all organizations participating in the blockchain network"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved organizations"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        List<Organization> organizations = fabricService.getAllOrganizations();
        return ResponseEntity.ok(organizations);
    }
    
    @Operation(
        summary = "Get organization by ID", 
        description = "Retrieves a specific organization by its ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Organization found"),
        @ApiResponse(responseCode = "404", description = "Organization not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {
        Organization organization = fabricService.getOrganizationById(id);
        if (organization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organization);
    }
}