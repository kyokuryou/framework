package org.smarty.security.support.listener;

import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.support.service.SecurityService;
import org.smarty.web.support.listener.WebListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 监听器 - 后台登录成功、登录失败处理
 */
public class SecurityListener extends WebListener {
	private SecurityService securityService;
	private int failureMaxCount;

	public void setFailureMaxCount(int failureMaxCount) {
		this.failureMaxCount = failureMaxCount;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public void onEvent(ApplicationEvent event) {
		// 登录成功：记录登录IP、清除登录失败次数
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
			successLogin((Authentication) authEvent.getSource());
		}
		// 登录失败：增加登录失败次数
		if (event instanceof AuthenticationFailureBadCredentialsEvent) {
			AuthenticationFailureBadCredentialsEvent authEvent = (AuthenticationFailureBadCredentialsEvent) event;
			failLogin((Authentication) authEvent.getSource());
		}
	}

	/**
	 * 登录成功
	 */
	private void successLogin(Authentication authentication) {
		String username = authentication.getName();
		String id = securityService.getLoginId(username);
		if (ObjectUtil.isEmpty(id)) {
			throw new UsernameNotFoundException(username);
		}
		if (!securityService.isLocked(id)) {
			securityService.update(id, 0, getRemoteAddress(authentication));
		}
	}

	/**
	 * 登录失败
	 */
	private void failLogin(Authentication authentication) {
		String username = authentication.getName();
		String id = securityService.getLoginId(username);
		if (ObjectUtil.isEmpty(id)) {
			throw new UsernameNotFoundException(username);
		}

		if (!securityService.isLocked(id)) {
			return;
		}
		int lfc = securityService.getLoginFailureCount(username) + 1;

		if (failureMaxCount > 0 && lfc >= failureMaxCount) {
			securityService.lock(id);
		}
		securityService.update(id, lfc, getRemoteAddress(authentication));
	}

	private String getRemoteAddress(Authentication authentication) {
		return ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
	}
}