package com.wty.changedemo.extend;

import com.wty.changedemo.handler.AbstractInterceptorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogInfoExtend extends AbstractInterceptorHandler<Object> {

    private Logger LOGGER = LoggerFactory.getLogger(LogInfoExtend.class);

    //debug
    protected void logDebug(String template,Object... params){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug(template,params);
        }
    }

    //info
    protected void logInfo(String template,Object... params){
        if(LOGGER.isInfoEnabled()){
            LOGGER.info(template,params);
        }
    }

    //warn
    protected void logWarn(Throwable e,String template,Object... params){
        if(LOGGER.isWarnEnabled()){
            if (e != null) {
                LOGGER.warn(e.toString());
            }
            LOGGER.warn(template,params);
        }
    }

    //error
    protected void logError(Throwable e,String template,Object... params){
        if(LOGGER.isErrorEnabled()){
            LOGGER.error(e.toString());
            LOGGER.error(template,params);
        }
    }

}
