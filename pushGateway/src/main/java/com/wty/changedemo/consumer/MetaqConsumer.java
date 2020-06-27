package com.wty.changedemo.consumer;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.handler.BusinessHandlerExecute;
import com.wty.changedemo.queue.MetaQueue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MetaqConsumer implements InitializingBean {

    @Autowired
    private MetaQueue queue;
    @Autowired
    private BusinessHandlerExecute execute;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    ProducerDTO producerDTO = (ProducerDTO)queue.pull();
                    if(producerDTO == null || producerDTO.getExpireAt() < System.currentTimeMillis()){
                        continue;
                    }
                    System.out.println("开始投递任务：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    Object send = execute.businessHandler(producerDTO);
                    System.out.println("投递任务结束：" + send + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
