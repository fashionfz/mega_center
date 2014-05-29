package com.dao;

import java.util.List;

import com.po.CheckMain;

public interface ICheckMainDAO {

    public List<CheckMain> query(int page,int rows);
    
    public long queryCount();
    
    public String save(CheckMain main);
    
    public CheckMain queryCheckMain(String mainId);
    
    public void updateCheckMain(String id,int checkCount,int errorCount);
}
