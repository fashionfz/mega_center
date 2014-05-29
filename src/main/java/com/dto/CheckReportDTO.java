package com.dto;

import java.util.List;

import com.po.CheckReport;

public class CheckReportDTO {

    private long total;
    private List<CheckReport> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<CheckReport> getRows() {
        return rows;
    }
    public void setRows(List<CheckReport> rows) {
        this.rows = rows;
    }
    
    
}
