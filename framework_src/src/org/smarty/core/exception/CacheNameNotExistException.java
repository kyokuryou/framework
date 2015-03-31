package org.smarty.core.exception;

/**
 * 缓存名字不存在
 */
public class CacheNameNotExistException extends Exception {
    public CacheNameNotExistException() {
        super();
    }

    public CacheNameNotExistException(Throwable cause) {
        super(cause);
    }
}
