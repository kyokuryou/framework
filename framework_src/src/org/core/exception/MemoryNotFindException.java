package org.core.exception;

/**
 * 缓存不存在或已经被删除,抛出此异常
 */
public class MemoryNotFindException extends Exception {
    public MemoryNotFindException() {
        super();
    }

    public MemoryNotFindException(Throwable cause) {
        super(cause);
    }
}
