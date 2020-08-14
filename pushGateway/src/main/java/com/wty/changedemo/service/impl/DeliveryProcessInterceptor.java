package com.wty.changedemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.wty.changedemo.adaptor.AbstractHandlerInterceptorAdaptor;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryProcessInterceptor extends AbstractHandlerInterceptorAdaptor<ProducerDTO> {

    @Override
    public Object pre(BusinessHandler<ProducerDTO> handle , ProducerDTO producerDTO){
        //TODO
        if(producerDTO == null){
            return false;
        }
        //TODO hook
        return null;



    }

    @Override
    public void post(BusinessHandler<ProducerDTO> handle , ProducerDTO producerDTO , Object result) {
        if(!(result instanceof List)){
            return;
        }
        List download = (List) result;
        producerDTO.setFlag(3);
        System.err.println("投递：" + JSON.toJSONString(download));
    }

    @Override
    public void after(BusinessHandler<ProducerDTO> handle, ProducerDTO taskId, Throwable e) {

    }

    @Override
    public void hook(BusinessHandler<ProducerDTO> handler, ProducerDTO t){

    }

    @Override
    public int getOrder(){
        return Integer.MIN_VALUE - 1;
    }

    @Override
    public boolean support(ProducerDTO producerDTO) {
        return producerDTO.getFlag() == 2 && producerDTO.getExpireAt() > System.currentTimeMillis();
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }

}
