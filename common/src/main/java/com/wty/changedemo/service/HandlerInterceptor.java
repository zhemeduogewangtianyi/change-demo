package com.wty.changedemo.service;

import org.springframework.core.Ordered;

public interface HandlerInterceptor<T> extends Supported<T>, Available<T>, Ordered {

    Object pre(BusinessHandler<T> handler , T t);

    void post(BusinessHandler<T> hander , T t, Object result) throws Throwable;

    void after(BusinessHandler<T> handler , T t , Throwable e) throws Throwable;

    @Override
    int getOrder();
}
