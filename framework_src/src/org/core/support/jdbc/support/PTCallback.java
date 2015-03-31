package org.core.support.jdbc.support;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

public interface PTCallback {
    public void doTransaction(TransactionStatus transactionStatus, PlatformTransactionManager transactionManager);
}
