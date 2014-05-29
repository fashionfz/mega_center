package com.dto;

public class QueryDeviceDTO implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String dvrName;
    private String dvrIp;
    private String dvrUserName;
    private String dvrPassword;
    private String type;
    public String getDvrName() {
        return dvrName;
    }
    public void setDvrName(String dvrName) {
        this.dvrName = dvrName;
    }
    public String getDvrIp() {
        return dvrIp;
    }
    public void setDvrIp(String dvrIp) {
        this.dvrIp = dvrIp;
    }
    public String getDvrUserName() {
        return dvrUserName;
    }
    public void setDvrUserName(String dvrUserName) {
        this.dvrUserName = dvrUserName;
    }
    public String getDvrPassword() {
        return dvrPassword;
    }
    public void setDvrPassword(String dvrPassword) {
        this.dvrPassword = dvrPassword;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
