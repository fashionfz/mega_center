package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.CheckMainDTO;
import com.po.CheckMain;
import com.service.ICheckMainService;

@Controller
public class CheckMainController {

    @Autowired
    private ICheckMainService checkMainService;
 
    @RequestMapping(value="/queryCheckMain")
    public @ResponseBody CheckMainDTO queryCheckMain(HttpServletRequest request,HttpServletResponse response){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        CheckMainDTO dto = new CheckMainDTO();
        long count = checkMainService.queryCount();
        List<CheckMain> list = checkMainService.query(Integer.parseInt(page),Integer.parseInt(rows));
        dto.setTotal(count);
        dto.setRows(list);
        return dto;
    }
}
