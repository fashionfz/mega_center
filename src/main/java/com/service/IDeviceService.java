package com.service;

import java.util.List;

import com.dto.QueryDeviceDTO;
import com.po.Device;

public interface IDeviceService {

    public List<Device> query(QueryDeviceDTO dto,int page,int row);
    
    public long queryCount(QueryDeviceDTO dto);
    
    public long getAllCount();
    
    public int create(Device device);
    
    public void savePlot(String deviceId,String plot);
    
    public int deleteDevice(String deviceId);
    
    public int create(List<Device> list);
    
    public List<Device> queryCheck(String[] gourpIds);
}
