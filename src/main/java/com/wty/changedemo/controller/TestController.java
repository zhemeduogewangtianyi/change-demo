package com.wty.changedemo.controller;

import com.wty.changedemo.handler.HandlerExecute;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class TestController {

    @Autowired
    private HandlerExecute execute;

    @Autowired
    private BusinessHandler businessHandler;

    @RequestMapping(value = "/test")
    public Object test(){
        //TODO wty 定时任务端的参数代替 1L
        return execute.execute(businessHandler,1L);
    }

}
