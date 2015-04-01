package org.smarty.core.test;

import org.codehaus.xfire.client.Client;

import java.net.URL;
public class test {

	/**
	 * webService接口URL
	 */
	private String WebserviceUrl;

	public static void main(String[] args) throws Exception {
		test batch = new test();
		batch.excute();
	}

	/**
	 * 数据同步处理
	 * @throws Exception
	 */
	protected void excute() throws Exception {
		init();
		getWeatherStation("","EQUIP_PHYS","13629");
	}

	protected void init() throws Exception {
		WebserviceUrl = "http://localhost:7001/dky/service/equipMonFlagService?wsdl";
	}

	private 	 void  getWeatherStation(String a ,String b,String c)
	throws Exception {

		String urlstr = WebserviceUrl;
		Object[] name = null;

		name = callWebservice(
				"execute",urlstr, new Object[] {a,b,c});
	}

	/**
	 * 调用WEBSERVICE
	 * @param methodName
	 * @param url
	 * @param params
	 * @return
	 */
	public Object[] callWebservice(String methodName ,String url ,Object[] params) {

		URL serverUrl = null;
		Client client = null;
		String webServiceUrl = "";
		webServiceUrl = url;
		Object[] returnMessage =null;
		try {
			serverUrl = new URL(webServiceUrl);
			client = new Client(serverUrl);
			returnMessage  = client.invoke(methodName, params);
		} catch (Exception e) {
            e.printStackTrace();
		}
		return returnMessage;
	}


}
