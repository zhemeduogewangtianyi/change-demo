package com.wty.changedemo.listener;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.handler.BusinessHandlerExecute;
import com.wty.changedemo.queue.RedisQueue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO wty 启动监听，建议配置维护起来，挂掉了方便启动
@Component
public class RunListener implements InitializingBean {

    @Autowired
    private ListenerRegister register;
    @Autowired
    private RedisQueue redisQueue;
    @Autowired
    private BusinessHandlerExecute execute;

    @Override
    public void afterPropertiesSet() throws Exception {
        Task task = new Task("key", new TaskCallback<ProducerDTO>() {
            @Override
            public void callback(ProducerDTO o) {
                ProducerDTO producerDTO = (ProducerDTO)redisQueue.rpop();
                if(producerDTO != null){
                    System.err.println("111");
                    execute.businessHandler(producerDTO);
                }
            }
        },new ProducerDTO());
        QueueExecutor executor = new QueueExecutor("key", 5, task);

        register.register("key",executor);
    }
}
