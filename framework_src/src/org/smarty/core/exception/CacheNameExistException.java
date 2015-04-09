package org.smarty.core.exception;

/**
 * 缓存名字已存在
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class CacheNameExistException extends Exception {

    public CacheNameExistException() {
        super();
    }

    public CacheNameExistException(Throwable cause) {
        super(cause);
    }
}
