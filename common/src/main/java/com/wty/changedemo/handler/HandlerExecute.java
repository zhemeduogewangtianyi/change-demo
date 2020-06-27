package com.wty.changedemo.handler;

import com.wty.changedemo.service.HandlerInterceptor;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HandlerExecute<T> extends AbstractHandler<T> {

    @Autowired
    private List<HandlerInterceptor<T>> interceptors;

    public Object execute(BusinessHandler<T> businessHandler, T t){

        setInterceptors(interceptors);

        Object temp = this.applyPre(businessHandler, t);
        if(temp != null){
            return temp;
        }

        Object result = businessHandler.handle(t);

        Throwable throwable = null;
        try {
            this.applyPost(businessHandler , t , result);
        } catch (Throwable e) {
            throwable = e;
            e.printStackTrace();
        }finally {
            this.applyAfter(businessHandler , t , throwable);
        }

        return result;
    }

}
