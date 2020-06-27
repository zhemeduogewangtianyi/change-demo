package com.wty.changedemo.dao;

import org.springframework.stereotype.Component;

@Component
public class VmMapper {

    public String vmProcess(String data){
        return "<a>"+data+"</a>";
    }

}
