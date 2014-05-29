package com.service;

import java.util.Date;
import java.util.List;

import com.po.CheckLog;
import com.po.CheckMain;
import com.po.CheckReport;

public interface ICheckReportService {

    public List<CheckReport> queryAll(String mainId,int page,int rows);
    
    public long queryCount(String mainId);
    
    public List<CheckLog> queryCheckLog(String testId);
    
    
    public void clearLog();
    
    public String saveMain(String name,Date time,int allCount);
    
    public void saveCheck(String mainId,Date time,String xml,String mode);
    
    public CheckMain queryCheckMain(String mainId);
    
    
    public void updateCheckMain(String id,int checkCount,int errorCount);
}
