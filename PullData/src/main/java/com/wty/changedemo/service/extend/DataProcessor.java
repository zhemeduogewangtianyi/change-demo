package com.wty.changedemo.service.extend;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.wty.changedemo.dao.EtlMapper;
import com.wty.changedemo.dao.VmMapper;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.service.BusinessHandler;
import com.wty.changedemo.service.OdpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataProcessor implements BusinessHandler<ProducerDTO> {

    @Autowired
    private OdpsService odpsService;
    @Autowired
    private EtlMapper etlMapper;
    @Autowired
    private VmMapper vmMapper;


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 2;
    }

    @Override
    public boolean support(ProducerDTO t) {
        if(t == null){
            return false;
        }
        boolean b = t.getFlag() == 1;
        boolean b1 = System.currentTimeMillis() < t.getExpireAt();
        return b && b1;
    }

    @Override
    public Object handle(ProducerDTO producerDTO) {

        List<Map<String, Object>> maps = odpsService.pullOdps(producerDTO);
        //ETL
        String etl = etlMapper.etl(JSON.toJSONString(maps));
        //VM
        String s = vmMapper.vmProcess(etl);
        return s;
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }
}
