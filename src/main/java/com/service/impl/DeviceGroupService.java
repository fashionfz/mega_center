package com.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IDeviceGroupDAO;
import com.dto.GroupDeviceDTO;
import com.po.Device;
import com.po.DeviceGroup;
import com.po.RGroupDevice;
import com.service.IDeviceGroupService;

@Service
@Transactional
public class DeviceGroupService implements IDeviceGroupService {

    @Autowired
    private IDeviceGroupDAO deviceGroupDAO;
    
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<DeviceGroup> queryAll(int page,int rows) {
        return deviceGroupDAO.queryAll(page,rows);
    }

    @Override
    @Transactional
    public void create(DeviceGroup group) {
        if(group.getId()==null||"".equals(group.getId())){
            group.setId(null);
            deviceGroupDAO.create(group);
        }else{
            deviceGroupDAO.update(group);
        }
        
    }

    @Override
    @Transactional
    public int deleteGroup(String groupId) {
        deviceGroupDAO.delRGroupDevice(groupId);
        return deviceGroupDAO.deleteGroup(groupId);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<GroupDeviceDTO> queryByGroup(String groupId,int page,int rows) {
        return deviceGroupDAO.queryByGroup(groupId,page,rows);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Device> queryUnGroup(String groupId,int page,int rows) {
        return deviceGroupDAO.queryUnGroup(groupId,page,rows);
    }

    @Override
    @Transactional
    public void addGroupDevice(String groupId, String[] deviceIds,
            String[] types) {
        if(deviceIds.length==types.length){
            for(int i=0;i<deviceIds.length;i++){
                RGroupDevice r = new RGroupDevice();
                r.setGroupId(groupId);
                r.setRecordType(Integer.parseInt(types[i]));
                r.setVicId(deviceIds[i]);
                deviceGroupDAO.saveRGroupDevice(r);
            }
        }
        
    }

    @Override
    @Transactional
    public void deleteGroupDevice(String groupId, String[] deviceIds) {
        for(String deviceId : deviceIds){
            deviceGroupDAO.delRGroupDevice(groupId, deviceId);
        }
    }

    @Override
    @Transactional
    public void updateGroupDevice(String groupId, String deviceId, String type) {
        deviceGroupDAO.updateGroupDevice(groupId, deviceId, Integer.parseInt(type));
        
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long queryCount() {
        return deviceGroupDAO.queryCount();
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long queryByGroup(String groupId) {
        return deviceGroupDAO.queryByGroup(groupId);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public BigInteger queryUnGroup(String groupId) {
        return deviceGroupDAO.queryUnGroup(groupId);
    }

    @Override
    @Transactional
    public String getNames(String[] ids) {
        String names="";
        for(String id : ids){
            names += deviceGroupDAO.findById(id).getName();
        }
        return names;
    }

}
