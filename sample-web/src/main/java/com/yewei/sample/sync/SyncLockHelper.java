package com.yewei.sample.sync;

import com.yewei.cache.lock.DistLockManager;
import com.yewei.cache.lock.IDistLock;
import com.yewei.common.error.BusinessException;
import com.yewei.common.error.GeneralCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 */
@Service
@Slf4j
public class SyncLockHelper {
    // 默认10秒超时时间
    private static final long WAIT_TIME = 10;
    // 默认最大执行任务时间20秒
    private static final long LEASE_TIME = 20;
    @Autowired
    private DistLockManager lockManager;

    /**
     * 
     * @param lockKey
     */
    public <R> R lock(String lockKey, long waitTime, long leaseTime, TimeUnit unit, LockTaskBiz<R> lockTaskBiz) {
        // get lock
        IDistLock distLock = lockManager.buildDistLock(lockKey);
        Boolean locked = false;
        try {
            locked = distLock.tryLock(waitTime, leaseTime, unit);
            if (locked) {
                return lockTaskBiz.doBusiness();
            } else {
                throw new BusinessException(GeneralCode.GET_REDIS_LOCKED_FAILED);
            }
        } catch (BusinessException e) {
            // 业务异常继续抛出
            throw e;
        } catch (Exception e) {
            log.error("业务处理失败", e);
            throw new BusinessException(GeneralCode.SYS_ERROR);
        } finally {
            // release lock
            if (locked) {
                distLock.unlock();
            }
        }
    }

    /**
     * 默认10s获取不到锁立刻释放报错，如果获取到锁，最大锁住20s，执行任务时间很长的需要自己指定
     * 
     * @param lockKey
     * @param lockTaskBiz
     * @param <R>
     * @return
     */
    public <R> R lock(String lockKey, LockTaskBiz<R> lockTaskBiz) {
        return lock(lockKey, WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS, lockTaskBiz);
    }

}
