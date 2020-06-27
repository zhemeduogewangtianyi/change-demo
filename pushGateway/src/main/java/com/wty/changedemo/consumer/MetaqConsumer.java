package com.wty.changedemo.consumer;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.queue.MetaQueue;
import com.wty.changedemo.service.DeliveryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class MetaqConsumer implements InitializingBean {

    @Autowired
    private MetaQueue queue;
    @Autowired
    private DeliveryService deliveryService;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    ProducerDTO pull = (ProducerDTO)queue.pull();
                    if(pull == null){
                        continue;
                    }
                    boolean send = deliveryService.send(pull);
                    System.out.println("投递任务：" + send);
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
