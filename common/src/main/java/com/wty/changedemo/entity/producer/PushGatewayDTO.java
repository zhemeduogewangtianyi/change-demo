package com.wty.changedemo.entity.producer;

import com.wty.changedemo.entity.common.CommonParamsDTO;
import lombok.Data;

@Data
public class PushGatewayDTO {

    /** ftp sftp ... */
    private String type;

    private CommonParamsDTO params;

}
