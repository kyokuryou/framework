package org.smarty.security.common;

import java.util.Set;
import org.smarty.web.commons.WebLauncher;

/**
 * 启动加载项
 */
public class SecurityLauncher extends WebLauncher {

	@Override
	protected Set<ClassLoader> getLauncher() {
		Set<ClassLoader> cls = super.getLauncher();
		cls.add(SecurityLauncher.class.getClassLoader());
		return cls;
	}
}
