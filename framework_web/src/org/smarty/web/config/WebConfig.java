package org.smarty.web.config;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import freemarker.template.utility.XmlEscape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.smarty.core.config.AdapterProcess;
import org.smarty.core.config.SystemConfig;
import org.smarty.web.commons.CaptchaEngine;
import org.smarty.web.commons.FreemarkerManager;
import org.smarty.web.commons.GenerateHtml;
import org.smarty.web.commons.WebBaseConstant;
import org.smarty.web.support.rest.RestTask;
import org.smarty.web.utils.SpringWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * SecurityConfigurer
 */
@Configuration(value = "web_system")
@EnableWebMvc
@Import(value = {SystemConfig.class})
@ComponentScan(useDefaultFilters = false, basePackages = "org.smarty.web", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class)
})
@Order(0)
public class WebConfig extends WebMvcConfigurerAdapter implements ServletContextAware, MessageSourceAware {
	public static final String MULTIPART_RESOLVER_NAME = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME;
	public static final String CAPTCHA_SERVICE_NAME = "captchaService";
	public static final String REST_TASK_NAME = "restTask";
	public static final String MESSAGE_SOURCE_NAME = "messageSource";
	public static final String THEME_SOURCE_NAME = "themeSource";
	public static final String GENERATE_HTML_NAME = "generateHtml";
	public static final String FREEMARKER_MANAGER_NAME = "freemarkerManager";
	public static final String FREEMARKER_CONFIGURER_NAME = "freemarkerConfigurer";
	private WebConfigAdapter configAdapter = new DefaultConfigAdapter();
	@Autowired
	private AdapterProcess adapterProcess;
	@Value("${debug:false}")
	private boolean debug;
	@Value("${resources.ftl}")
	private String ftlPath;
	@Value("${resources.jsp}")
	private String jspPath;
	@Value("${resources.html}")
	private String htmlPath;
	@Value("${content.type}")
	private String contentType;
	@Value("${context.attribute}")
	private String contextAttribute;
	@Value("${message.names:message}")
	private String[] messageSourceNames;
	@Value("${theme.name:theme}")
	private String themeSourceNames;
	@Value("${upload.maxSize:1048576}")
	private long uploadMaxSize;
	@Value("${async.timeout:30000}")
	private long asyncTimeout;
	@Value("${captcha.storage.delay:600}")
	private int captchaStorageDelay;

	@Autowired(required = false)
	public void setConfigAdapter(WebConfigAdapter configAdapter) {
		this.configAdapter = configAdapter;
	}

