package com.wty.changedemo.service;

import org.springframework.core.Ordered;

public interface BusinessHandler<E> extends Supported<E>,Ordered, Available<E> {

    /** 处理规范 */
    Object handle(E e);

}
