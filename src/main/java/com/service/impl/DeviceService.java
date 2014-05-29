package com.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constanst;
import com.common.server.AppLicenceUtil;
import com.dao.IDeviceDAO;
import com.dto.QueryDeviceDTO;
import com.po.Device;
import com.service.IDeviceService;

@Service
@Transactional
public class DeviceService implements IDeviceService{

    @Autowired
    private IDeviceDAO deviceDAO;
    
    @Override
    @Transactional
    public List<Device> query(QueryDeviceDTO dto,int page,int row) {
        return deviceDAO.query(dto,page,row);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long getAllCount() {
        return deviceDAO.getAllCount();
    }

    @Override
    @Transactional
    public int create(Device device) {
        if(device.getId()!=null&&!"".equals(device.getId())){
            deviceDAO.update(device);
            return Constanst.REQUEST_SUCCESS;
        }else{
            try{
                int deviceCount = AppLicenceUtil.videoCount();
                long count = deviceDAO.getAllCount();
                if(count>=deviceCount)
                    return Constanst.DEVICE_OUT_DATABASE; 
                device.setId(null);
                deviceDAO.create(device);
                return Constanst.REQUEST_SUCCESS;
            }catch(Exception e){
                return Constanst.LICENCE_ERROR; 
            }
        }
    }

    @Override
    @Transactional
    public void savePlot(String deviceId, String plot) {
        deviceDAO.savePlot(deviceId, plot);
    }

    @Override
    @Transactional
    public int deleteDevice(String deviceId) {
        deviceDAO.deleteGroupDevice(deviceId);
        return deviceDAO.deleteDevice(deviceId);
    }

    @Override
    @Transactional
    public int create(List<Device> list) {
        try{
            int deviceCount = AppLicenceUtil.videoCount();
            long count = deviceDAO.getAllCount();
            if(count+list.size()>deviceCount)
                return Constanst.DEVICE_OUT_DATABASE;
            for(Device device : list){
                deviceDAO.create(device);
            }
            return Constanst.REQUEST_SUCCESS;
        }catch(Exception e){
            return Constanst.LICENCE_ERROR; 
        }
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long queryCount(QueryDeviceDTO dto) {
        return deviceDAO.queryCount(dto);
    }

    @Override
    @Transactional
    public List<Device> queryCheck(String[] gourpIds) {
        Set<Device> set = new HashSet<Device>();
        for(String groupId : gourpIds){
            set.addAll(deviceDAO.queryCheck(groupId));
        }
        return new ArrayList<Device>(set);
    }

}
