package com.service;

import java.util.List;

import com.po.SysMenu;

public interface IMenuService {

    public List<SysMenu> queryAll();
    
    public List<SysMenu> queryAnth(String roleId);
    
    public List<SysMenu> queryUnAnth(String roleId);
    
    public void create(SysMenu menu);
    
    public int deleteMenu(String menuId);
    
    public List<SysMenu> queryByUser(String username);
    
    public void init(List<SysMenu> list);
    
}
