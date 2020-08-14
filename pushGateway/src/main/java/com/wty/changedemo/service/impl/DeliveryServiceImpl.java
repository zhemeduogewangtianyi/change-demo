package com.wty.changedemo.service.impl;

import java.util.List;
import java.util.Map;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.entity.producer.PushGatewayDTO;
import com.wty.changedemo.manager.OssManager;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements BusinessHandler<ProducerDTO> {

    @Autowired
    private OssManager ossManager;

    @Override
    public Object handle(ProducerDTO producerDTO) {
        try {
            PushGatewayDTO pushGatewayDTO = producerDTO.getPushGatewayDTO();
            String type = pushGatewayDTO.getType();
            CommonParamsDTO params = pushGatewayDTO.getParams();
            String ip = params.getIp();
            String port = params.getPort();
            String username = params.getUsername();
            String password = params.getPassword();
            String ossPath = params.getOssPath();

            String download = ossManager.download(ossPath);
            //投递。。。
            Thread.sleep(5000L);
            return download;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean support(ProducerDTO producerDTO) {
        return producerDTO.getFlag() == 2 && producerDTO.getExpireAt() > System.currentTimeMillis();

    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 3;
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }
}
