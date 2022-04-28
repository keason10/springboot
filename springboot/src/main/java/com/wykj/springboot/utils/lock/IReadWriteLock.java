package com.wykj.springboot.utils.lock;

public interface IReadWriteLock {

    boolean tryWLock() throws Exception;

    boolean tryRLock() throws Exception;

    void rUnlock() throws Exception;

    void wUnlock() throws Exception;
}
