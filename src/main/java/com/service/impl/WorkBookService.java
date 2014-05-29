package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IWorkBookDAO;
import com.po.WorkBook;
import com.service.IWorkBookService;
@Service
@Transactional
public class WorkBookService implements IWorkBookService{

    @Autowired
    private IWorkBookDAO workBookDAO;
    
    
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<WorkBook> queryAll() {
        return workBookDAO.queryAll();
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<WorkBook> query(String code) {
        return workBookDAO.query(code);
    }

    @Override
    @Transactional
    public void init(List<WorkBook> list) {
        for(WorkBook book : list){
            workBookDAO.create(book);
        }
    }

}
