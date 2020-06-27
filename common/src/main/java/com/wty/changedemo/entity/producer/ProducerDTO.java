package com.wty.changedemo.entity.producer;

import lombok.Data;

@Data
public class ProducerDTO {

    /** 上下文ID */
    private String contextId;

    /** 任务ID */
    private Long taskId;

    /** 0：生产json 1:拉取处理数据 2：投递数据 3 投递中 4 投递失败 5 结束 */
    private Integer flag;

    /** 开关 0 开启 1 关闭 */
    private Integer available = 0;

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
