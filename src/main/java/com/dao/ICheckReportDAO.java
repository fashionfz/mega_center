package com.dao;

import java.util.Date;
import java.util.List;

import com.po.CheckLog;
import com.po.CheckReport;

public interface ICheckReportDAO {

    public List<CheckReport> queryAll(String mainId,int page,int rows);
    
    public long queryCount(String mainId);
    
    public List<CheckLog> queryCheckLog(String testId);
    
    public void clearLog(Date date);
    
    public void clearReport(Date date);
    
    public String save(CheckReport report);
    
    public void save(CheckLog log);
    
}
