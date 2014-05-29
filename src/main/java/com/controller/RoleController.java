package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.po.SysRole;
import com.service.IRoleService;

@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;
    
    @RequestMapping(value="/queryRole")
    public @ResponseBody List<SysRole> queryAll(){
        return roleService.queryAll();
    }
    
    @RequestMapping(value="/queryAnthRole")
    public @ResponseBody List<SysRole> queryAnth(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("userId");
        return roleService.queryAnth(userId);
    }
    
    @RequestMapping(value="/queryUnAnthRole")
    public @ResponseBody List<SysRole> queryUnAnth(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("userId");
        return roleService.queryUnAnth(userId);
    }
    
    @RequestMapping(value = "/addRole",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public SysRole addRole(@RequestBody SysRole role) throws Exception{
        roleService.save(role);
        return role;
    }
    
    
    @RequestMapping(value="/roleAuthMenu")
    public void userAnthRole(HttpServletRequest request,HttpServletResponse response){
        String roleId = request.getParameter("roleId");
        String menuIds = request.getParameter("menuIds");
        if(menuIds!=null&&!"".equals(menuIds))
            roleService.roleAnthMenu(roleId, menuIds.split(":"));
        else
            roleService.roleAnthMenu(roleId, new String[0]);
    }
    
    
    @RequestMapping(value="/deleteRole")
    public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
        String roleId=request.getParameter("roleId");
        if(roleId!=null&&!"".equals(roleId)){
            return roleService.deleteRole(roleId);
        }else{
            return 0;
        }
    }
}
