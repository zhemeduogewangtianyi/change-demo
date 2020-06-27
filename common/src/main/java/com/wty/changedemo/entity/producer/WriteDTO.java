package com.wty.changedemo.entity.producer;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import lombok.Data;

@Data
public class WriteDTO {

    /** 类型 ： oss  */
    private String type;

    private CommonParamsDTO params;

}
