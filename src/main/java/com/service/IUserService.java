package com.service;

import java.util.List;

import com.po.SysUser;

public interface IUserService {

	
	public String login(String name,String password);
	
	public List<SysUser> queryAll(int page,int rows);
	
	public int create(SysUser user);
	
	public long getAllCount();
	
	public void userAnthRole(String userId,String[] roleIds);
	
	public int deleteUser(String userId);
	
	public void init(SysUser user);
}
