package com.wty.changedemo.service;

import com.wty.changedemo.entity.producer.ProducerDTO;

public interface DeliveryService {

    boolean send(ProducerDTO producerDTO);

}
