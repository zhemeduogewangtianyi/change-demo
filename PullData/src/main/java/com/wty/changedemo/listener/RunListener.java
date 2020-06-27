package com.wty.changedemo.listener;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.entity.producer.PushGatewayDTO;
import com.wty.changedemo.manager.OssManager;
import com.wty.changedemo.queue.MetaQueue;
import com.wty.changedemo.queue.RedisQueue;
import com.wty.changedemo.service.OdpsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//TODO wty 启动监听，建议配置维护起来，挂掉了方便启动
@Component
public class RunListener implements InitializingBean {

    @Autowired
    private ListenerRegister register;
    @Autowired
    private RedisQueue redisQueue;
    @Autowired
    private OdpsService odpsService;
    @Autowired
    private OssManager ossManager;
    @Autowired
    private MetaQueue metaQueue;

    @Override
    public void afterPropertiesSet() throws Exception {
        Task task = new Task("key", new TaskCallback<ProducerDTO>() {
            @Override
            public void callback(ProducerDTO o) {
                ProducerDTO producerDTO = (ProducerDTO)redisQueue.rpop();
                if(producerDTO != null && producerDTO.getExpireAt() > System.currentTimeMillis()){

                    List<Map<String, Object>> maps = odpsService.pullOdps(producerDTO);
                    //ETL
                    //VM
                    String upload = ossManager.upload(producerDTO, maps);
                    //发送mq
                    PushGatewayDTO pushGatewayDTO = producerDTO.getPushGatewayDTO();
                    CommonParamsDTO params = pushGatewayDTO.getParams();
                    params.setOssPath(upload);
                    pushGatewayDTO.setParams(params);
                    producerDTO.setPushGatewayDTO(pushGatewayDTO);
                    metaQueue.send(producerDTO);
                    System.out.println(Thread.currentThread().getName() + " 回调执行完毕！");
                }
            }
        },new ProducerDTO());
        QueueExecutor executor = new QueueExecutor("key", 5, task);

        register.register("key",executor);
    }
}
