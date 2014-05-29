package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.DeviceDTO;
import com.dto.QueryDeviceDTO;
import com.po.Device;
import com.service.IDeviceService;

@Controller
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;
    
    
    @RequestMapping(value="/queryDevice")
    public @ResponseBody DeviceDTO query(HttpServletRequest request,HttpServletResponse response){
        QueryDeviceDTO dto = new QueryDeviceDTO();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dvrName = request.getParameter("dvrName");
        String dvrIp = request.getParameter("dvrIp");
        String dvrUserName = request.getParameter("dvrUserName");
        String dvrPassword = request.getParameter("dvrPassword");
        String type = request.getParameter("type");
        dto.setDvrName(dvrName);
        dto.setDvrIp(dvrIp);
        dto.setDvrUserName(dvrUserName);
        dto.setDvrPassword(dvrPassword);
        dto.setType(type);
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        long count = deviceService.queryCount(dto);
        List<Device> list = deviceService.query(dto,Integer.parseInt(page),Integer.parseInt(rows));
        DeviceDTO result = new DeviceDTO();
        result.setTotal(count);
        result.setRows(list);
        return result;
    }
    
    @RequestMapping(value="/queryDeviceCount")
    public @ResponseBody long queryCount(){
        return deviceService.getAllCount();
    }
    
    
    @RequestMapping(value = "/addDevice",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public int create(@RequestBody Device device) throws Exception{
        return deviceService.create(device);
    }
    
    @RequestMapping(value="/savePlot")
    public void userAnthRole(HttpServletRequest request,HttpServletResponse response){
        String deviceId = request.getParameter("deviceId");
        String plot = request.getParameter("plot");
        deviceService.savePlot(deviceId, plot);
    }
    
    @RequestMapping(value="/deleteDevice")
    public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
        String deviceId=request.getParameter("deviceId");
        if(deviceId!=null&&!"".equals(deviceId)){
            return deviceService.deleteDevice(deviceId);
        }else{
            return 0;
        }
    }
}
