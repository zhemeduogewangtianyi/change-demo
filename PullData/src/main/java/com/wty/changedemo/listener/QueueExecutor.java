package com.wty.changedemo.listener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;

/** 注册接受 */
public class QueueExecutor extends Thread {

    private static volatile ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(200);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            10,
            600,
            TimeUnit.SECONDS,
            queue,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private Task task;

    QueueExecutor(String name, Integer priority, Task task) {
        super(name);
        this.setPriority(priority);
        this.task = task;
    }

    @Override
    public void run() {
        while(executor != null){
            executor.execute(task);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /** 结束 */
    boolean end(){
        boolean res ;
        try{
            if(executor != null){
                executor.shutdownNow();
            }
            executor = null;
            if(!CollectionUtils.isEmpty(queue)){
                queue.clear();
            }
            res = true;
        }catch(Exception e){
            res = false;
        }
        return res && task.stop();
    }
}
