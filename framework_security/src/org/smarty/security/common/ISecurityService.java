package org.smarty.security.common;

import java.util.List;
import org.smarty.security.bean.Resource;
import org.smarty.security.bean.UserSecurity;

/**
 * 接口
 */
public interface ISecurityService {
	String getLoginId(String username);

	int getLoginFailureCount(String id);

	boolean update(String id, int failureCount, String ip);

	boolean isLocked(String id);

	boolean lock(String id);

	boolean unlock(String id);

	UserSecurity getUserSecurity(String id);

	List<Resource> getResourceList(String url);
}
