package org.smarty.security.common;

import org.smarty.core.utils.LogicUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 监听器 - 后台登录成功、登录失败处理
 */
public class SecurityListener implements ApplicationListener, BeanFactoryAware {
	private ISecurityService securityService;
	private int loginFailureMaxCount = -1;

	public void onApplicationEvent(ApplicationEvent event) {
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
		if (LogicUtil.isEmpty(id)) {
			throw new UsernameNotFoundException(username);
		}
		if (!securityService.isLocked(id)) {
			securityService.update(id, 0, getIp(authentication));
		}
	}

	/**
	 * 登录失败
	 */
	private void failLogin(Authentication authentication) {
		String username = authentication.getName();
		String id = securityService.getLoginId(username);
		if (LogicUtil.isEmpty(id)) {
			throw new UsernameNotFoundException(username);
		}

		if (!securityService.isLocked(id)) {
			return;
		}
		int lfc = securityService.getLoginFailureCount(username) + 1;

		if (loginFailureMaxCount > -1 && lfc >= loginFailureMaxCount) {
			securityService.lock(id);
		}
		securityService.update(id, lfc, getIp(authentication));
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.securityService = beanFactory.getBean(ISecurityService.class);
	}

	public void setLoginFailureMaxCount(int loginFailureMaxCount) {
		this.loginFailureMaxCount = loginFailureMaxCount;
	}

	private String getIp(Authentication authentication) {
		return ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
	}
}