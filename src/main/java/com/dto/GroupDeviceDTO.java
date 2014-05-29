package com.dto;

public class GroupDeviceDTO implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String vicId;
    private String vicName;
    private String groupId;
    private int recordType;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getVicId() {
        return vicId;
    }
    public void setVicId(String vicId) {
        this.vicId = vicId;
    }
    public String getVicName() {
        return vicName;
    }
    public void setVicName(String vicName) {
        this.vicName = vicName;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public int getRecordType() {
        return recordType;
    }
    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }

    
}
