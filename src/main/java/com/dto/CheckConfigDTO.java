package com.dto;

public class CheckConfigDTO implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String beginTime;
    private String endTime;
    private int checkCount;
    private int checkType;
    private int alarmType;
    private int logTimeRang;
    private String deviceGroup;
    private String centerTime;
    private int centerType;
    private String dvrTime;
    private int dvrType;
    
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public int getCheckCount() {
        return checkCount;
    }
    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }
    public int getCheckType() {
        return checkType;
    }
    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }
    public int getAlarmType() {
        return alarmType;
    }
    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }
    public int getLogTimeRang() {
        return logTimeRang;
    }
    public void setLogTimeRang(int logTimeRang) {
        this.logTimeRang = logTimeRang;
    }
    public String getDeviceGroup() {
        return deviceGroup;
    }
    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }
    public String getCenterTime() {
        return centerTime;
    }
    public void setCenterTime(String centerTime) {
        this.centerTime = centerTime;
    }
    public int getCenterType() {
        return centerType;
    }
    public void setCenterType(int centerType) {
        this.centerType = centerType;
    }
    public String getDvrTime() {
        return dvrTime;
    }
    public void setDvrTime(String dvrTime) {
        this.dvrTime = dvrTime;
    }
    public int getDvrType() {
        return dvrType;
    }
    public void setDvrType(int dvrType) {
        this.dvrType = dvrType;
    }
    
    
}
