package com.wty.changedemo.service.impl;

import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.service.OdpsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OdpsServiceImpl implements OdpsService {
    @Override
    public List<Map<String, Object>> pullOdps(ProducerDTO producerDTO) {
        //TODO 拉数据
        List<Map<String,Object>> maps = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++){
            Map<String,Object> map = new HashMap<>();
            map.put(i+"",i);
            maps.add(map);
        }
        return maps;
    }
}
