package com.wty.changedemo.entity.producer;

import lombok.Data;

@Data
public class ProducerDTO {

    /** 上下文ID */
    private String contextId;

    /** 创建时间 */
    private Long createAt;

    /** 过期时间 */
    private Long expireAt;

    /** 优先级 */
    private Integer priority;

    /** 读取信息 */
    private ReaderDTO reader;

    /** etl 信息 */
    private EtlDTO etlDTO;

    /** 模板渲染 */
    private RenderDTO renderDTO;

    /** 上传输出 */
    private WriteDTO writeDTO;

    /** 报送 */
    private PushGatewayDTO pushGatewayDTO;

}
