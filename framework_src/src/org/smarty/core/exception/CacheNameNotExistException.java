package org.smarty.core.exception;

/**
 * 缓存名字不存在
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class CacheNameNotExistException extends Exception {
    public CacheNameNotExistException() {
        super();
    }

    public CacheNameNotExistException(Throwable cause) {
        super(cause);
    }
}
