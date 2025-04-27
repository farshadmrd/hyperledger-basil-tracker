package org1.model;

public class BasilStateUpdateRequest {
    private String gps;
    private Long timestamp;
    private String temperature;
    private String humidity;
    private String status;
    
    // Default constructor for JSON deserialization
    public BasilStateUpdateRequest() {}
    
    public BasilStateUpdateRequest(String gps, Long timestamp, String temperature, String humidity, String status) {
        this.gps = gps;
        this.timestamp = timestamp != null ? timestamp : System.currentTimeMillis() / 1000;
        this.temperature = temperature;
        this.humidity = humidity;
        this.status = status;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Long getTimestamp() {
        return timestamp != null ? timestamp : System.currentTimeMillis() / 1000;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}