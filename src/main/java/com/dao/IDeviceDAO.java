package com.dao;

import java.util.List;

import com.dto.QueryDeviceDTO;
import com.po.Device;

public interface IDeviceDAO {

    public List<Device> query(QueryDeviceDTO dto,int page,int row);
    
    public long queryCount(QueryDeviceDTO dto);
    
    public long getAllCount();
    
    public void create(Device device);
    
    public void savePlot(String deviceId,String plot);
    
    public int deleteDevice(String deviceId);
    
    
    public int deleteGroupDevice(String deviceId);
    
    
    public void update(Device device);
    
    
    public List<Device> queryCheck(String groupId);
    
    public Device findById(String id);
    
    
}
