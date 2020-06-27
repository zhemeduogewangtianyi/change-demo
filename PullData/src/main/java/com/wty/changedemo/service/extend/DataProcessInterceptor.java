package com.wty.changedemo.service.extend;

import com.wty.changedemo.adaptor.AbstractHandlerInterceptorAdaptor;
import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.entity.producer.PushGatewayDTO;
import com.wty.changedemo.manager.OssManager;
import com.wty.changedemo.queue.MetaQueue;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataProcessInterceptor extends AbstractHandlerInterceptorAdaptor<ProducerDTO> {

    @Autowired
    private OssManager ossManager;

    @Autowired
    private MetaQueue metaQueue;

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
        List maps = (List) result;
        String upload = ossManager.upload(producerDTO, maps);
        //发送mq
        PushGatewayDTO pushGatewayDTO = producerDTO.getPushGatewayDTO();
        CommonParamsDTO params = pushGatewayDTO.getParams();
        params.setOssPath(upload);
        pushGatewayDTO.setParams(params);
        producerDTO.setPushGatewayDTO(pushGatewayDTO);
        producerDTO.setFlag(2);
        //数据库记录
        metaQueue.send(producerDTO);
        //数据库更新
        System.out.println(Thread.currentThread().getName() + " 回调执行完毕！");
    }

    @Override
    public void after(BusinessHandler<ProducerDTO> handle, ProducerDTO taskId, Throwable e) {

    }

    @Override
    public int getOrder(){
        return Integer.MIN_VALUE - 1;
    }

    @Override
    public boolean support(ProducerDTO producerDTO) {
        return producerDTO.getFlag() == 1 && producerDTO.getExpireAt() > System.currentTimeMillis();
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }

}
