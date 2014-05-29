package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IServiceDAO;
import com.po.SysService;
import com.service.IServiceService;

@Service
@Transactional
public class ServiceService implements IServiceService{

    @Autowired
    private IServiceDAO serviceDAO;
    
    @Override
    @Transactional
    public SysService getByCode(String code) {
        return serviceDAO.getByCode(code);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<SysService> queryAll(int page, int rows) {
        return serviceDAO.queryAll(page, rows);
    }

    @Override
    @Transactional
    public int create(SysService service) {
        if(service.getId()==null||"".equals(service.getId())){
            service.setId(null);
            return serviceDAO.create(service);
        }else
            return serviceDAO.update(service);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long getAllCount() {
        return serviceDAO.getAllCount();
    }

    @Override
    @Transactional
    public int deleteUser(String serviceId) {
        return serviceDAO.deleteUser(serviceId);
    }

}
