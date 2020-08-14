package com.wty.changedemo.service;

import com.wty.changedemo.adaptor.AbstractHandlerInterceptorAdaptor;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.queue.RedisQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OdpsProducerInterceptor extends AbstractHandlerInterceptorAdaptor<ProducerDTO> {

    @Autowired
    private RedisQueue queue;

    @Override
    public Object pre(BusinessHandler<ProducerDTO> handle , ProducerDTO producerDTO){
        //TODO
        if(producerDTO == null){
            return "上下文不能为空 ！";
        }
        return null;

    }

    @Override
    public void post(BusinessHandler<ProducerDTO> handle , ProducerDTO taskId , Object result) {
        if(!(result instanceof ProducerDTO)){
            return;
        }
        ProducerDTO producerDTO = (ProducerDTO) result;
        producerDTO.setFlag(1);
        if(producerDTO.getExpireAt() > System.currentTimeMillis()){
            queue.lpush(producerDTO);
        }
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
        return producerDTO.getFlag() == 0 && producerDTO.getExpireAt() > System.currentTimeMillis();
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }
}
