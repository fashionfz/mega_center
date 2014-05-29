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

import com.dto.QueryUserDTO;
import com.po.SysUser;
import com.service.IUserService;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    
    
	@RequestMapping("/login.do")
	public void login(HttpServletRequest request){

	}

	@RequestMapping(value = "/addUser",method = {RequestMethod.POST},headers = {"content-type=application/json","content-type=application/xml"})
	@ResponseBody
	public int create(@RequestBody SysUser user) throws Exception{
        return userService.create(user);
	}
	
	@RequestMapping(value="/queryUser")
	public @ResponseBody QueryUserDTO queryUser(HttpServletRequest request,HttpServletResponse response){
	    String page = request.getParameter("page");
	    String rows = request.getParameter("rows");
	    QueryUserDTO dto = new QueryUserDTO();
	    long count = userService.getAllCount();
	    List<SysUser> list = userService.queryAll(Integer.parseInt(page),Integer.parseInt(rows));
	    dto.setTotal(count);
	    dto.setRows(list);
	    return dto;
	}
	
	@RequestMapping(value="/userAuthRole")
	public void userAnthRole(HttpServletRequest request,HttpServletResponse response){
	    String userId = request.getParameter("userId");
	    String roleIds = request.getParameter("roleIds");
	    if(roleIds!=null&&!"".equals(roleIds))
	        userService.userAnthRole(userId, roleIds.split(":"));
	    else
	        userService.userAnthRole(userId, new String[0]);
	}
	@RequestMapping(value="/deleteUser")
	public @ResponseBody int deleteUser(HttpServletRequest request,HttpServletResponse response){
	    String userId=request.getParameter("userId");
	    if(userId!=null&&!"".equals(userId)){
	        return userService.deleteUser(userId);
	    }else{
	        return 0;
	    }
	}

}
