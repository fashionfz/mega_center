package com.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_WORK_BOOK")
public class WorkBook implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="CODE",nullable = false,length=255)
    private String code;
    @Column(name="NAME",nullable = false,length=255)
    private String name;
    @Column(name="BOOK_KEY",nullable = false)
    private int key;
    @Column(name="VALUE",nullable = false,length=255)
    private String value;
    @Column(name="STATUS",nullable = false)
    private int status;
    @Column(name="BOOK_SORT",nullable = false)
    private int sort;
    
    public WorkBook(){
        
    }
    
    public WorkBook(String code,String name,int key,String value,int sort,int status){
        this.name=name;
        this.code=code;
        this.key=key;
        this.value=value;
        this.sort=sort;
        this.status=status;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
 
    
    
}
