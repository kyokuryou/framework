package org.test.commons;

import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;

/**
 * @author qul
 * @since LVGG1.1
 */
public class LvggLocaleResolver extends AbstractLocaleContextResolver {
	public static final String LOCALE_REQUEST_NAME = LvggLocaleResolver.class.getName() + ".LOCALE";
	public static final String TIMEZONE_REQUEST_NAME = LvggLocaleResolver.class.getName() + ".TIMEZONE";

	@Override
	public LocaleContext resolveLocaleContext(HttpServletRequest request) {
		final String locale = request.getHeader("locale");
		final String timeZone = request.getHeader("timezone");
		return new TimeZoneAwareLocaleContext() {

			@Override
			public Locale getLocale() {
				if (ObjectUtil.isEmpty(locale)) {
					return BaseConstant.DEF_LOCALE;
				}
				return StringUtils.parseLocaleString(locale);
			}

			@Override
			public TimeZone getTimeZone() {
				if (ObjectUtil.isEmpty(timeZone)) {
					return BaseConstant.DEF_TIMEZONE;
				}
				return TimeZone.getTimeZone(timeZone);
			}
		};
	}

	@Override
	public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
		Locale locale = null;
		TimeZone timeZone = null;
		if (localeContext != null) {
			locale = localeContext.getLocale();
			if (localeContext instanceof TimeZoneAwareLocaleContext) {
				timeZone = ((TimeZoneAwareLocaleContext) localeContext).getTimeZone();
			}
		}
		System.out.println(locale.getDisplayName());

		request.setAttribute(LOCALE_REQUEST_NAME, (locale != null ? locale : getDefaultLocale()));
		request.setAttribute(TIMEZONE_REQUEST_NAME, (timeZone != null ? timeZone : getDefaultTimeZone()));
	}
}
