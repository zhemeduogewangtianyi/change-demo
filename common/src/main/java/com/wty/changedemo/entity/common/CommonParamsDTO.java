package com.wty.changedemo.entity.common;

import com.wty.changedemo.entity.producer.PartitionDTO;
import lombok.Data;

@Data
public class CommonParamsDTO {

    /** 单例ID */
    private String dataId;

    /** 分区信息 */
    private PartitionDTO partitionDTO;

    /** 偏移量 */
    private Integer offset;

    /** 步长 */
    private Integer step;

    /** 并发 */
    private Integer concurrent;

    /** oss的目录页 /202006240000/<taskId> */
    private String datObject;

    /** ODPS */
    private String endPoint;

    private String project;

    private String table;

    private String accessId;

    private String accessKey;


    /** 投递 */
    private String ip;

    private String port;

    private String username;

    private String password;

    private String ossPath;

}
