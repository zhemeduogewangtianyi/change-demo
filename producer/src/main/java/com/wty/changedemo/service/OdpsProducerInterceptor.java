package com.wty.changedemo.service;

import com.wty.changedemo.adaptor.AbstractHandlerInterceptorAdaptor;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.queue.RedisQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OdpsProducerInterceptor extends AbstractHandlerInterceptorAdaptor<Long> {

    @Autowired
    private RedisQueue<ProducerDTO> queue;

    @Override
    public Object pre(BusinessHandler<Long> handle , Long taskId){
        //TODO
        if(taskId == null){
            return "taskId 不能为空 ！";
        }
        return null;

    }

    @Override
    public void post(BusinessHandler<Long> handle , Long taskId , Object result) {
        if(!(result instanceof ProducerDTO)){
            return;
        }
        ProducerDTO producerDTO = (ProducerDTO) result;
        if(producerDTO.getExpireAt() > System.currentTimeMillis()){
            queue.lpush(producerDTO);
        }
    }

    @Override
    public void after(BusinessHandler<Long> handle, Long taskId, Throwable e) {

    }

    @Override
    public int getOrder(){
        return Integer.MIN_VALUE - 1;
    }

}
