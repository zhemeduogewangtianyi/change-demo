package com.wty.changedemo.service;

import com.wty.changedemo.dao.DataSourceMapper;
import com.wty.changedemo.dao.TaskMapper;
import com.wty.changedemo.entity.TaskDO;
import com.wty.changedemo.entity.common.CommonParamsDTO;
import com.wty.changedemo.entity.producer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer implements BusinessHandler<Long> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    public Object handle(Long taskId){

        TaskDO task = taskMapper.getTask(taskId);
        DataSourceDO dataSourceById = dataSourceMapper.getDataSourceById(task.getDataSourceId());
        String project = dataSourceById.getProject();
        String table = dataSourceById.getTable();

        ProducerDTO producerDTO = new ProducerDTO();

        producerDTO.setContextId("1a-2b-3c");
        producerDTO.setCreateAt(System.currentTimeMillis());
        producerDTO.setExpireAt(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
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
    public boolean support(Long aLong) {
        TaskDO task = taskMapper.getTask(aLong);
        if(task == null || task.getType() == null){
            return false;
        }
        return task.getType() - 1 == 0;

    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean available(Long aLong) {
        return true;
    }
}
