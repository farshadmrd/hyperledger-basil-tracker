package org1.model;

import java.util.List;

public class Basil {
    private String qrCode;
    private Long creationTimestamp;
    private String origin;
    private String currentStatus;
    private String currentGps;
    private Owner currentOwner;
    private List<BasilLeg> transportHistory;
    
    // Default constructor for JSON deserialization
    public Basil() {}
    
    // Create request constructor (just needs ID and origin)
    public Basil(String id, String location) {
        this.qrCode = id;
        this.origin = location;
    }
    
    // Full constructor matching chaincode model
    public Basil(String qrCode, Long creationTimestamp, String origin, 
                String currentStatus, String currentGps, 
                Owner currentOwner, List<BasilLeg> transportHistory) {
        this.qrCode = qrCode;
        this.creationTimestamp = creationTimestamp;
        this.origin = origin;
        this.currentStatus = currentStatus;
        this.currentGps = currentGps;
        this.currentOwner = currentOwner;
        this.transportHistory = transportHistory;
    }
    
    // Compatibility methods for existing code
    public String getId() {
        return qrCode;
    }
    
    public void setId(String id) {
        this.qrCode = id;
    }
    
    public String getLocation() {
        return origin;
    }
    
    public void setLocation(String location) {
        this.origin = location;
    }
    
    // Getters and setters for new fields
    public String getQrCode() {
        return qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
    public Long getCreationTimestamp() {
        return creationTimestamp;
    }
    
    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getCurrentStatus() {
        return currentStatus;
    }
    
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    
    public String getCurrentGps() {
        return currentGps;
    }
    
    public void setCurrentGps(String currentGps) {
        this.currentGps = currentGps;
    }
    
    public Owner getCurrentOwner() {
        return currentOwner;
    }
    
    public void setCurrentOwner(Owner currentOwner) {
        this.currentOwner = currentOwner;
    }
    
    public List<BasilLeg> getTransportHistory() {
        return transportHistory;
    }
    
    public void setTransportHistory(List<BasilLeg> transportHistory) {
        this.transportHistory = transportHistory;
    }
}