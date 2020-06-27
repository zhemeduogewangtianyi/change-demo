package com.wty.changedemo.dao;

import com.wty.changedemo.entity.producer.DataSourceDO;
import org.springframework.stereotype.Component;

@Component
public class DataSourceMapper {

    public DataSourceDO getDataSourceById(Long id){
        DataSourceDO dataSourceDO = new DataSourceDO();
        dataSourceDO.setId(1L);
        dataSourceDO.setName("测试");
        dataSourceDO.setProject("sec_xxx");
        dataSourceDO.setTable("table");
        return dataSourceDO;
    }

}
