package com.ifurion.crawler.api;

import com.ifurion.crawler.model.Book;
import com.ifurion.crawler.service.Crawler;

import java.util.List;

/**
 * @author haoliang on 2018/10/9.
 */
public class Controller {
	private Crawler crawler = new Crawler();

	/**
	 * 获取每个类目下的计算机书目信息
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Book> getBookList() throws Exception {
		return crawler.getBookList();
	}

	/**
	 * 获取计算机网络项目下的所有分类url
	 *
	 * @return ["/cp01.54.08.00.00.00.html","/cp01.54.06.00.00.00.html"]
	 * @throws Exception
	 */
	public List<String> getCategoryUrls() throws Exception {
		return crawler.getCategoryUrls();
	}
}
