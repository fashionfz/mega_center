package com.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.util.HttpUtil;

/**
 * 自动定时服务
 * @author Administrator
 *
 */
public class AutoJob {

    int count =0;
    private static Scheduler scheduler;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void doVICPlot() {
//        count++;
//        System.out.println("自动执行" + new Date());
//        if(count>8)
//            resetJob("0/20 * * * * ?");
    }
    
    
    public void checkDeviceStore(){
        Date begin=new Date();
        System.out.println("自动检查开始："+df.format(begin));
        CheckDviceThread check = new CheckDviceThread(begin);
        check.start();
    }
    
    public void downCheckDevice(){
        System.out.println("自动检查结束："+df.format(new Date()));
        CheckDviceThread.close();
        JavaThreadPool.shutdownNow();
    }
    
    
    
    /**
     * 自动clear日志
     */
    public void clearCheckLog(){
        //Thread log = new CheckLogClear();
        //JavaThreadPool.addThread(log);
    }
    
    
    

    public static void resetJob(String triggerName,String cronExpression) {
        try {
            // 运行时可通过动态注入的scheduler得到trigger，
            // 注意采用这种注入方式在有的项目中会有问题，如果遇到注入问题，
            // 可以采取在运行方法时候，获得bean来避免错误发生。
            CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(
                    triggerName, Scheduler.DEFAULT_GROUP);
            String originConExpression = trigger.getCronExpression();
            // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
            if (!originConExpression.equalsIgnoreCase(cronExpression)) {
                trigger.setCronExpression(cronExpression);
                scheduler.rescheduleJob(triggerName, Scheduler.DEFAULT_GROUP,
                        trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
