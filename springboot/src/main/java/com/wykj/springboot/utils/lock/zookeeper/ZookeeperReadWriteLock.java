package com.wykj.springboot.utils.lock.zookeeper;

import com.wykj.springboot.utils.lock.IReadWriteLock;
import java.util.concurrent.TimeUnit;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock.ReadLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock.WriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperReadWriteLock implements IReadWriteLock {

    private final String lockKey;
    private Integer lockSeconds;
    private Integer tryTimes;
    private WriteLock writeLock;
    private ReadLock readLock;
    private final InterProcessReadWriteLock lock;

    public ZookeeperReadWriteLock(String lockKey, Integer lockSeconds, Integer tryTimes) {
        this.lockKey = lockKey;
        this.lockSeconds = lockSeconds;
        this.tryTimes = tryTimes;
        // localhost:2181 本地zookeeper地址
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new ExponentialBackoffRetry(1000, 3));
        client.start();
        // client.close();
        lock = new InterProcessReadWriteLock(client, this.lockKey);
    }

    @Override
    public boolean tryWLock() throws Exception {
        this.writeLock = lock.writeLock();
        for (int i = 0; i < this.tryTimes; i++) {
            if (writeLock.acquire(this.lockSeconds, TimeUnit.SECONDS)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tryRLock() throws Exception {
        this.readLock = lock.readLock();
        for (int i = 0; i < this.tryTimes; i++) {
            if (readLock.acquire(this.lockSeconds, TimeUnit.SECONDS)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void rUnlock() throws Exception {
        this.readLock.release();
    }

    @Override
    public void wUnlock() throws Exception {
        this.writeLock.release();
    }

    public static void main(String[] args) throws Exception {
        ZookeeperReadWriteLock zookeeperReadWriteLock = new ZookeeperReadWriteLock("/lock/keason", 3, 3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                    try {
                        boolean b = zookeeperReadWriteLock.tryRLock();
                        if (b) {
                            System.out.println("keason hello get read lock");
                        } else {
                            System.out.printf("keason hello  can not get read lock" + Thread.currentThread());
                        }
                        // zookeeperReadWriteLock.rUnlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    boolean b = zookeeperReadWriteLock.tryWLock();
                    if (b) {
                        System.out.println("keason hello get write lock");
                    } else {
                        System.out.printf("keason hello  can not get write lock" + Thread.currentThread());
                    }
                    // zookeeperReadWriteLock.wUnlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(10000);
        zookeeperReadWriteLock.rUnlock();
        zookeeperReadWriteLock.wUnlock();
    }
}
