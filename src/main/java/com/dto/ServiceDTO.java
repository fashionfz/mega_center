package com.dto;

import java.util.List;

import com.po.SysService;

public class ServiceDTO {

    private long total;
    private List<SysService> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<SysService> getRows() {
        return rows;
    }
    public void setRows(List<SysService> rows) {
        this.rows = rows;
    }
    
}
