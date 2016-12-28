package com.aoshi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	private static HttpUtils instance;
	private static HttpClient httpClient;

	public synchronized static HttpUtils getInstance() {
		if (instance == null) {
			instance = new HttpUtils();
			httpClient = HttpClients.createDefault();
		}
		return instance;
	}

	/**
	 * 
	 * @param path
	 * @param params
	 *            模拟http post
	 * @param encode
	 * @return
	 */
	public String sendPostMethod(String path, Map<String, Object> params, String encoding) {
		HttpPost httpPost = new HttpPost(path);
		String result = "";
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		try {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					String name = entry.getKey();
					String value = entry.getValue().toString();
					BasicNameValuePair valuePair = new BasicNameValuePair(name, value);
					parameters.add(valuePair);
				}
			}
			// 纯文本表单，不包含文件
			UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters, encoding);
			httpPost.setEntity(encodedFormEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(), encoding);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param path
	 * @param params
	 *            模拟http get
	 * @param encode
	 * @return
	 */
	public String sendGetMethod(String path, Map<String, Object> params, String encoding) throws Exception {
		String result = "";
		String parameters = "";

		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				String name = entry.getKey();
				String value = entry.getValue().toString();
				parameters = parameters + name + "=" + value + "&";
			}
			parameters = parameters.substring(0, parameters.length() - 1);
		}

		HttpGet httpGet = new HttpGet(path + "?" + parameters);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity(), encoding);
		}

		return result;
	}
}
