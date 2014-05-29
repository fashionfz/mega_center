package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ICheckMainDAO;
import com.po.CheckMain;
import com.service.ICheckMainService;
@Service
@Transactional
public class CheckMainService implements ICheckMainService{

    @Autowired
    private ICheckMainDAO checkMainDAO;
    
    @Override
    @Transactional
    public List<CheckMain> query(int page,int rows) {
        return checkMainDAO.query(page,rows);
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long queryCount() {
        return checkMainDAO.queryCount();
    }

}
