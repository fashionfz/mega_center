package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.po.SysMenu;
import com.service.IMenuService;

@Controller
public class MenuController {

    @Autowired
    private IMenuService menuService;
    
    @RequestMapping(value="/queryMenu")
    public @ResponseBody List<SysMenu> queryAll(){
        List<SysMenu> list = menuService.queryAll();
        return list;
    }
    
    
    @RequestMapping(value="/queryAnthMenu")
    public @ResponseBody List<SysMenu> queryAnth(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("roleId");
        return menuService.queryAnth(userId);
    }
    
    @RequestMapping(value="/queryUnAnthMenu")
    public @ResponseBody List<SysMenu> queryUnAnth(HttpServletRequest request,HttpServletResponse response){
        String userId = request.getParameter("roleId");
        return menuService.queryUnAnth(userId);
    }
    
    
    @RequestMapping(value = "/addMenu",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
    @ResponseBody
    public SysMenu create(@RequestBody SysMenu menu) throws Exception{
        menuService.create(menu);
        return menu; 
    }
    
    @RequestMapping(value="/deleteMenu")
    public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
        String menuId=request.getParameter("menuId");
        if(menuId!=null&&!"".equals(menuId)){
            return menuService.deleteMenu(menuId);
        }else{
            return 0;
        }
    }
    
    @RequestMapping(value="/queryMenuByUser")
    public @ResponseBody List<SysMenu> queryMenu(HttpServletRequest request,HttpServletResponse response){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return menuService.queryByUser(username);
    }
}
