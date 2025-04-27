package org1.model;

public class Basil {
    private String id;
    private String location;
    
    // Default constructor for JSON deserialization
    public Basil() {}
    
    public Basil(String id, String location) {
        this.id = id;
        this.location = location;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}