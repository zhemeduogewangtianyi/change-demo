package com.wty.changedemo.lock;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁定资源类
 */
@Data
public class LockResource {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
}
