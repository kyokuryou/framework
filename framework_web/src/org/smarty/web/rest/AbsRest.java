package org.smarty.web.rest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.utils.JsonUtil;
import org.smarty.web.commons.WebBaseConstant;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * RESTFUL
 */
public abstract class AbsRest {
	private final Log logger = LogFactory.getLog(getClass());
	private final RestTemplate rest = new RestTemplate(getMessageConverter());
	private HttpHeaders headers;

	public AbsRest() {
		rest.setRequestFactory(getRequestFactory());
		rest.setErrorHandler(getResponseErrorHandler());
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpHeaders getHeaders() {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		return headers;
	}

	protected abstract void delete(String url, Map<String, Object> urlVar);

	protected abstract void put(String url, Map<String, Object> urlVar);

	protected abstract void get(String url, Map<String, Object> urlVar);

	protected abstract void post(String url, Map<String, Object> urlVar);

	protected final ClientHttpRequestFactory getRequestFactory() {
		SimpleClientHttpRequestFactory schrf = new SimpleClientHttpRequestFactory();
		schrf.setConnectTimeout(WebBaseConstant.CONNECT_TIMEOUT);
		schrf.setReadTimeout(WebBaseConstant.READ_TIMEOUT);
		return schrf;
	}

	protected final ResponseErrorHandler getResponseErrorHandler() {
		return new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

			}
		};
	}

	protected final List<HttpMessageConverter<?>> getMessageConverter() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new StringHttpMessageConverter(WebBaseConstant.DEF_ENCODE));
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new GsonHttpMessageConverter());
		FormHttpMessageConverter fmc = new FormHttpMessageConverter();
		fmc.setPartConverters(messageConverters);
		messageConverters.add(fmc);
		return messageConverters;
	}

	protected final HttpEntity<String> getTextEntity(String text) {
		HttpHeaders headers = getHeaders();
		headers.setContentType(new MediaType("text", "plain", Charset.forName("ISO-8859-1")));
		logger.debug("header : " + headers);
		return new HttpEntity<String>(text, headers);
	}

	protected final HttpEntity<String> getXmlEntity(String xmlStr) {
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.TEXT_XML);
		logger.debug("header : " + headers);
		return new HttpEntity<String>(xmlStr, headers);
	}

	protected final HttpEntity<String> getHtmlEntity(String xmlStr) {
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		logger.debug("header : " + headers);
		return new HttpEntity<String>(xmlStr, headers);
	}

	protected final HttpEntity<MultiValueMap<String, Object>> getFileEntity(Map<String, File> urlVar) {
		MultiValueMap<String, Object> formData = createFormData(urlVar);
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		logger.debug("header : " + headers);
		return new HttpEntity<MultiValueMap<String, Object>>(formData, headers);
	}

	protected final HttpEntity<String> getJsonEntity(Map<String, Object> urlVar) {
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(JsonUtil.encode(urlVar), headers);
	}

	protected final HttpEntity<MultiValueMap<String, Object>> getPostEntity(Map<String, Object> urlVar) {
		MultiValueMap<String, Object> formData = createFormData(urlVar);
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		return new HttpEntity<MultiValueMap<String, Object>>(formData, headers);
	}

	protected final HttpEntity<String> getGetEntity() {
		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new HttpEntity<String>(headers);
	}

	protected final HttpEntity<MultiValueMap<String, Object>> getPutEntity(Map<String, Object> urlVar) {
		return getPostEntity(urlVar);
	}

	protected final HttpEntity<String> getDeleteEntity() {
		return getGetEntity();
	}

	private MultiValueMap<String, Object> createFormData(Map<String, ?> urlVar) {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		if (urlVar == null) {
			return formData;
		}
		Set<? extends Map.Entry<String, ?>> mes = urlVar.entrySet();
		for (Map.Entry<String, ?> me : mes) {
			Object val = me.getValue();
			if (val instanceof File || val instanceof File[]) {
				formData.put(me.getKey(), getMultiFile(val));
				continue;
			}
			formData.set(me.getKey(), val);
		}
		return formData;
	}

	protected final void sendEntityBody(RestModel model) {
		RestHandler handler = model.getHandler();
		sendBefore(handler);
		try {
			ResponseEntity<byte[]> re = rest.exchange(model.getUrl(), model.getMethod(), model.getEntity(), byte[].class);
			if (re == null) {
				return;
			}
			HttpStatus status = re.getStatusCode();
			logger.debug("http status :" + status.value() + ":" + status.getReasonPhrase());
			boolean isParse = status.is2xxSuccessful();
			if (isParse) {
				byte[] body = re.getBody();
				logger.debug("response body : " + body);
				sendHandler(handler, body);
			} else {
				sendBad(handler, status.value());
			}
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	protected final void sendBefore(RestHandler handler) {
		if (handler == null) {
			return;
		}
		handler.beforeSend();
	}

	protected final void sendHandler(RestHandler handler, byte[] data) {
		if (handler == null) {
			return;
		}
		handler.complete(data);
	}

	protected final void sendBad(RestHandler handler, int statusCode) {
		if (handler == null) {
			return;
		}
		handler.bad(statusCode);
	}

	private List<Object> getMultiFile(Object file) {
		if (file == null) {
			return null;
		}

		List<Object> fsrs = new ArrayList<Object>();
		Class<?> fc = file.getClass();
		if (!fc.isArray()) {
			fsrs.add(new FileSystemResource((File) file));
		} else {
			int len = Array.getLength(file);
			for (int i = 0; i < len; i++) {
				File f = (File) Array.get(file, i);
				fsrs.add(new FileSystemResource(f));
			}
		}
		return fsrs;
	}
}
