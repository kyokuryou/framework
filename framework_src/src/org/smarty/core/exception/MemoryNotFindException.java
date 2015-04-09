package org.smarty.core.exception;

/**
 * 缓存不存在或已经被删除,抛出此异常
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MemoryNotFindException extends Exception {
    public MemoryNotFindException() {
        super();
    }

    public MemoryNotFindException(Throwable cause) {
        super(cause);
    }
}
