package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.ServiceDTO;
import com.po.SysService;
import com.service.IServiceService;

@Controller
public class ServiceController {
    
    @Autowired
    private IServiceService serviceService;

    @RequestMapping(value = "/addService",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public int create(@RequestBody SysService user) throws Exception{
        return serviceService.create(user);
    }
    
    @RequestMapping(value="/queryService")
    public @ResponseBody ServiceDTO queryService(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        ServiceDTO dto = new ServiceDTO();
        long count = serviceService.getAllCount();
        List<SysService> list = serviceService.queryAll(Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count);
        dto.setRows(list);
        return dto;
    }
    @RequestMapping(value="/deleteService")
    public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
        String userId=request.getParameter("serviceId");
        if(userId!=null&&!"".equals(userId)){
            return serviceService.deleteUser(userId);
        }else{
            return 0;
        }
    }
}
