package com.service;

import java.util.List;

import com.po.SysService;

public interface IServiceService {

    public SysService getByCode(String code);
    
    public List<SysService> queryAll(int page,int rows);
    
    public int create(SysService user);
    
    public long getAllCount();
    
    public int deleteUser(String serviceId);
    
}
