package org.smarty.web.rest;

import java.io.File;
import java.util.Map;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * REST FULL
 */
public class RestTask extends AbsRest {
	private AsyncTaskExecutor taskExecutor;

	public RestTask() {
		super();
	}

	public void setTaskExecutor(AsyncTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * 上传文件
	 *
	 * @param urlVar 文件名
	 */
	public void postFile(String url, Map<String, File> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getFileEntity(urlVar));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	public void postText(String url, String text) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getTextEntity(text));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	public void postJson(String url, Map<String, Object> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getJsonEntity(urlVar));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	/**
	 * 提交xml
	 *
	 * @param xmlStr xml字符串
	 */
	public void postXml(String url, String xmlStr) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getXmlEntity(xmlStr));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	/**
	 * 提交xml
	 *
	 * @param htmlStr html字符串
	 */
	public void postHtml(String url, String htmlStr) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getHtmlEntity(htmlStr));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	/**
	 * 删除操作
	 */
	public void delete(String url, Map<String, Object> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.DELETE);
		model.setEntity(getDeleteEntity());
		model.setUriVariables(urlVar);

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	/**
	 * 更新操作
	 */
	public void put(String url, Map<String, Object> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.PUT);
		model.setEntity(getPutEntity(urlVar));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}


	/**
	 * 获取数据操作
	 *
	 * @param urlVar 请求参数
	 */
	public void get(String url, Map<String, Object> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.GET);
		model.setEntity(getGetEntity());
		model.setUriVariables(urlVar);

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	/**
	 * 添加操作
	 *
	 * @param urlVar 请求参数
	 */
	public void post(String url, Map<String, Object> urlVar) {
		final RestModel model = new RestModel(url, HttpMethod.POST);
		model.setEntity(getPostEntity(urlVar));

		taskExecutor(new Runnable() {
			@Override
			public void run() {
				sendEntityBody(model);
			}
		});
	}

	private synchronized void taskExecutor(Runnable runnable) {
		// 执行网络请求
		taskExecutor.execute(runnable);
	}
}
