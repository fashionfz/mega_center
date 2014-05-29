package com.service;

import java.util.List;

import com.po.WorkBook;

public interface IWorkBookService {

    public List<WorkBook> queryAll();
    
    public List<WorkBook> query(String code);
    
    public void init(List<WorkBook> list);
}
