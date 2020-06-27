package com.wty.changedemo.service;

import com.alibaba.fastjson.JSON;
import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.entity.producer.PushGatewayDTO;
import com.wty.changedemo.manager.OssManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private OssManager ossManager;

    @Override
    public boolean send(ProducerDTO producerDTO) {
        try {
            PushGatewayDTO pushGatewayDTO = producerDTO.getPushGatewayDTO();
            String type = pushGatewayDTO.getType();
            CommonParamsDTO params = pushGatewayDTO.getParams();
            String ip = params.getIp();
            String port = params.getPort();
            String username = params.getUsername();
            String password = params.getPassword();
            String ossPath = params.getOssPath();

            List<Map<String, Object>> download = ossManager.download(ossPath);
            //投递。。。
            Thread.sleep(5000L);
            System.err.println("投递：" + JSON.toJSONString(download));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
