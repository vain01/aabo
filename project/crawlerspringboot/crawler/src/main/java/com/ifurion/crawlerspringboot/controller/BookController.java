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
	 * @param isRapid 使用缓存类目,快速爬取
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bookList", method = RequestMethod.GET)
	public List<Book> getBookList(Boolean isRapid) throws Exception {
		if (isRapid == null) {
			isRapid = true;
		}
		return crawler.getBookList(isRapid);
	}

	/**
	 * 获取计算机网络项目下的所有分类(到第四级)url
	 *
	 * @return ["/cp01.54.08.00.00.00.html","/cp01.54.06.00.00.00.html"]
	 * @throws Exception
	 */
	@RequestMapping(value = "bookCategoryUrls", method = RequestMethod.GET)
	public List<String> getCategoryUrls(Boolean isRapid) throws Exception {
		if (isRapid == null) {
			isRapid = true;
		}
		return crawler.getCategoryUrls(isRapid);
	}

	@RequestMapping(value = "allBooks")
	public List<Book> selectAllBooks() {
		return crawler.selectAllBooks();
	}

	@RequestMapping(value = "allBooksCount")
	public int selectAllBooksCount() {
		return crawler.selectAllBooksCount();
	}

	@RequestMapping(value = "bookQuery")
	public List<Book> selectBookByName(String name) {
		return crawler.selectBookByName(name);
	}

	@RequestMapping(value = "bookInsertion")
	public int insertBook() {
		return crawler.insertBook();
	}

	@RequestMapping(value = "emptyBookDb")
	public int deleteAllBooks() {
		return crawler.deleteAllBooks();
	}

	@RequestMapping(value = "initializer")
	public String init() throws IOException {
		return crawler.init();
	}
}
