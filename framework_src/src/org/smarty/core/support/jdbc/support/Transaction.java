package org.smarty.core.support.jdbc.support;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 事务管理
 */
final class Transaction {
    private int timeout = 120;
    private int isolationLevel = TransactionDefinition.ISOLATION_REPEATABLE_READ;
    private int propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED;

    private DataSource targetDataSource;
    private PlatformTransactionManager transactionManager;

    public Transaction(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;

    }

    public DataSource createTransactionManager(boolean create) {
        if (create) {
            TransactionAwareDataSourceProxy dataSourceProxy;
            dataSourceProxy = new TransactionAwareDataSourceProxy(targetDataSource);
            transactionManager = new DataSourceTransactionManager(dataSourceProxy);
            return dataSourceProxy;
        }else{
            return targetDataSource;
        }
    }

    public final PlatformTransactionManager getTransactionManager() {
        if (transactionManager == null) {
            createTransactionManager(true);
        }
        return transactionManager;
    }

    /**
     * 返回一个新事务模板
     *
     * @return transactionTemplate
     */
    public final TransactionTemplate getTransactionTemplate() {
        if (transactionManager == null) {
            createTransactionManager(true);
        }
        return new TransactionTemplate(transactionManager);
    }

    /**
     * 返回Connection
     *
     * @return connection
     */
    public final Connection getConnection() {
        return DataSourceUtils.getConnection(targetDataSource);
    }


    /**
     * @param isolationLevel 隔离级别
     */
    public final void setIsolationLevel(int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    /**
     * @param timeout 超时时间(秒)
     */
    public final void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @param propagationBehavior 事务传播行为类型
     */
    public void setPropagationBehavior(int propagationBehavior) {
        this.propagationBehavior = propagationBehavior;
    }

    /**
     * 事务模板 执行事务,自动提交,出现异常时自动回滚,如果需要手动回滚使用status.setRollbackOnly();
     * 默认:
     * 隔离级别:ISOLATION_REPEATABLE_READ;
     * 事务传播行为类型:PROPAGATION_REQUIRED;
     * 超时时间:120秒;
     *
     * @param methodName 当前方法名称,将以作为事务的名字
     * @return Object
     */
    public <T> T doAutoTransaction(String methodName, TransactionCallback<T> action) {
        TransactionTemplate transactionTemplate;
        String transactionName = this.getClass().getName() + "." + methodName;
        if (transactionManager == null) {
            createTransactionManager(true);
        }
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setName(transactionName);
        transactionTemplate.setTimeout(timeout);
        transactionTemplate.setIsolationLevel(isolationLevel);
        transactionTemplate.setPropagationBehavior(propagationBehavior);
        return transactionTemplate.execute(action);
    }

    /**
     * 执行事务,需要手动进行提交和回滚操作.
     * 默认:
     * 隔离级别:ISOLATION_REPEATABLE_READ;
     * 事务传播行为类型:PROPAGATION_REQUIRED;
     * 超时时间:120秒;
     *
     * @param methodName 当前方法名称,将以作为事务的名字
     * @param action
     * @return
     */
    public void doManualTransaction(String methodName, PTCallback action) {
        String transactionName = this.getClass().getName() + "." + methodName;
        DefaultTransactionDefinition def;
        if (transactionManager == null) {
            createTransactionManager(true);
        }
        def = new DefaultTransactionDefinition();
        def.setName(transactionName);
        def.setTimeout(timeout);
        def.setIsolationLevel(isolationLevel);
        def.setPropagationBehavior(propagationBehavior);
        TransactionStatus status = transactionManager.getTransaction(def);
        action.doTransaction(status, transactionManager);
    }
}