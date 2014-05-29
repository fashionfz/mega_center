package com.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.listener.SpringUtil;
import com.po.CheckConfig;
import com.po.Device;
import com.po.SysService;
import com.service.ICheckConfigService;
import com.service.ICheckReportService;
import com.service.IDeviceGroupService;
import com.service.IDeviceService;
import com.service.IServiceService;
import com.util.HttpUtil;
import com.util.TopicPublisher;


public class CheckDviceThread extends Thread{
    
    public static CloseableHttpClient httpclient;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    IDeviceService service  = (IDeviceService) SpringUtil.getBean("deviceService");
    ICheckConfigService check = (ICheckConfigService) SpringUtil.getBean("checkConfigService");
    IDeviceGroupService group = (IDeviceGroupService) SpringUtil.getBean("deviceGroupService");
    public static ICheckReportService report = (ICheckReportService) SpringUtil.getBean("checkReportService");
    
    
    public static String mainId="";
    public static int checkCount=0;
    public static int errorCount=0;
    public static Date time;
    public static String checkName="";
    
    public CheckDviceThread(Date date){
        time=date;
    }
    
    @Override
    public void run() {
        //清零
        checkCount=0;
        errorCount=0;
        IServiceService server = (IServiceService) SpringUtil.getBean("serviceService");
        SysService serverInfo = server.getByCode("check");
        if(serverInfo==null)
            return;
        
        CheckConfig config = check.query("deviceGroup");
        if(config==null||"".equals(config))
            return;
        String zuNames = group.getNames(config.getItemValue().split(","));
        List<Device> list=service.queryCheck(config.getItemValue().split(","));
        
        
        
        
        
        
        CheckConfig mode = check.query("checkCount");
        if("2".equals(mode.getItemValue())){
            mainId=report.saveMain(df.format(time)+"_"+zuNames+"_循环检测", time,list.size());
        }
        else{
            mainId=report.saveMain(df.format(time)+"_"+zuNames+"_一次/每天",time,list.size());
        }
        
        
        try{
            //"http://localhost:8080/C_server/checkFile"
            httpclient = HttpUtil.getConnectPool("http://"+serverInfo.getIp()+":"+serverInfo.getPort()+serverInfo.getUrl());
        }catch(Exception e){
            e.printStackTrace();
        }

        if("e".equals(mode.getItemValue())){
            int i=0;
            while(true){
                for(Device device : list){
//                    if(mainId==""){
//                        return;
//                    }
                    String para ="xml=<message><device id=\""+device.getId()+"\"/></message>";
                    HttpPost post = new HttpPost("http://"+serverInfo.getIp()+":"+serverInfo.getPort()+serverInfo.getUrl());
                    CheckDviceStore t = new CheckDviceStore(httpclient,post,para,mode.getItemValue());
                    JavaThreadPool.addThread(t);
                    i++;
                    if(i%10==0){
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            int i=0;
            for(Device device : list){
//                if(mainId==""){
//                    return;
//                }
                String para ="xml=<message><device id=\""+device.getId()+"\"/></message>";
                HttpPost post = new HttpPost("http://"+serverInfo.getIp()+":"+serverInfo.getPort()+serverInfo.getUrl());
                CheckDviceStore t = new CheckDviceStore(httpclient,post,para,mode.getItemValue());
                JavaThreadPool.addThread(t);
                i++;
                if(i%10==0){
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            JavaThreadPool.shutdown();
            while (!JavaThreadPool.awaitTermination(1, TimeUnit.MILLISECONDS)) {  
                //线程池没有关闭
            }
            CheckDviceThread.close();

        }
        
        
        
    }
    public static void close(){
        //更新检测结果
        if(mainId!=null&&!"".equals(mainId)){
            IServiceService server = (IServiceService) SpringUtil.getBean("serviceService");
            SysService serverInfo = server.getByCode("message");
            report.updateCheckMain(mainId, checkCount, errorCount);
            TopicPublisher pub = new TopicPublisher(serverInfo);
            String context =checkName+":已检测完毕";
            try {
                pub.send(new String(context.getBytes("UTF-8"), "ISO-8859-1"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                HttpUtil.httpClient.close();
                HttpUtil.httpClient=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainId="";
        }
    }
    
}
