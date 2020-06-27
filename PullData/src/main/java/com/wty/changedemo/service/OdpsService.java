package com.wty.changedemo.service;

import com.wty.changedemo.entity.producer.ProducerDTO;

import java.util.List;
import java.util.Map;

public interface OdpsService {

    List<Map<String,Object>> pullOdps(ProducerDTO producerDTO);

}
