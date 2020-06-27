package com.wty.changedemo.entity.producer;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import lombok.Data;

@Data
public class EtlDTO {

    private Long id;

    private CommonParamsDTO params;

}
