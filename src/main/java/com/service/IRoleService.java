package com.service;

import java.util.List;

import com.po.SysRole;

public interface IRoleService {

    public List<SysRole> queryAll();
    
    public List<SysRole> queryAnth(String userId);
    
    public List<SysRole> queryUnAnth(String userId);
    
    
    public void save(SysRole role);
    
    public void roleAnthMenu(String roleId,String[] menuIds);
    
    public int deleteRole(String roleId);
    
}
