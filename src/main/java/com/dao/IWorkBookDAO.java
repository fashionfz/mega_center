package com.dao;

import java.util.List;

import com.po.WorkBook;

public interface IWorkBookDAO {

    public List<WorkBook> queryAll();
    
    public List<WorkBook> query(String code);
    
    public void create(WorkBook book);
}
