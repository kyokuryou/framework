package org.smarty.web.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.StrutsStatics;
import org.smarty.core.bean.SystemConfig;
import org.smarty.core.utils.SystemConfigUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断会员是否登录
 */
public class LoginVerifyInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -86246303854807787L;


    public String intercept(ActionInvocation invocation) throws Exception {
        SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
        ActionContext actionContext = invocation.getInvocationContext();
        String loginMemberId = (String) actionContext.getSession().get(systemConfig.getLoginSessionId());
        if (loginMemberId == null) {
            HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
            HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
            Cookie cookie = new Cookie(systemConfig.getLoginCookieName(), null);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "login";
        }
        return invocation.invoke();
    }
}