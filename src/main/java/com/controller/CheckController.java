package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.CheckReportDTO;
import com.po.CheckLog;
import com.po.CheckMain;
import com.po.CheckReport;
import com.service.ICheckReportService;

@Controller
public class CheckController {

    @Autowired
    private ICheckReportService checkReportService;
    
    @RequestMapping(value="/queryCheckReport")
    public @ResponseBody CheckReportDTO queryCheckReport(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        if(page==null||"".equals(page))
            page="0";
        String rows = request.getParameter("rows");
        if(rows==null||"".equals(rows))
            rows="0";
        String mainId = request.getParameter("mainId");
        CheckReportDTO dto = new CheckReportDTO();
        long count = checkReportService.queryCount(mainId);
        List<CheckReport> list = checkReportService.queryAll(mainId,Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count);
        dto.setRows(list);
        return dto;
    }
    
    @RequestMapping(value="/queryCheckCount")
    public @ResponseBody CheckMain queryCheckCount(HttpServletRequest request,HttpServletResponse response){
        String mainId = request.getParameter("mainId");
        return checkReportService.queryCheckMain(mainId);
    }
    
    @RequestMapping(value="/queryCheckLog")
    public @ResponseBody List<CheckLog> queryCheckLog(HttpServletRequest request,HttpServletResponse response){
        String testId = request.getParameter("testId");
        List<CheckLog> list = checkReportService.queryCheckLog(testId);
        return list;
    }
}
