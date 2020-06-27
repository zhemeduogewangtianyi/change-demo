package com.wty.changedemo.entity.producer;

import lombok.Data;

@Data
public class PartitionDTO {

    /** odps分区 */
    private String ds;

    /**  */
    private String type;

}
