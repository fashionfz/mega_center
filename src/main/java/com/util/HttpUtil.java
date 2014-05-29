package com.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.task.CheckDviceStore;


public class HttpUtil {

    public static CloseableHttpClient httpClient=null;
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static CloseableHttpClient getConnectPool(String url) throws Exception{
        if(httpClient!=null)
            return httpClient;
        else{
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            // 将最大连接数增加到200
            cm.setMaxTotal(200);
            // 将每个路由基础的连接增加到20
            cm.setDefaultMaxPerRoute(20);
            //将目标主机的最大连接数增加到50
            httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .build();
            return httpClient;
        }

    }
    
    
//    public static void close(){
//        System.out.println("http连接关闭："+df.format(new Date()));
//        try {
//            httpClient.close();
//            httpClient=null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void main(String[] args){
        HttpUtil util = new HttpUtil();
        try {
            String para ="xml=<message><device id='123'/></message>";
            HttpPost post = new HttpPost("http://localhost:8080/C_server/checkFile");
            CheckDviceStore t = new CheckDviceStore(util.getConnectPool("http://25.30.5.223:8080/C_server/checkFile"),post,para,"1");
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}