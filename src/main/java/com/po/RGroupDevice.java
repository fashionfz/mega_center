package com.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="R_GROUP_DEVICE")
public class RGroupDevice implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "ID", unique = true, nullable = false,length=32)
    private String id;
    @Column(name="VIC_ID",nullable = false,length=32)
    private String vicId;
    @Column(name="GROUP_ID",nullable = false,length=32)
    private String groupId;
    @Column(name="RECORD_TYPE",nullable = true)
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
