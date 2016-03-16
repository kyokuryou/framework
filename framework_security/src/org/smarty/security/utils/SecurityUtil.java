package org.smarty.security.utils;

import org.smarty.security.support.service.FilterMetadataSource;

/**
 * SecurityUtil
 */
public final class SecurityUtil  {
	private static FilterMetadataSource filterMetadataSource;

	public static FilterMetadataSource getFilterMetadataSource() {
		return filterMetadataSource;
	}

	public static void setFilterMetadataSource(FilterMetadataSource filterMetadataSource) {
		SecurityUtil.filterMetadataSource = filterMetadataSource;
	}
}
