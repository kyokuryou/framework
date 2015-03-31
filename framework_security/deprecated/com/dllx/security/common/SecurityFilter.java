package com.dllx.security.common;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collection;


public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        invoke(new FilterInvocation(request, response, chain));
    }

    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        // object为FilterInvocation对象
        // super.beforeInvocation(fi);
        // 源码
        // 1.获取请求资源的权限
        // 执行
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();
        Collection<ConfigAttribute> attributes = securityMetadataSource.getAttributes(fi);
        // 2.是否拥有权限


        try {
            getAccessDecisionManager().decide(authenticated, fi, attributes);
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(beforeInvocation(fi), null);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
        return FilterInvocation.class;
    }
}