package org.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * Created Date 2015/04/13
 *
 * @author kyokuryou
 * @version 1.0
 */
@Aspect
public class DaoAop implements Ordered {

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void atRepository() {

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
