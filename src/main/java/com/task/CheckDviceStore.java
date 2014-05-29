package com.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.listener.SpringUtil;
import com.service.ICheckReportService;

/**
 * 检查录像存储
 * @author Administrator
 *
 */
public class CheckDviceStore extends Thread{

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httppost;
    private final String parameters;
    private final String mode;
    
    ICheckReportService service = (ICheckReportService) SpringUtil.getBean("checkReportService");

    public CheckDviceStore(CloseableHttpClient httpClient, HttpPost httppost,String parameters,String mode) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httppost = httppost;
        this.parameters=parameters;
        this.mode=mode;
    }

    @Override
    public void run() {
        try {
            StringBuilder sb = new StringBuilder();
            System.out.println(parameters);
            StringEntity pa = new StringEntity(parameters);
            pa.setContentType("application/x-www-form-urlencoded");
            httppost.setEntity(pa);
            CloseableHttpResponse response = httpClient.execute(httppost, context);
            try {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        entity.getContent(), "UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
//                if("".equals(CheckDviceThread.mainId))
//                    return;
                service.saveCheck(CheckDviceThread.mainId, CheckDviceThread.time, sb.toString(),mode);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
        } catch (IOException ex) {
        }
    }
}
