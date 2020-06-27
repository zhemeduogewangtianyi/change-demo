package com.wty.changedemo.listener;

import com.wty.changedemo.lock.LockResource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ListenerRegister {

    private static volatile Map<String,QueueExecutor> registerMap = new ConcurrentHashMap<>();

    private volatile LockResource lock = new LockResource();

    public void register(String key,QueueExecutor executor){
        lock.getLock().lock();
        try{
            if(registerMap.containsKey(key)){
                //TODO
                return ;
            }
            executor.start();
            registerMap.put(key,executor);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
    }

    public void unregister(String key){
        lock.getLock().lock();
        try{
            if(!registerMap.containsKey(key)){
                //TODO
                return;
            }
            for(Iterator<Map.Entry<String,QueueExecutor>> car = registerMap.entrySet().iterator() ; car.hasNext() ; ){
                Map.Entry<String, QueueExecutor> next = car.next();
                String k = next.getKey();
                QueueExecutor value = next.getValue();
                if(k.equals(key)){
                    car.remove();
                }
                value.end();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
    }

}
