package com.wty.changedemo.listener;

import java.util.concurrent.ThreadLocalRandom;

public class Task<T> implements Runnable {

    private String taskName;

    private boolean flag = true;

    private TaskCallback<T> taskCallback;

    private T t;

    public Task(String taskName, TaskCallback<T> taskCallback, T t) {
        this.taskName = taskName;
        this.taskCallback = taskCallback;
        this.t = t;
    }

    @Override
    public void run() {
        while(flag){
            taskCallback.callback(t);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean stop(){
        if(this.flag){
            this.flag = false;
        }
        return true;
    }

}
