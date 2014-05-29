package com.dao;

import java.util.List;

import com.po.RUserRole;
import com.po.SysUser;

public interface IUserDAO {

    public List<SysUser> queryAll(int page,int rows);
    
    public void create(SysUser user);
    
    public long getAllCount();
    
    public void saveUserAnth(RUserRole rur);
    
    public void delUserAnth(String userId);
    
    public int deleteUser(String userId);
    
    public void updateUser(SysUser user);
    
    public SysUser findById(String id);
}
