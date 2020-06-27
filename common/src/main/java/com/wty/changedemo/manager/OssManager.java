package com.wty.changedemo.manager;

import com.alibaba.fastjson.JSON;
import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.ProducerDTO;
import com.wty.changedemo.entity.producer.WriteDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OssManager {

    public String upload(ProducerDTO producerDTO,List<Map<String, Object>> maps){
        WriteDTO writeDTO = producerDTO.getWriteDTO();
        String type = writeDTO.getType();
        CommonParamsDTO params = writeDTO.getParams();
        String datObject = params.getDatObject();
        String dataId = params.getDataId();

        //文件名称....
        //...

        System.err.println("上传 ： params : " + JSON.toJSONString(maps));
        //上传
        return "fileName_" + System.currentTimeMillis();
    }

    public List<Map<String,Object>> download(String filePath){
        //TODO oss下载数据
        List<Map<String,Object>> maps = new ArrayList<>();
        for(int i = 0 ; i < 100 ; i++){
            Map<String,Object> map = new HashMap<>();
            map.put(i+"",i);
            maps.add(map);
        }
        return maps;
    }

}
