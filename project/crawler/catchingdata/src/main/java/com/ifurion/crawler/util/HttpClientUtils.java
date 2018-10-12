package com.ifurion.crawler.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author haoliang on 2018/10/9.
 */
@Slf4j
public class HttpClientUtils {
	public static HttpResponse getRawHtml(String url) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet getMethod = new HttpGet(url);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		try {
			response = httpClient.execute(getMethod);
		} catch (IOException e) {
		} finally {
		}
		return response;
	}

	public static String getHtmlContent(String url) throws IOException {
		HttpResponse response = HttpClientUtils.getRawHtml(url);
		int statusCode = response.getStatusLine().getStatusCode();
		String entity = null;
		if (statusCode == HttpStatus.SC_OK && response.getEntity() != null) {
			entity = EntityUtils.toString(response.getEntity());
		}
		return entity;
	}

	/**
	 * 反爬虫
	 */
	public void antiCrawler() {

	}
}
