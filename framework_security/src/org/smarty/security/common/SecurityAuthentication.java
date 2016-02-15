package org.smarty.security.common;

import java.util.Date;
import org.smarty.core.utils.DateUtil;
import org.smarty.security.bean.UserSecurity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 后台权限认证
 */
public class SecurityAuthentication implements UserDetailsService, BeanFactoryAware {
	private ISecurityService securityService;
	private int loginFailureLockTime = 0;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		String id = securityService.getLoginId(username);
		if (id == null) {
			throw new UsernameNotFoundException(username);
		}
		UserSecurity us = securityService.getUserSecurity(id);
		if (us.isAccountNonLocked()) {
			us.setAccountNonLocked(!unLock(id, us.getLockedDate()));
		}
		return us;
	}

	/**
	 * 解除管理员账户锁定
	 */
	private boolean unLock(String id, Date lockedDate) {
		if (lockedDate == null) {
			return true;
		}
		if (loginFailureLockTime == 0) {
			return true;
		}
		Date nlt = DateUtil.addMinute(lockedDate, loginFailureLockTime);
		return nlt.before(new Date()) && securityService.unlock(id);
	}

	public void setLoginFailureLockTime(int loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.securityService = beanFactory.getBean(ISecurityService.class);
	}
}