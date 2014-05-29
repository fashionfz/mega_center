package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.CheckConfigDTO;
import com.po.CheckConfig;
import com.service.ICheckConfigService;

@Controller
public class CheckConfigController {

    @Autowired
    private ICheckConfigService checkConfigService;
    
    @RequestMapping(value = "/saveCheckConfig",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public void saveCheckConfig(@RequestBody CheckConfigDTO dto){
        List<CheckConfig> list = new ArrayList<CheckConfig>();
        CheckConfig beginTime = new CheckConfig();
        beginTime.setItemCode("beginTime");
        beginTime.setItemValue(dto.getBeginTime());
        list.add(beginTime);
        
        CheckConfig endTime = new CheckConfig();
        endTime.setItemCode("endTime");
        endTime.setItemValue(dto.getEndTime());
        list.add(endTime);
        
        CheckConfig checkCount = new CheckConfig();
        checkCount.setItemCode("checkCount");
        checkCount.setItemValue(String.valueOf(dto.getCheckCount()));
        list.add(checkCount);
        
        
        CheckConfig checkType = new CheckConfig();
        checkType.setItemCode("checkType");
        checkType.setItemValue(String.valueOf(dto.getCheckType()));
        list.add(checkType);
        
        CheckConfig alarmType = new CheckConfig();
        alarmType.setItemCode("alarmType");
        alarmType.setItemValue(String.valueOf(dto.getAlarmType()));
        list.add(alarmType);
        
        CheckConfig logTimeRang = new CheckConfig();
        logTimeRang.setItemCode("logTimeRang");
        logTimeRang.setItemValue(String.valueOf(dto.getLogTimeRang()));
        list.add(logTimeRang);
        
        CheckConfig deviceGroup = new CheckConfig();
        deviceGroup.setItemCode("deviceGroup");
        deviceGroup.setItemValue(dto.getDeviceGroup());
        list.add(deviceGroup);
        
        CheckConfig centerTime = new CheckConfig();
        centerTime.setItemCode("centerTime");
        centerTime.setItemValue(dto.getCenterTime());
        list.add(centerTime); 
        
        CheckConfig centerType = new CheckConfig();
        centerType.setItemCode("centerType");
        centerType.setItemValue(String.valueOf(dto.getCenterType()));
        list.add(centerType);
        
        CheckConfig dvrTime = new CheckConfig();
        dvrTime.setItemCode("dvrTime");
        dvrTime.setItemValue(dto.getDvrTime());
        list.add(dvrTime); 
        
        CheckConfig dvrType = new CheckConfig();
        dvrType.setItemCode("dvrType");
        dvrType.setItemValue(String.valueOf(dto.getDvrType()));
        list.add(dvrType);   
        
        
        checkConfigService.updateConfig(list);
    }
    
    @RequestMapping(value="/queryCheckConfig")
    public @ResponseBody List<CheckConfig> queryConfig(){
        return checkConfigService.queryAll();
    }
}
