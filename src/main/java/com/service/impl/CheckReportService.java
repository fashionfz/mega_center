package com.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ICheckConfigDAO;
import com.dao.ICheckMainDAO;
import com.dao.ICheckReportDAO;
import com.dao.IDeviceDAO;
import com.po.CheckConfig;
import com.po.CheckLog;
import com.po.CheckMain;
import com.po.CheckReport;
import com.po.Device;
import com.service.ICheckReportService;
import com.task.CheckDviceThread;
import com.util.XmlUtil;

@Service
@Transactional
public class CheckReportService implements ICheckReportService{
    
    

    @Autowired
    private ICheckConfigDAO checkConfigDAO;
    
    
    @Autowired
    private ICheckReportDAO checkReportDAO;
    
    @Autowired
    private ICheckMainDAO checkMainDAO;
    
    @Autowired
    private IDeviceDAO deviceDAO;
    
    
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<CheckReport> queryAll(String mainId,int page,int rows) {
        return checkReportDAO.queryAll(mainId,page,rows);
    }


    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<CheckLog> queryCheckLog(String testId) {
        return checkReportDAO.queryCheckLog(testId);
    }


    @Override
    @Transactional
    public void clearLog() {
        Calendar now = Calendar.getInstance();
        CheckConfig config = checkConfigDAO.query("logTimeRang");
        if(config!=null){
            String day = config.getItemValue();
            now.add(Calendar.DATE, 0-Integer.parseInt(day));
            checkReportDAO.clearLog(now.getTime());
            checkReportDAO.clearReport(now.getTime());
        }
    }


    @Override
    @Transactional
    public void saveCheck(String mainId, Date time, String xml,String mode) {
        Device dev=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CheckReport report = new CheckReport();
        XmlUtil util = new XmlUtil();
        Document doc = util.StringToXml(xml);
        Element root = doc.getRootElement();
        Iterator<Element> iters = root.elementIterator();
        String reportId="";
        String deviceId="";
        while(iters.hasNext()){
            Element device = iters.next();
            report.setMainId(mainId);
            deviceId=device.attribute("id").getValue();
            dev = deviceDAO.findById(deviceId);
            report.setDeviceId(deviceId);
            report.setCheckDayType(1);
            report.setCheckResult(1);
            report.setDvrIp(dev.getDvrIp());
            report.setDvrName(dev.getDvrName());
            report.setVicName(dev.getVicName());
            report.setLocalRecordCycle(dev.getRecordCycle());
            report.setRemoteRecordCycle(dev.getRecordCycleRemote());
            report.setVicType(dev.getVicType());
            report.setTimeStr(df.format(time));
            report.setTestTime(time);
            reportId = checkReportDAO.save(report);
            CheckDviceThread.errorCount++;
            
            Iterator<Element> iters2 = device.elementIterator();
            while(iters2.hasNext()){
                Element error = iters2.next();
                CheckLog log = new CheckLog();
                try {
                    log.setErrorNode(df.parse(error.attribute("time").getValue()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                log.setErrorRang(Integer.parseInt(error.attribute("rang").getValue()));
                log.setTestId(reportId);
                log.setDeviceId(deviceId);
                
                if("2".equals(mode)){//循环
                    
                }else{
                    checkReportDAO.save(log);
                }
            }
        }
        CheckDviceThread.checkCount++;
        
    }


    @Override
    @Transactional
    public String saveMain(String name, Date time,int allCount) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CheckMain main = new CheckMain();
        main.setName(name);
        main.setTimeStr(df.format(time));
        main.setCheckTime(time);
        main.setAllCount(allCount);
        return checkMainDAO.save(main);
    }


    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public CheckMain queryCheckMain(String mainId) {
        return checkMainDAO.queryCheckMain(mainId);
    }


    @Override
    @Transactional
    public void updateCheckMain(String id, int checkCount, int errorCount) {
        checkMainDAO.updateCheckMain(id, checkCount, errorCount);
    }


    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long queryCount(String mainId) {
        return checkReportDAO.queryCount(mainId);
    }

}
