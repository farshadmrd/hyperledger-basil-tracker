package org1.model;

public class BasilLeg {
    private Long timestamp;
    private String gps;
    private String temperature;
    private String humidity;
    private Owner owner;
    
    // Default constructor for JSON deserialization
    public BasilLeg() {}
    
    public BasilLeg(Long timestamp, String gps, String temperature, String humidity, Owner owner) {
        this.timestamp = timestamp;
        this.gps = gps;
        this.temperature = temperature;
        this.humidity = humidity;
        this.owner = owner;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}