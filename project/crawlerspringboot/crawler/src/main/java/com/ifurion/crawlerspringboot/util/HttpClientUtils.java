package com.ifurion.crawlerspringboot.util;

import com.ifurion.crawlerspringboot.entity.IP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haoliang on 2018/10/9.
 */
@Slf4j
public class HttpClientUtils {
	public static List<IP> ipPool = new ArrayList<>();

	public static HttpResponse getRawHtml(String url, Boolean useIpPool) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet getMethod = new HttpGet(url);
		if (CollectionUtils.isEmpty(ipPool) || useIpPool != null && useIpPool == false) {
			HttpResponse response = httpClient.execute(getMethod);
			return response;
		}
		int num = 0;
		do {
			String ip = ipPool.get(num).getIp();
			String port = ipPool.get(num).getPort();
			HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).setSocketTimeout(3000).build();
			getMethod.setConfig(config);

			HttpResponse response;
			try {
				response = httpClient.execute(getMethod);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					return response;
				}
				num++;
			} catch (IOException e) {
				num++;
			}
		} while (num <= ipPool.size() - 1);
		return new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
	}

	public static String getHtmlContent(String url) throws IOException {
		return getHtmlContent(url, false);
	}

	public static String getHtmlContent(String url, Boolean useIpPool) throws IOException {
		HttpResponse response = HttpClientUtils.getRawHtml(url, useIpPool);
		int statusCode = response.getStatusLine().getStatusCode();
		String entity = null;
		if (statusCode == HttpStatus.SC_OK && response.getEntity() != null) {
			entity = EntityUtils.toString(response.getEntity());
		}
		return entity;
	}


}
