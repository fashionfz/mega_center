package com.util;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.po.SysService;

/**
 * Use in conjunction with TopicListener to test the performance of ActiveMQ
 * Topics.
 */
public class TopicPublisher implements MessageListener {

    private static final char[] DATA = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private final Object mutex = new Object();
    private Connection connection;
    private Session session;
    private MessageProducer publisher;
    private Topic topic;
    private Topic control;
    

    private String url = "tcp://localhost:61616";
    private int size = 256;
    private int subscribers = 1;
    private int remaining;
    private int messages = 10000;
    private long delay;
    private int batch = 2000;

    private byte[] payload;
    
    
    public TopicPublisher(){
        
    }
    
    public TopicPublisher(SysService serverInfo){
        if(serverInfo!=null)
            this.url="tcp://"+serverInfo.getIp()+":"+serverInfo.getPort();
    }

    public static void main(String[] argv) throws Exception {

        TopicPublisher t= new TopicPublisher();
        String txt = "测试数据";
        t.send(new String(txt.getBytes("UTF-8"), "ISO-8859-1"));
    }

    public void send(String context) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic("FirstTopic");
        control = session.createTopic("FirstTopic");

        publisher = session.createProducer(topic);
        publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        
        publisher.send(session.createTextMessage(context));

        connection.stop();
        connection.close();
    }

    private long batch(int msgCount) throws Exception {
        long start = System.currentTimeMillis();
        remaining = subscribers;
        publish();
        waitForCompletion();
        return System.currentTimeMillis() - start;
    }

    private void publish() throws Exception {

        // send events
        BytesMessage msg = session.createBytesMessage();
        msg.writeBytes(payload);
        for (int i = 0; i < messages; i++) {
            publisher.send(msg);
            if ((i + 1) % 1000 == 0) {
                System.out.println("Sent " + (i + 1) + " messages");
            }
        }

        // request report
        publisher.send(session.createTextMessage("REPORT"));
    }

    private void waitForCompletion() throws Exception {
        System.out.println("Waiting for completion...");
        synchronized (mutex) {
            while (remaining > 0) {
                mutex.wait();
            }
        }
    }

    public void onMessage(Message message) {
        synchronized (mutex) {
            System.out.println("Received report " + getReport(message) + " " + --remaining + " remaining");
            if (remaining == 0) {
                mutex.notify();
            }
        }
    }

    Object getReport(Message m) {
        try {
            return ((TextMessage)m).getText();
        } catch (JMSException e) {
            e.printStackTrace(System.out);
            return e.toString();
        }
    }

    static long min(long[] times) {
        long min = times.length > 0 ? times[0] : 0;
        for (int i = 0; i < times.length; i++) {
            min = Math.min(min, times[i]);
        }
        return min;
    }

    static long max(long[] times) {
        long max = times.length > 0 ? times[0] : 0;
        for (int i = 0; i < times.length; i++) {
            max = Math.max(max, times[i]);
        }
        return max;
    }

    static long avg(long[] times, long min, long max) {
        long sum = 0;
        for (int i = 0; i < times.length; i++) {
            sum += times[i];
        }
        sum -= min;
        sum -= max;
        return sum / times.length - 2;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
