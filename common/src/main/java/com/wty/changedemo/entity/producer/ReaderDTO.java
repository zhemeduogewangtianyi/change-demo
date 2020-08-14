package com.wty.changedemo.entity.producer;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import lombok.Data;

@Data
public class ReaderDTO {

    /** 读取类型 1：odps */
    private String type;

    /** 公共参数 */
    private CommonParamsDTO params;

}
