package com.wty.changedemo.queue;

import com.wty.changedemo.lock.LockResource;
import org.springframework.stereotype.Component;

@Component
public class MetaQueue {

    private Object[] datas;

    private int front;
    private int real;

    private static volatile LockResource lock = new LockResource();

    public MetaQueue() {
        this.datas = new Object[10];
        this.front = 0;
        this.real = 0;
    }

    public void send(Object data){
        lock.getLock().lock();
        try{
            if((this.real + 1) % datas.length == this.front){
                lock.getCondition().await();
            }
            this.datas[this.real] = data;
            this.real = ( this.real + 1 ) % datas.length;
            lock.getCondition().signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.getLock().unlock();
        }
    }

    public Object pull(){
        lock.getLock().lock();
        try{
            if(this.front == this.real){
                lock.getCondition().await();
            }
            Object data = this.datas[this.front];
            this.front = (this.front + 1) % datas.length;
            lock.getCondition().signalAll();
            return data;
        }catch(Exception e){

        }finally {
            lock.getLock().unlock();
        }
        return null;
    }

}
