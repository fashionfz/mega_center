package com.service;

import java.util.List;

import com.po.CheckMain;

public interface ICheckMainService {

    public List<CheckMain> query(int page,int rows);
    
    public long queryCount();
}
