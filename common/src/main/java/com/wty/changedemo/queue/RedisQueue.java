package com.wty.changedemo.queue;

import com.wty.changedemo.lock.LockResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;

@Component
public class RedisQueue {

    private static volatile LinkedList<Object> queue = new LinkedList<>();

    private static LockResource lock = new LockResource();

    public void lpush(Object e){
        lock.getLock().lock();
        try{
            queue.addFirst(e);
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }

    }

    public void rpush(Object e){
        lock.getLock().lock();
        try{
            queue.addLast(e);
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
    }

    public Object rpop(){
        lock.getLock().lock();
        Object e = null;
        try{
            if(CollectionUtils.isEmpty(queue)){
                return null;
            }
            e =  queue.pop();
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
        return e;
    }

}