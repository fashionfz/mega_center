package com.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="T_TEST_LOG")
public class CheckLog implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="TEST_ID",nullable = false,length=32)
    private String testId;
    @Column(name="ERROR_NOE",nullable = false)
    private Date errorNode;
    @Column(name="ERROR_RANG",nullable = false)
    private int errorRang;
    @Column(name="ERROR_TYPE")
    private int errorType;
    @Column(name="NEXT_CHECK")
    private int nextCheck;
    @Column(name="REMARK",length=255)
    private String remark;
    @Column(name="HAND_TIME")
    private Date handTime;
    @Column(name="STATUS")
    private int status;
    @Column(name="USER_ID",length=32)
    private String userId;
    @Column(name="DEVICE_ID",length=32)
    private String deviceId;
    
    
    
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTestId() {
        return testId;
    }
    public void setTestId(String testId) {
        this.testId = testId;
    }
    public Date getErrorNode() {
        return errorNode;
    }
    public void setErrorNode(Date errorNode) {
        this.errorNode = errorNode;
    }
    public int getErrorRang() {
        return errorRang;
    }
    public void setErrorRang(int errorRang) {
        this.errorRang = errorRang;
    }
    public int getErrorType() {
        return errorType;
    }
    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
    public int getNextCheck() {
        return nextCheck;
    }
    public void setNextCheck(int nextCheck) {
        this.nextCheck = nextCheck;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getHandTime() {
        return handTime;
    }
    public void setHandTime(Date handTime) {
        this.handTime = handTime;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    
}
