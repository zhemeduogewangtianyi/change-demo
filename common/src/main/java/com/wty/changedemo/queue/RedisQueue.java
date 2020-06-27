package com.wty.changedemo.queue;

import com.wty.changedemo.lock.LockResource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class RedisQueue<E> {

    private volatile LinkedList<E> queue = getTemp();

    private static volatile LockResource lock = new LockResource();

    private static <E> LinkedList<E> getTemp(){
        return new LinkedList<>();
    }

    public void lpush(E e){
        lock.getLock().lock();
        try{
            queue.addFirst(e);
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }

    }

    public void rpush(E e){
        lock.getLock().lock();
        try{
            queue.addLast(e);
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
    }

    public E rpop(){
        lock.getLock().lock();
        E e = null;
        try{
            e =  queue.poll();
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
        return e;
    }

}