package com.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_SYS_MENU")
public class SysMenu implements java.io.Serializable{

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
    @Column(name="ICON",nullable = true,length=255)
    private String icon;
    @Column(name="URL",nullable = false,length=255)
    private String url;
    @Column(name="STATUS",nullable = false)
    private int status;
    
    
    public SysMenu(){
        
    }
    
    public SysMenu(String name,String url,int status){
        this.name=name;
        this.url=url;
        this.status=status;
    }
    
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
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;

    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    
 
}
