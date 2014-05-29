package com.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.core.security.MyInvocationSecurityMetadataSource;
import com.dao.IRoleDAO;
import com.po.RRoleMenu;
import com.po.SysMenu;
import com.po.SysRole;
import com.service.IRoleService;

@Service
@Transactional
public class RoleService implements IRoleService{

    @Autowired
    private IRoleDAO roleDAO;
    
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysRole> queryAll() {
        return roleDAO.queryAll();
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysRole> queryAnth(String userId) {
        return roleDAO.queryAnth(userId);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysRole> queryUnAnth(String userId) {
        return roleDAO.queryUnAnth(userId);
    }

    @Override
    @Transactional
    public void save(SysRole role) {
        if(role.getId()==null||"".equals(role.getId())){
            role.setId(null);
            roleDAO.save(role);
        }else{
            roleDAO.updateRole(role);
        }
        
    }

    @Override
    @Transactional
    public void roleAnthMenu(String roleId, String[] menuIds) {
        roleDAO.delRoleAnth(roleId);
        for(String menuId : menuIds){
            RRoleMenu rrm = new RRoleMenu();
            rrm.setRoleId(roleId);
            rrm.setMenuId(menuId);
            roleDAO.saveRoleAnth(rrm);
        }
        
        //重新加载security
        MyInvocationSecurityMetadataSource.getResourceMap().clear();
        List<Object[]> list = roleDAO.querySecurity();
        for(Object[] objs : list){
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            SysRole role = (SysRole) objs[0];
            SysMenu menu = (SysMenu) objs[1];
            if(MyInvocationSecurityMetadataSource.getResourceMap().get(menu.getUrl())!=null&&
                    !"".equals(MyInvocationSecurityMetadataSource.getResourceMap().get(menu.getUrl()))){
                ConfigAttribute ca = new SecurityConfig(role.getName());
                MyInvocationSecurityMetadataSource.getResourceMap().get(menu.getUrl()).add(ca);
            }else{
                ConfigAttribute ca = new SecurityConfig(role.getName());
                atts.add(ca);
                MyInvocationSecurityMetadataSource.getResourceMap().put(menu.getUrl(), atts);
            }
        }
    }

    @Override
    @Transactional
    public int deleteRole(String roleId) {
        roleDAO.deluserAnth(roleId);
        roleDAO.delRoleAnth(roleId);
        return roleDAO.deleteRole(roleId);
    }

}
