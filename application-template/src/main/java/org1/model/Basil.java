package org1.model;

import java.util.List;

/**
 * Represents a basil plant in the supply chain.
 */
public class Basil {
    private String qrCode;
    private Long creationTimestamp;
    private String station;      // Changed from location/origin to station
    private String currentStatus;
    private String currentGps;   // Current geographical location
    private String temperature;  // Temperature measurement
    private String humidity;     // Humidity measurement
    private Owner currentOwner;
    private List<BasilLeg> transportHistory;
    
    // Default constructor for JSON deserialization
    public Basil() {}
    
    // Create request constructor with all fields
    public Basil(String id, String station, String currentGps, String temperature, String humidity) {
        this.qrCode = id;
        this.station = station;
        this.currentGps = currentGps;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    // Create request constructor with temperature and humidity
    public Basil(String id, String station, String temperature, String humidity) {
        this.qrCode = id;
        this.station = station;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    // Create request constructor (just needs ID and station)
    public Basil(String id, String station) {
        this.qrCode = id;
        this.station = station;
    }
    
    // Full constructor matching chaincode model
    public Basil(String qrCode, Long creationTimestamp, String station, 
                String currentStatus, String currentGps, 
                Owner currentOwner, List<BasilLeg> transportHistory) {
        this.qrCode = qrCode;
        this.creationTimestamp = creationTimestamp;
        this.station = station;
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

    // New getters and setters for station (replacing location)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    // Compatibility methods for frontend that might still use location
    public String getLocation() {
        return station;
    }
    
    public void setLocation(String location) {
        this.station = location;
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