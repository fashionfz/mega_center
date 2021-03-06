package com.dao;

import java.math.BigInteger;
import java.util.List;

import com.dto.GroupDeviceDTO;
import com.po.Device;
import com.po.DeviceGroup;
import com.po.RGroupDevice;

public interface IDeviceGroupDAO {

    public List<DeviceGroup> queryAll(int page,int rows);
    
    public long queryCount();
    
    public void create(DeviceGroup group);
    
    public void update(DeviceGroup group);
    
    public int deleteGroup(String groupId);
    
    public List<GroupDeviceDTO> queryByGroup(String groupId,int page,int rows);
    
    public long queryByGroup(String groupId);
    
    public List<Device> queryUnGroup(String groupId,int page,int rows);
    
    public BigInteger queryUnGroup(String groupId);
    
    public void saveRGroupDevice(RGroupDevice r);
    
    public void delRGroupDevice(String groupId,String deviceId);
    
    public void delRGroupDevice(String groupId);
    
    public void updateGroupDevice(String groupId, String deviceId, int type);
    
    public DeviceGroup findById(String id);
}
