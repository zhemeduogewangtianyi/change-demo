package com.wty.changedemo.handler;

import com.wty.changedemo.lock.LockResource;
import com.wty.changedemo.service.BusinessHandler;
import com.wty.changedemo.service.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractHandler<T> {

    private volatile int index = -1;

    private List<HandlerInterceptor<T>> interceptors = new ArrayList<>();

    private static volatile LockResource lock = new LockResource();

    Object applyPre(BusinessHandler<T> businessHandler , T t){
        for(int i = 0 ; i < interceptors.size() ; i++ ){
            HandlerInterceptor<T> interceptor = interceptors.get(i);
            if(!condition(interceptor,businessHandler,t)){
                continue;
            }

            this.index = i;

            Object pre = interceptor.pre(businessHandler,t);
            //无数据后处理
            if(pre != null){
                applyAfter(businessHandler,t,null);
                return pre;
            }
        }
        return null;
    }

    void applyPost(BusinessHandler<T> businessHandler , T t, Object result) throws Throwable {
        for(int i = interceptors.size() - 1 ; i >= 0 ; i--){
            HandlerInterceptor<T> interceptor = interceptors.get(i);
            if(!condition(interceptor,businessHandler,t)){
                continue;
            }
            interceptor.post(businessHandler,t,result);
        }
    }

    void applyAfter(BusinessHandler<T> businessHandler , T t , Throwable throwable){

        for(int i = this.index ; i >= 0 ; i--){
            HandlerInterceptor<T> interceptor = interceptors.get(i);
            if(!condition(interceptor , businessHandler , t)){
                continue;
            }
            try {
                interceptor.after(businessHandler , t , throwable);
            } catch (Throwable e) {
                //TODO
                e.printStackTrace();
            }
        }
    }


    private boolean condition(HandlerInterceptor<T> interceptor , BusinessHandler<T> businessHandler , T t){

        if(!interceptor.support(t)){
            return false;
        }
        if(!interceptor.available(t)){
            return false;
        }
        if(!businessHandler.support(t)){
            return false;
        }
        return businessHandler.available(t);
    }


    public void setInterceptors(List<HandlerInterceptor<T>> interceptors){
        lock.getLock().lock();
        try{
            this.interceptors = interceptors;
            this.interceptors.sort(new Comparator<HandlerInterceptor<T>>() {
                @Override
                public int compare(HandlerInterceptor<T> o1, HandlerInterceptor<T> o2) {
                    return o2.getOrder() - o1.getOrder();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            lock.getLock().unlock();
        }
    }

}
