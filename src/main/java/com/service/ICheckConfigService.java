package com.service;

import java.util.List;

import com.po.CheckConfig;

public interface ICheckConfigService {

    public void saveConfig(List<CheckConfig> list);
    
    public void updateConfig(List<CheckConfig> list);
    
    public List<CheckConfig> queryAll();
    
    public CheckConfig query(String code);
}
