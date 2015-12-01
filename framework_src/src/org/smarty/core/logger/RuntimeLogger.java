package org.smarty.core.logger;

import org.apache.log4j.Logger;
import org.smarty.core.common.BaseConstant;

public class RuntimeLogger {
    private static Logger logger;

    public RuntimeLogger(Class klass) {
        logger = Logger.getLogger(klass);
    }

    public void error(String memo) {
        logger.error(memo);
    }

    public void error(Throwable e) throws RuntimeException {
        logger.error("", e);
        throw new RuntimeException(e);
    }

    public void error(String memo, Throwable e) throws RuntimeException {
        logger.error(memo, e);
        throw new RuntimeException(e);
    }

    public void warn(String memo) {
        logger.warn(memo);
    }

    public void info(String memo) {
        logger.info(memo);
    }

    public void out(Object o) {
        out(o, null);
    }


    public void out(Throwable e) {
        out("", e);
    }

    public void out(Object memo, Throwable e) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        StringBuilder po = new StringBuilder();
        if (memo != null) {
            po.append(memo.toString());
        }
        if (e != null) {
            po.append(e.getMessage());
        }
        if (po.length() > 0) {
            BaseConstant.DEF_OUT.println(po.toString());
        }
    }


}
