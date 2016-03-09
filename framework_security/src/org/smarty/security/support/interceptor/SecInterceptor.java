package org.smarty.security.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.web.support.interceptor.AbsInterceptor;

/**
 * Created by Administrator on 2016/3/3.
 */
public class SecInterceptor extends AbsInterceptor {

	@Override
	public boolean preInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}
}
