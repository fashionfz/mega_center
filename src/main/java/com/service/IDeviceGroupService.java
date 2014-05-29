package com.service;

import java.math.BigInteger;
import java.util.List;

import com.dto.GroupDeviceDTO;
import com.po.Device;
import com.po.DeviceGroup;

public interface IDeviceGroupService {

    public List<DeviceGroup> queryAll(int page,int rows);
    
    public long queryCount();
    
    public void create(DeviceGroup group);
    
    public int deleteGroup(String groupId);
    
    public List<GroupDeviceDTO> queryByGroup(String groupId,int page,int rows);
    
    public long queryByGroup(String groupId);
    
    public List<Device> queryUnGroup(String groupId,int page,int rows);
    
    public BigInteger queryUnGroup(String groupId);
    
    public void addGroupDevice(String groupId,String[] deviceIds,String[] types);
    
    public void deleteGroupDevice(String groupId,String[] deviceIds);
    
    public void updateGroupDevice(String groupId,String deviceId,String type);
    
    
    public String getNames(String[] ids);
    
}
