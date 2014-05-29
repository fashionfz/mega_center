package com.task;

import com.listener.SpringUtil;
import com.service.ICheckReportService;

/**
 * 自动清除过期日志，每天执行一次
 * @author Administrator
 *
 */
public class CheckLogClear extends Thread{

    @Override
    public void run() {
        ICheckReportService service  = (ICheckReportService) SpringUtil.getBean("checkReportService");
        service.clearLog();
    }

}
