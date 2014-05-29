package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constanst;
import com.common.server.AppLicenceUtil;
import com.dao.IUserDAO;
import com.po.RUserRole;
import com.po.SysUser;
import com.service.IUserService;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private IUserDAO userDAO;
    
    
     private Md5PasswordEncoder encoder;
     
    
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String login(String name, String password) {
		return "success";
	}

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysUser> queryAll(int page,int rows) {
        return userDAO.queryAll(page,rows);
    }

    @Override
    @Transactional
    public int create(SysUser user) {
        try {
            int authCount = AppLicenceUtil.userCount();
            long dataCount = userDAO.getAllCount();
            if(dataCount>authCount)
                return Constanst.USER_OUT_DATABASE; 
        
            
            encoder = new Md5PasswordEncoder();
            if(user.getId()==null||"".equals(user.getId())){
                user.setId(null);
                user.setPassword(encoder.encodePassword(user.getPassword(), user.getName()));
                userDAO.create(user);
            }
            else{
                SysUser old = userDAO.findById(user.getId());
                if(!old.getPassword().equals(user.getPassword()))
                    user.setPassword(encoder.encodePassword(user.getPassword(), user.getName()));
                userDAO.updateUser(user);
            }
            return Constanst.REQUEST_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constanst.LICENCE_ERROR; 
        }
    }

    @Override
    @Transactional
    public long getAllCount() {
        return userDAO.getAllCount();
    }

    @Override
    @Transactional
    public void userAnthRole(String userId, String[] roleIds) {
        userDAO.delUserAnth(userId);
        for(String roleId : roleIds){
            RUserRole rur = new RUserRole();
            rur.setRoleId(roleId);
            rur.setUserId(userId);
            userDAO.saveUserAnth(rur);
        }
        
    }

    @Override
    @Transactional
    public int deleteUser(String userId) {
        userDAO.delUserAnth(userId);
        return userDAO.deleteUser(userId);
    }

    @Override
    @Transactional
    public void init(SysUser user) {
        encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword(user.getPassword(), user.getName()));
        userDAO.create(user);
    }

}
