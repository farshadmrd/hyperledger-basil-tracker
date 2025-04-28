package org1.model;

import java.util.List;

/**
 * Represents a basil plant in the supply chain.
 */
public class Basil {
    private String qrCode;
    private Long creationTimestamp;
    private String location;     // Initial location (greenhouse)
    private String currentStatus;
    private String currentGps;   // Current geographical location
    private String temperature;  // Temperature measurement
    private String humidity;     // Humidity measurement
    private Owner currentOwner;
    private List<BasilLeg> transportHistory;
    
    // Default constructor for JSON deserialization
    public Basil() {}
    
    // Create request constructor with temperature and humidity
    public Basil(String id, String location, String temperature, String humidity) {
        this.qrCode = id;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    // Create request constructor (just needs ID and location)
    public Basil(String id, String location) {
        this.qrCode = id;
        this.location = location;
    }
    
    // Full constructor matching chaincode model
    public Basil(String qrCode, Long creationTimestamp, String location, 
                String currentStatus, String currentGps, 
                Owner currentOwner, List<BasilLeg> transportHistory) {
        this.qrCode = qrCode;
        this.creationTimestamp = creationTimestamp;
        this.location = location;
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
    
    // Getters and setters for temperature and humidity
    public String getTemperature() {
        return temperature;
    }
    
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    
    public String getHumidity() {
        return humidity;
    }
    
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    
    // Getters and setters for fields
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
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
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