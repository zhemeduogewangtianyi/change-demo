package com.wty.changedemo.service;

import com.wty.changedemo.dao.DataSourceMapper;
import com.wty.changedemo.dao.TaskMapper;
import com.wty.changedemo.entity.TaskDO;
import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.*;
import com.wty.changedemo.queue.RedisQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer implements BusinessHandler<ProducerDTO> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private RedisQueue queue;

    public Object handle(ProducerDTO producerDTO){

        TaskDO task = taskMapper.getTask(producerDTO.getTaskId());
        DataSourceDO dataSourceById = dataSourceMapper.getDataSourceById(task.getDataSourceId());
        String project = dataSourceById.getProject();
        String table = dataSourceById.getTable();

        producerDTO.setContextId("1a-2b-3c");
        producerDTO.setCreateAt(System.currentTimeMillis());
        producerDTO.setPriority(5);

        ReaderDTO reader = new ReaderDTO();
        reader.setType("odps");

        CommonParamsDTO params = new CommonParamsDTO();
        //TODO
        params.setDataId("1a2b3c");
        params.setStep(5000);
        params.setOffset(0);
        params.setConcurrent(1);
        params.setAccessKey("accessKey");
        params.setAccessId("accessId");
        params.setEndPoint("http://www.baidu.com");
        params.setProject(project);
        params.setTable(table);

        PartitionDTO partitionDTO = new PartitionDTO();
        partitionDTO.setDs("20200101");
        partitionDTO.setType("1");
        params.setPartitionDTO(partitionDTO);

        reader.setParams(params);
        reader.setStepSwitch(true);

        producerDTO.setReader(reader);

        EtlDTO etlDTO = new EtlDTO();
        etlDTO.setId(1L);
        producerDTO.setEtlDTO(etlDTO);

        RenderDTO renderDTO = new RenderDTO();
        renderDTO.setId(1L);
        producerDTO.setRenderDTO(renderDTO);

        WriteDTO writeDTO = new WriteDTO();
        writeDTO.setType("oss");
        CommonParamsDTO commonParamsDTO = new CommonParamsDTO();
        //TODO
        commonParamsDTO.setDataId("write");
        commonParamsDTO.setDatObject("/202006240000" + task.getId());
        writeDTO.setParams(commonParamsDTO);
        producerDTO.setWriteDTO(writeDTO);

        PushGatewayDTO pushGatewayDTO = new PushGatewayDTO();
        pushGatewayDTO.setType("ftp");
        CommonParamsDTO pushGateWayParams = new CommonParamsDTO();
        pushGateWayParams.setIp("127.0.0.1");
        pushGateWayParams.setPort("22");
        pushGateWayParams.setUsername("ftpuser");
        pushGateWayParams.setPassword("tfpuser");
        pushGatewayDTO.setParams(pushGateWayParams);

        producerDTO.setPushGatewayDTO(pushGatewayDTO);

        return producerDTO;
    }

    @Override
    public boolean support(ProducerDTO producerDTO) {
        if(producerDTO == null){
            return false;
        }
        boolean b = producerDTO.getFlag() == 0 && producerDTO.getExpireAt() > System.currentTimeMillis();
        return b;

    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean available(ProducerDTO producerDTO) {
        return producerDTO.getAvailable() == 0;
    }
}
