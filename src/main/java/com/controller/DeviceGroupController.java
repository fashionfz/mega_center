package com.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.AuthDeviceDTO;
import com.dto.DeviceDTO;
import com.dto.DeviceGroupDTO;
import com.dto.GroupDeviceDTO;
import com.po.Device;
import com.po.DeviceGroup;
import com.service.IDeviceGroupService;

@Controller
public class DeviceGroupController {

    @Autowired
    private IDeviceGroupService deviceGroupService;
    
    @RequestMapping(value="/queryGroup")
    public @ResponseBody DeviceGroupDTO queryGroup(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        if(page==null||"".equals(page))
            page="0";
        String rows = request.getParameter("rows");
        if(rows==null||"".equals(rows))
            rows="0";
        DeviceGroupDTO dto = new DeviceGroupDTO();
        long count = deviceGroupService.queryCount();
        List<DeviceGroup> list = deviceGroupService.queryAll(Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count);
        dto.setRows(list);
        return dto;
    }
    
    @RequestMapping(value = "/addGroup",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public DeviceGroup create(@RequestBody DeviceGroup group) throws Exception{
        deviceGroupService.create(group);
        return group; 
    }
    
    @RequestMapping(value="/deleteGroup")
    public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
        String groupId=request.getParameter("groupId");
        if(groupId!=null&&!"".equals(groupId)){
            return deviceGroupService.deleteGroup(groupId);
        }else{
            return 0;
        }
    }
    
    
    @RequestMapping(value="/queryByGroup")
    public @ResponseBody AuthDeviceDTO queryDeviceByGroup(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String groupId = request.getParameter("groupId");
        AuthDeviceDTO dto = new AuthDeviceDTO();
        long count = deviceGroupService.queryByGroup(groupId);
        List<GroupDeviceDTO> list = deviceGroupService.queryByGroup(groupId,Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count);
        dto.setRows(list);
        return dto;
    }
    
    
    @RequestMapping(value="/queryUnGroup")
    public @ResponseBody DeviceDTO queryUnGroup(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String groupId = request.getParameter("groupId");
        DeviceDTO dto = new DeviceDTO();
        BigInteger count = deviceGroupService.queryUnGroup(groupId);
        List<Device> list = deviceGroupService.queryUnGroup(groupId,Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count.longValue());
        dto.setRows(list);
        return dto;
    }
    
    @RequestMapping(value="/addGroupDevice")
    public void addGroupDevice(HttpServletRequest request,HttpServletResponse response){
        String groupId = request.getParameter("groupId");
        String deviceIds = request.getParameter("deviceIds");
        String types = request.getParameter("types");
        deviceGroupService.addGroupDevice(groupId, deviceIds.split(":"), types.split(":"));
    }
    
    @RequestMapping(value="/delGroupDevice")
    public void deleteGroupDevice(HttpServletRequest request,HttpServletResponse response){
        String groupId = request.getParameter("groupId");
        String deviceIds = request.getParameter("deviceIds");
        deviceGroupService.deleteGroupDevice(groupId, deviceIds.split(":"));
    }
    
    @RequestMapping(value="/upGroupDevice")
    public void updateGroupDevice(HttpServletRequest request,HttpServletResponse response){
        String groupId = request.getParameter("groupId");
        String deviceId = request.getParameter("deviceId");
        String type = request.getParameter("type");
        deviceGroupService.updateGroupDevice(groupId, deviceId,type);
    }
}
