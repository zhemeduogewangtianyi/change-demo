package com.wty.changedemo.dao;

import com.wty.changedemo.entity.TaskDO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDO getTask(Long taskId){
        TaskDO taskDO = new TaskDO();
        taskDO.setId(taskId);
        taskDO.setTaskId(taskId);
        taskDO.setEtlId(taskId);
        taskDO.setVmId(taskId);
        taskDO.setDataSourceId(taskId);
        taskDO.setConfigJson("{\"a\":\"1\",\"b\":\"2\",\"taskId\":" + taskId + "}");
        taskDO.setType(1);
        return taskDO;
    }

}
