package com.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SYS_SERVICE")
public class SysService implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    @Column(name="SERVICE_NAME",nullable = false,length=255)
    private String name;
    @Column(name="SERVICE_CODE",nullable = false,length=255)
    private String code;
    @Column(name="SERVICE_IP",nullable = false,length=255)
    private String ip;
    @Column(name="SERVICE_PORT",nullable = false)
    private int port;
    @Column(name="SERVICE_URL",nullable = false,length=255)
    private String url;
    @Column(name="AC_NAME",nullable = false,length=255)
    private String acName;
    @Column(name="AC_PASSWORD",nullable = false,length=255)
    private String acPwd;
    @Column(name="STATUS",nullable = false)
    private int status;
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAcName() {
        return acName;
    }
    public void setAcName(String acName) {
        this.acName = acName;
    }
    public String getAcPwd() {
        return acPwd;
    }
    public void setAcPwd(String acPwd) {
        this.acPwd = acPwd;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
    
}
