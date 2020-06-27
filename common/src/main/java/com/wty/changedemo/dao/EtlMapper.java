package com.wty.changedemo.dao;

import org.springframework.stereotype.Component;

@Component
public class EtlMapper {

    public String etl(String data){
        return data + "_etl";
    }

}
