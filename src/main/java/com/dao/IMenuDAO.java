package com.dao;

import java.util.List;

import com.po.SysMenu;

public interface IMenuDAO {

    public List<SysMenu> queryAll();
    
    public List<SysMenu> queryAnth(String roleId);
    
    public List<SysMenu> queryUnAnth(String roleId);
    
    public void create(SysMenu menu);
    
    
    public void updateMenu(SysMenu menu);
    
    public int deleteMenu(String menuId);
    
    public void clearMenuR(String menuId);
    
    public List<SysMenu> queryByUser(String username);
    
}
