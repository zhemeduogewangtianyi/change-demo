package com.wty.changedemo.service;

public interface Available<E> {

    default boolean available(E e){
        return true;
    }

}
