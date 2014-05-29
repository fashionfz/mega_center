package com.dao;

import java.util.List;

import com.po.SysService;

public interface IServiceDAO {

    public SysService getByCode(String code);
    
    public List<SysService> queryAll(int page,int rows);
    
    public int create(SysService user);
    
    public int update(SysService user);
    
    public long getAllCount();
    
    public int deleteUser(String serviceId);
}
