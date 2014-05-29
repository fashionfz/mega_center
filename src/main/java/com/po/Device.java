package com.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_DEVICE")
public class Device implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="VIC_NAME",nullable = false,length=255)
    private String vicName;
    @Column(name="DVR_NAME",nullable = false,length=255)
    private String dvrName;
    @Column(name="DVR_IP",nullable = true,length=255)
    private String dvrIp;
    @Column(name="DVR_PORT")
    private int dvrPort;
    
    @Column(name="DVR_USERNAME",nullable = true,length=255)
    private String dvrUserName;
    @Column(name="DVR_PASSWORD",nullable = true,length=255)
    private String dvrPassword;
    @Column(name="LOCATION",nullable = true,length=255)
    private String location;
    @Column(name="RECORD_CYCLE",nullable = true)
    private int recordCycle;
    @Column(name="RECORD_CYCLE_REMOTE",nullable = true)
    private int recordCycleRemote;
    
    @Column(name="RECORD_PLOT",nullable = true,length=1000)
    private String recordPlot;
    @Column(name="RECORD_TYPE",nullable = true)
    private int recordType;
    @Column(name="CHANNEL",nullable = true)
    private int channel;
    @Column(name="VIC_TYPE",nullable = true)
    private int vicType;
    @Column(name="PLOT_TYPE")
    private int plotType;
    
    
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getRecordCycle() {
        return recordCycle;
    }
    public void setRecordCycle(int recordCycle) {
        this.recordCycle = recordCycle;
    }
    public String getRecordPlot() {
        return recordPlot;
    }
    public void setRecordPlot(String recordPlot) {
        this.recordPlot = recordPlot;
    }
    public int getRecordType() {
        return recordType;
    }
    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
    public int getChannel() {
        return channel;
    }
    public void setChannel(int channel) {
        this.channel = channel;
    }
    public int getVicType() {
        return vicType;
    }
    public void setVicType(int vicType) {
        this.vicType = vicType;
    }
    public int getDvrPort() {
        return dvrPort;
    }
    public void setDvrPort(int dvrPort) {
        this.dvrPort = dvrPort;
    }
    public int getRecordCycleRemote() {
        return recordCycleRemote;
    }
    public void setRecordCycleRemote(int recordCycleRemote) {
        this.recordCycleRemote = recordCycleRemote;
    }
    public int getPlotType() {
        return plotType;
    }
    public void setPlotType(int plotType) {
        this.plotType = plotType;
    }
    
    
    
    
    
}
