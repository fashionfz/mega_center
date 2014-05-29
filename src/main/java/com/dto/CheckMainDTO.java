package com.dto;

import java.util.List;

import com.po.CheckMain;

public class CheckMainDTO {

    private long total;
    private List<CheckMain> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<CheckMain> getRows() {
        return rows;
    }
    public void setRows(List<CheckMain> rows) {
        this.rows = rows;
    }
    
}
