package com.wty.changedemo.entity;

import lombok.Data;

@Data
public class TaskDO {

    private Long id;

    private Long taskId;

    private Long etlId;

    private Long vmId;

    private Long dataSourceId;

    private String configJson;

    /** 1 odps 2 ... */
    private Integer type;

}
