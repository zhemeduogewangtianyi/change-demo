package com.wty.changedemo.handler;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.service.BusinessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusinessHandlerExecute {

    @Autowired
    private HandlerExecute<ProducerDTO> execute;

    @Autowired
    private List<BusinessHandler<ProducerDTO>> businessHandlers;

    public Object businessHandler(ProducerDTO producerDTO){
        for(BusinessHandler<ProducerDTO> handler : businessHandlers){
            if(!handler.support(producerDTO)){
                continue;
            }
            if(!handler.available(producerDTO)){
                continue;
            }
            return execute.execute(handler,producerDTO);
        }
        return null;
    }

}
