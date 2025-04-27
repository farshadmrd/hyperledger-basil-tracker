package org1.model;

public class OwnershipTransferRequest {
    private String orgId;
    private String name;
    
    // Default constructor for JSON deserialization
    public OwnershipTransferRequest() {}
    
    public OwnershipTransferRequest(String orgId, String name) {
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