	@Bean(name = MESSAGE_SOURCE_NAME)
	public MessageSource getMessageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setCacheSeconds(3600);
		ms.setUseCodeAsDefaultMessage(true);
		ms.setBasenames(messageSourceNames);
		return ms;
	}

	@Bean(name = THEME_SOURCE_NAME)
	public ThemeSource getThemeSource() {
		ResourceBundleThemeSource ts = new ResourceBundleThemeSource();
		ts.setBasenamePrefix(themeSourceNames);
		return ts;
	}

	@Bean(name = MULTIPART_RESOLVER_NAME)
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		mr.setResolveLazily(true);
		mr.setMaxUploadSize(uploadMaxSize);
		return mr;
	}

	@Bean(name = FREEMARKER_MANAGER_NAME)
	public FreemarkerManager getFreemarkerManager(FreeMarkerConfigurer freeMarkerConfigurer) {
		FreemarkerManager fm = new FreemarkerManager();
		fm.setFreeMarkerConfigurer(freeMarkerConfigurer);
		return fm;
	}

	@Bean(name = FREEMARKER_CONFIGURER_NAME)
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		Properties fss = new Properties();
		fss.setProperty("template_update_delay", debug ? "0" : "3600");
		fss.setProperty("default_encoding", WebBaseConstant.STR_CHARSET);
		fss.setProperty("locale", WebBaseConstant.STR_LOCALE);
		fss.setProperty("classic_compatible", "true");
		fss.setProperty("whitespace_stripping", "true");
		fss.setProperty("url_escaping_charset", WebBaseConstant.STR_CHARSET);
		fss.setProperty("date_format", WebBaseConstant.DEF_DATE_FORMAT);
		fss.setProperty("time_format", WebBaseConstant.DEF_TIME_FORMAT);
		fss.setProperty("datetime_format", WebBaseConstant.DEF_DATETIME_FORMAT);
		fss.setProperty("number_format", WebBaseConstant.DEF_ARITH_FORMAT);
		fss.setProperty("boolean_format", "true,false");
		fss.setProperty("tag_syntax", "auto_detect");
		fss.setProperty("auto_import", "/spring.ftl as spring");

		Map<String, Object> fvs = new HashMap<String, Object>();
		fvs.put("xml_escape", new XmlEscape());

		FreeMarkerConfigurer fmc = new FreeMarkerConfigurer();
		fmc.setDefaultEncoding(WebBaseConstant.STR_CHARSET);
		fmc.setTemplateLoaderPath("/");
		fmc.setFreemarkerSettings(fss);
		fmc.setFreemarkerVariables(fvs);
		return fmc;
	}

	/**
	 * 生成静态画面
	 */
	@Bean(name = GENERATE_HTML_NAME)
	@Lazy
	public GenerateHtml getGenerateHtml(FreemarkerManager freemarkerManager) {
		GenerateHtml gh = new GenerateHtml();
		gh.setCachePath(htmlPath);
		gh.setFreemarkerManager(freemarkerManager);
		return gh;
	}

	@Bean(name = CAPTCHA_SERVICE_NAME)
	@Lazy
	public ImageCaptchaService getImageCaptchaService() {
		DefaultManageableImageCaptchaService ics = new DefaultManageableImageCaptchaService();
		ics.setCaptchaEngine(new CaptchaEngine());
		ics.setMinGuarantedStorageDelayInSeconds(captchaStorageDelay);
		return ics;
	}

	@Bean(name = REST_TASK_NAME)
	@Lazy
	public RestTask getRestTask() {
		RestTask rt = new RestTask();
		rt.setTaskExecutor(adapterProcess.getShareObject(AsyncTaskExecutor.class));
		return rt;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		configAdapter.addInterceptors(registry);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter shmc = new StringHttpMessageConverter();

		List<MediaType> mts = new ArrayList<MediaType>();
		mts.add(new MediaType("text", "plain", WebBaseConstant.DEF_ENCODE));
		mts.add(new MediaType("text", "html", WebBaseConstant.DEF_ENCODE));

		shmc.setSupportedMediaTypes(mts);
		configAdapter.configure(converters);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		FreeMarkerViewResolver fmvr = new FreeMarkerViewResolver();
		fmvr.setViewClass(FreeMarkerView.class);
		fmvr.setContentType(contentType);
		fmvr.setRequestContextAttribute(contextAttribute);
		fmvr.setPrefix(ftlPath);
		fmvr.setViewNames("*.ftl");
		fmvr.setExposeSessionAttributes(true);
		fmvr.setExposeRequestAttributes(true);
		fmvr.setExposeSpringMacroHelpers(true);
		fmvr.setCache(true);
		fmvr.setOrder(0);

		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setViewClass(JstlView.class);
		irvr.setContentType(contentType);
		irvr.setPrefix(jspPath);
		irvr.setViewNames("*.jsp");
		irvr.setCache(true);
		irvr.setOrder(1);

		registry.viewResolver(fmvr);
		registry.viewResolver(irvr);
		configAdapter.configure(registry);
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setTaskExecutor(adapterProcess.getShareObject(AsyncTaskExecutor.class));
		configurer.setDefaultTimeout(asyncTimeout);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		SpringWebUtil.setServletContext(servletContext);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		SpringWebUtil.setMessageSource(messageSource);
	}

	public class DefaultConfigAdapter extends WebConfigAdapter {

	}
}