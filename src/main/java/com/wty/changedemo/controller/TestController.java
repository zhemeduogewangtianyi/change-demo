package com.wty.changedemo.controller;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.handler.BusinessHandlerExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @Autowired
    private BusinessHandlerExecute execute;

    @RequestMapping(value = "/test")
    public Object test(){
        //TODO wty 定时任务端的参数代替 1L
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setTaskId(1L);
        producerDTO.setFlag(0);
        producerDTO.setExpireAt(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
        return execute.businessHandler(producerDTO);
    }

}
