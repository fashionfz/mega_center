package com.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_TEST_REPORT")
public class CheckReport implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="DEVICE_ID",nullable = false,length=32)
    private String deviceId;
    @Column(name="MAIN_ID",nullable = false,length=32)
    private String mainId;
    @Column(name="VIC_NAME",length=255)
    private String vicName;
    @Column(name="DVR_NAME",length=255)
    private String dvrName;
    @Column(name="DVR_IP",length=255)
    private String dvrIp;
    @Column(name="VIC_TYPE")
    private int vicType;
    @Column(name="CHECK_RESULT")
    private int checkResult;
    @Column(name="TEST_TIME")
    private Date testTime;
    @Column(name="TIME_STR")
    private String timeStr;
    @Column(name="RECORD_CYCLE_LOCAL")
    private int LocalRecordCycle;
    @Column(name="RECORD_CYCLE_REMOTE")
    private int RemoteRecordCycle;
    @Column(name="CHECK_DAY_TYPE")
    private int checkDayType;
    
    
    
    
    
    
    
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getVicName() {
        return vicName;
    }
    public void setVicName(String vicName) {
        this.vicName = vicName;
    }
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
    public int getVicType() {
        return vicType;
    }
    public void setVicType(int vicType) {
        this.vicType = vicType;
    }
    public int getCheckResult() {
        return checkResult;
    }
    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }
    public Date getTestTime() {
        return testTime;
    }
    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    
    public int getLocalRecordCycle() {
        return LocalRecordCycle;
    }
    public void setLocalRecordCycle(int localRecordCycle) {
        LocalRecordCycle = localRecordCycle;
    }
    public int getRemoteRecordCycle() {
        return RemoteRecordCycle;
    }
    public void setRemoteRecordCycle(int remoteRecordCycle) {
        RemoteRecordCycle = remoteRecordCycle;
    }
    public String getMainId() {
        return mainId;
    }
    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
    public String getTimeStr() {
        return timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public int getCheckDayType() {
        return checkDayType;
    }
    public void setCheckDayType(int checkDayType) {
        this.checkDayType = checkDayType;
    }
    
 
    
    
    
}
