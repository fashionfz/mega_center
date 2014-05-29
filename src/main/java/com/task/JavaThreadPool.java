package com.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池类
 * @author Administrator
 *
 */
public class JavaThreadPool {

    private static ExecutorService pool=Executors.newFixedThreadPool(10);
    
    
    /**
     * 添加要执行的线程
     * @param thread
     */
    public static void addThread(Thread thread){
        getPool().execute(thread);
    }
    
    /**
     * 关闭线程池
     */
    public static void shutdownNow(){
        pool.shutdownNow();
    }
    
    public static void shutdown(){
        pool.shutdown();
    }
    public static boolean awaitTermination(long timeout,TimeUnit unit){
        try {
            return pool.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args){
        
    }
    
    public static ExecutorService getPool(){
        if(pool.isTerminated())
            pool = Executors.newFixedThreadPool(10);
        return pool;
    }
}
