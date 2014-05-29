package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IMenuDAO;
import com.po.SysMenu;
import com.service.IMenuService;

@Service
@Transactional
public class MenuService implements IMenuService{

    @Autowired
    private IMenuDAO menuDAO;
    
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysMenu> queryAll() {
        return menuDAO.queryAll();
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysMenu> queryAnth(String roleId) {
        return menuDAO.queryAnth(roleId);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysMenu> queryUnAnth(String roleId) {
        return menuDAO.queryUnAnth(roleId);
    }

    @Override
    @Transactional
    public void create(SysMenu menu) {
       if(menu.getId()==null||"".equals(menu.getId())){
           menu.setId(null);
           menuDAO.create(menu);
       }else{
           menuDAO.updateMenu(menu);
       }
        
    }

    @Override
    @Transactional
    public int deleteMenu(String menuId) {
        menuDAO.clearMenuR(menuId);
        return menuDAO.deleteMenu(menuId);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysMenu> queryByUser(String username) {
        return menuDAO.queryByUser(username);
    }

    @Override
    @Transactional
    public void init(List<SysMenu> list) {
        for(SysMenu menu : list){
            menuDAO.create(menu);
        }
    }

}
