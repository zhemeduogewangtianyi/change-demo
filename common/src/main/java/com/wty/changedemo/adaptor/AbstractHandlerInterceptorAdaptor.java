package com.wty.changedemo.adaptor;

import com.wty.changedemo.service.BusinessHandler;
import com.wty.changedemo.service.HandlerInterceptor;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class AbstractHandlerInterceptorAdaptor<T> implements HandlerInterceptor<T> {

    protected ThreadLocal<Deque<Object>> threadLocal = new ThreadLocal<Deque<Object>>(){
        @Override
        protected Deque<Object> initialValue() {
            return new ArrayDeque<>();
        }
    };

    @Override
    public Object pre(BusinessHandler<T> handler, T t) {
        return null;
    }

    @Override
    public void post(BusinessHandler<T> hander, T t, Object result) {

    }

    @Override
    public void after(BusinessHandler<T> handler, T t, Throwable e) {

    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean support(T t) {
        return false;
    }
}
