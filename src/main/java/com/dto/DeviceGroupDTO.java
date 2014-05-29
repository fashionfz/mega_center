package com.dto;

import java.util.List;

import com.po.DeviceGroup;

public class DeviceGroupDTO {

    private long total;
    private List<DeviceGroup> rows;
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }
    public List<DeviceGroup> getRows() {
        return rows;
    }
    public void setRows(List<DeviceGroup> rows) {
        this.rows = rows;
    }
    
}
