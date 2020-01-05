package com.yewei.sample.sync;

/**
 * 分布式锁具体业务内容
 * 
 * @param <R> ReturnValue
 */
public interface LockTaskBiz<R> {
    R doBusiness() throws Exception;
}
