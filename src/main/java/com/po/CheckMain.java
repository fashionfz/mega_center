package com.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_TEST_MAIN")
public class CheckMain  implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="NAME",nullable = false,length=255)
    private String name;
    @Column(name="CHECK_TIME",nullable = false)
    private Date checkTime;
    @Column(name="TIME_STR",nullable = false,length=20)
    private String timeStr;
    @Column(name="ALL_COUNT",nullable = false,length=32)
    private int allCount;
    @Column(name="CHECK_COUNT",nullable = false)
    private int checkCount;
    @Column(name="ERROR_COUNT",nullable = false)
    private int errorCount;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCheckTime() {
        return checkTime;
    }
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    public String getTimeStr() {
        return timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public int getAllCount() {
        return allCount;
    }
    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
    public int getCheckCount() {
        return checkCount;
    }
    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }
    public int getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    
    
    
}
