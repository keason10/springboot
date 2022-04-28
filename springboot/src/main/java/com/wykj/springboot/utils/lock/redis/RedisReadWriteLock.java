package com.wykj.springboot.utils.lock.redis;

import com.wykj.springboot.utils.lock.IReadWriteLock;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;

public class RedisReadWriteLock implements IReadWriteLock {
    private String lockKey;
    private Integer lockSeconds;
    private Integer tryTimes;
    private RReadWriteLock readWriteLock;
    private RLock rLock;
    private RLock wLock;

    public RedisReadWriteLock(String lockKey, Integer lockSeconds, Integer tryTimes) {
        this.lockKey = lockKey;
        this.lockSeconds = lockSeconds;
        this.tryTimes = tryTimes;
        readWriteLock = RedisCfg.getClient()
                .getReadWriteLock(this.lockKey);
    }

    @Override
    public boolean tryWLock() throws Exception {
        this.wLock = this.readWriteLock.writeLock();
        for (int i = 0; i < this.tryTimes; i++) {
            if (wLock.tryLock(this.lockSeconds, TimeUnit.SECONDS)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryRLock() throws Exception {
        this.rLock = readWriteLock.readLock();
        for (int i = 0; i < tryTimes; i++) {
            if (rLock.tryLock(lockSeconds, TimeUnit.SECONDS)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void rUnlock(){
        this.rLock.unlock();
    }


    @Override
    public void wUnlock() {
        this.wLock.unlock();
    }



}
