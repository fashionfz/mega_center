package com.dto;

import java.util.List;

import com.po.Device;

public class DeviceDTO {

    private long total;
    private List<Device> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<Device> getRows() {
        return rows;
    }
    public void setRows(List<Device> rows) {
        this.rows = rows;
    }
    
}
