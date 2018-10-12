package com.ifurion.crawlerspringboot.controller;


import com.ifurion.crawlerspringboot.entity.Book;
import com.ifurion.crawlerspringboot.service.Crawler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author haoliang on 2018/10/9.
 */
@RestController
@RequestMapping(value = "book")
public class BookController {
	@Resource
	private Crawler crawler;

	/**
	 * 获取每个类目下的计算机书目信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getBookList", method = RequestMethod.GET)
	public List<Book> getBookList() throws Exception {
		return crawler.getBookList();
	}

	/**
	 * 获取计算机网络项目下的所有分类url
	 *
	 * @return ["/cp01.54.08.00.00.00.html","/cp01.54.06.00.00.00.html"]
	 * @throws Exception
	 */
	@RequestMapping(value = "getCategoryUrls", method = RequestMethod.GET)
	public List<String> getCategoryUrls() throws Exception {
		return crawler.getCategoryUrls();
	}

	@RequestMapping(value = "selectAll")
	public List<Book> selectAll() {
		return crawler.selectAll();
	}

	@RequestMapping(value = "selectAllCount")
	public int selectAllCount() {
		return crawler.selectAllCount();
	}

	@RequestMapping(value = "selectByName")
	public List<Book> selectByName(String name) {
		return crawler.selectByName(name);
	}

	@RequestMapping(value = "insertOne")
	public int insertOne() {
		return crawler.insertOne();
	}

	@RequestMapping(value = "deleteAll")
	public int deleteAll() {
		return crawler.deleteAll();
	}

	@RequestMapping(value = "init")
	public String init() throws IOException {
		return crawler.init();
	}
}
