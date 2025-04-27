package org1.model;

public class Owner {
    private String orgId;
    private String name;
    
    // Default constructor for JSON deserialization
    public Owner() {}
    
    public Owner(String orgId, String name) {
        this.orgId = orgId;
        this.name = name;
    }
    
    public String getOrgId() {
        return orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}