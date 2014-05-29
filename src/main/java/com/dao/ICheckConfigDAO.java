package com.dao;

import java.util.List;

import com.po.CheckConfig;

public interface ICheckConfigDAO {

    public void create(CheckConfig config);
    
    public int update(CheckConfig config);
    
    public List<CheckConfig> queryAll();
    
    public long checkExits(CheckConfig config);
    
    public CheckConfig query(String code);
}
