package com.wty.changedemo.extend;

import com.wty.changedemo.adaptor.AbstractHandlerInterceptorAdaptor;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggingHandlerInterceptor<T> extends AbstractHandlerInterceptorAdaptor<T> {

    @Autowired
    private LogInfoExtend logInfo;

    @Override
    public Object pre(BusinessHandler<T> handle , T t){
        threadLocal.get().push(new Object());
        logInfo.logInfo("{} 收到参数 {}",handle.getClass().getSimpleName(),t);
        return null;
    }

    @Override
    public void post(BusinessHandler<T> handle , T t , Object result) {
        threadLocal.get().pop();
        logInfo.logInfo("{} 数据处理 {} ",handle.getClass().getSimpleName(),result);
    }

    @Override
    public void after(BusinessHandler<T> handle, T t, Throwable e) {
        if(e != null){
            if(!threadLocal.get().isEmpty()){
                threadLocal.get().clear();
                logInfo.logError(e,"{} 执行出错 {}",handle.getClass().getSimpleName(),e.getMessage());
            }
        }
    }

    @Override
    public int getOrder(){
        return Integer.MIN_VALUE - 1;
    }

    @Override
    public boolean support(T t) {
        return false;
    }
}
