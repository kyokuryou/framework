package org.smarty.security.support.service.impl;

import java.util.Date;
import org.smarty.core.utils.DateUtil;
import org.smarty.core.utils.ObjectUtil;
import org.smarty.security.bean.UserSecurity;
import org.smarty.security.support.service.SecurityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetailsServiceImpl
 */
public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean {
	private SecurityService securityService;
	private int failureLockTime;

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public void setFailureLockTime(int failureLockTime) {
		this.failureLockTime = failureLockTime;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectUtil.assertNotEmpty(securityService, "securityService must not null");
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		String id = securityService.getLoginId(username);
		if (id == null) {
			throw new UsernameNotFoundException(username);
		}
		UserSecurity us = securityService.getUserSecurity(id);
		if (us.isAccountNonLocked()) {
			return us;
		}
		if (canUnLock(us.getLockedDate())) {
			us.setAccountNonLocked(securityService.unlock(id));
		}
		return us;
	}

	/**
	 * 解除管理员账户锁定
	 */
	private boolean canUnLock(Date lockedDate) {
		if (lockedDate == null || failureLockTime == 0) {
			return true;
		}
		Date nlt = DateUtil.addMinute(lockedDate, failureLockTime);
		return nlt.before(new Date());
	}
}