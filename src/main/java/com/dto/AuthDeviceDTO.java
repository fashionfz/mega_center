package com.dto;

import java.util.List;

public class AuthDeviceDTO {

    private long total;
    private List<GroupDeviceDTO> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<GroupDeviceDTO> getRows() {
        return rows;
    }
    public void setRows(List<GroupDeviceDTO> rows) {
        this.rows = rows;
    }
    
}
