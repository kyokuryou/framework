package org.core.exception;

/**
 * 缓存名字已存在
 */
public class CacheNameExistException extends Exception {

    public CacheNameExistException() {
        super();
    }

    public CacheNameExistException(Throwable cause) {
        super(cause);
    }
}
