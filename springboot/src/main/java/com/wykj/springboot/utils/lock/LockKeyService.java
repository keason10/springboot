package com.wykj.springboot.utils.lock;

public interface LockKeyService {
    String getKey(Object cmd);
}
