package com.dao;

import java.util.List;

import com.po.RRoleMenu;
import com.po.SysRole;

public interface IRoleDAO {

    public List<SysRole> queryAll();
    
    public List<SysRole> queryAnth(String userId);
    
    public List<SysRole> queryUnAnth(String userId);
    
    public void save(SysRole role);
    
    public void saveRoleAnth(RRoleMenu rrm);
    
    public void delRoleAnth(String roleId);
    
    public void deluserAnth(String roleId);
    
    public int deleteRole(String roleId);
    
    public void updateRole(SysRole role);
    
    
    public List<Object[]> querySecurity();
}
