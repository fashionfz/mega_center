package com.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ICheckConfigDAO;
import com.po.CheckConfig;
import com.service.ICheckConfigService;
import com.task.AutoJob;

@Service
@Transactional
public class CheckConfigService implements ICheckConfigService{

    @Autowired
    private ICheckConfigDAO checkConfigDAO;
    
    @Override
    @Transactional
    public void saveConfig(List<CheckConfig> list) {
        for(CheckConfig config : list)
        {
            checkConfigDAO.create(config);
        }
    }

    @Override
    @Transactional
    public void updateConfig(List<CheckConfig> list) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(CheckConfig config : list){
            if(checkConfigDAO.checkExits(config)>0){
                checkConfigDAO.update(config);
                
                if("beginTime".equals(config.getItemCode())){
                    Date begin;
                    try {
                        begin = df.parse(config.getItemValue());
                        System.out.println(begin.getSeconds()+" "+begin.getMinutes()+" "+begin.getHours()+" * * ?");
                        AutoJob.resetJob("checkTrigger", begin.getSeconds()+" "+begin.getMinutes()+" "+begin.getHours()+" * * ?");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                }
                else if("endTime".equals(config.getItemCode())){
                    Date begin;
                    try {
                        begin = df.parse(config.getItemValue());
                        System.out.println(begin.getSeconds()+" "+begin.getMinutes()+" "+begin.getHours()+" * * ?");
                        AutoJob.resetJob("downTrigger", begin.getSeconds()+" "+begin.getMinutes()+" "+begin.getHours()+" * * ?");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                }                
            }else{
                config.setId(null);
                checkConfigDAO.create(config);
            }
        }
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<CheckConfig> queryAll() {
        return checkConfigDAO.queryAll();
    }
    
    @Override
    @Transactional
    public CheckConfig query(String code){
        return checkConfigDAO.query(code);
    }

}
