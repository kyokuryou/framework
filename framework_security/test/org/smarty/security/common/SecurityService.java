package org.smarty.security.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.smarty.security.bean.ResourceSecurity;
import org.smarty.security.bean.UserSecurity;
import org.springframework.stereotype.Component;


@Component
public class SecurityService implements ISecurityService {
	@Override
	public String getLoginId(String username) {
		if ("admin".equals(username)) {
			return "1";
		}
		return null;
	}

	@Override
	public boolean isLocked(String id) {
		return false;
	}

	@Override
	public int getLoginFailureCount(String id) {
		return 0;
	}

	@Override
	public boolean update(String id, int failureCount, String ip) {
		return true;
	}

	@Override
	public boolean lock(String id) {
		return true;
	}

	@Override
	public boolean unlock(String id) {
		return true;
	}

	@Override
	public UserSecurity getUserSecurity(String id) {
		UserSecurity us = new UserSecurity();
		us.setUid("1");
		us.setUsername("admin");
		// cc545b5c7d7e33d686d599022d8b5436
		us.setPassword("e10adc3949ba59abbe56e057f20f883e");
		us.setAccountNonLocked(true);
		us.setAccountNonExpired(true);
		us.setCredentialsNonExpired(true);
		us.setEnabled(true);

		us.setAuthorities(Arrays.asList(getAdmin()));

		return us;
	}

	@Override
	public List<ResourceSecurity> getResourceList(String url) {
		List<ResourceSecurity> res = new ArrayList<ResourceSecurity>();

		ResourceSecurity res1 = new ResourceSecurity();
		res1.setId(1);
		res1.setValue("/admin/**");
		res1.setAuthorities(Arrays.asList(getAdmin()));

		ResourceSecurity res2 = new ResourceSecurity();
		res2.setId(2);
		res2.setValue("/admin/test.do");
		res2.setAuthorities(Arrays.asList(getAnonymous()));

		res.add(res2);
		res.add(res1);
		return res;
	}

	private String getAdmin() {
		return "ROLE_ADMIN";
	}

	private String getAnonymous() {

		return "ROLE_ANONYMOUS";
	}
}
