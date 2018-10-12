package com.ifurion.crawlerspringboot.service;

import com.ifurion.crawlerspringboot.dao.BookDao;
import com.ifurion.crawlerspringboot.entity.Book;
import com.ifurion.crawlerspringboot.util.HttpClientUtils;
import com.ifurion.crawlerspringboot.util.HttpDocumentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ifurion.crawlerspringboot.constant.Constant.*;


/**
 * @author haoliang on 2018/10/10.
 */
@Slf4j
@Service
public class Crawler implements Runnable{
	@Resource
	private BookDao bookDao;

	/**
	 * 获取每个类目下的计算机书目信息
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Book> getBookList() throws Exception {
		List<String> categoryUrls = getCategoryUrls();
		if (CollectionUtils.isEmpty(categoryUrls)) {
			return null;
		}
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < categoryUrls.size(); i++) {
			log.info("{},爬取URL:{}", i + 1, categoryUrls.get(i));
			for (int j = 1; j <= 100; j++) {
				fetchBooksInfo(categoryUrls, books, i, j);
			}
			log.info("当前爬到的书籍条目:{}", books.size());
		}

		return books;
	}

	private void fetchBooksInfo(List<String> categoryUrls, List<Book> books, int i, int j) throws Exception {
		String url = compileUrl(categoryUrls, i, j, POSTFIX_SCORE_DESC);
		String htmlContent = HttpClientUtils.getHtmlContent(url);
		List<Book> bookList = HttpDocumentUtils.getBookList(htmlContent);
		if (!CollectionUtils.isEmpty(bookList)) {
			saveBookToDb(bookList);
			books.addAll(bookList);
		}
	}

	private void saveBookToDb(List<Book> bookList) {
		if (CollectionUtils.isEmpty(bookList)) {
			return;
		}
		for (int i = 0; i < bookList.size(); i++) {
			System.out.println(bookList.get(i));
			bookDao.insert(bookList.get(i));
		}
	}

	private String compileUrl(List<String> categoryUrls, int i, int pageNo, String postfix) {
		String path = categoryUrls.get(i)
			.replace(PATH_SPLIT_CHAR, PATH_SPLIT_CHAR + PREFIX_PAGE + pageNo + HYPHEN)
			.replace(POSTFIX_HTML_FILE_EXTENTION, postfix);
		return HOST + path;
	}

	/**
	 * 获取计算机网络项目下的所有分类url
	 *
	 * @return ["/cp01.54.08.00.00.00.html","/cp01.54.06.00.00.00.html"]
	 * @throws Exception
	 */
	public List<String> getCategoryUrls() throws Exception {
		String url = CATEGORY_URL;
		String htmlContent = HttpClientUtils.getHtmlContent(url);
		List<String> result = HttpDocumentUtils.getBookCategoryUrls(htmlContent);
		return result;
	}

	public List<Book> testSqlite() {
		return bookDao.selectAll();
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {
			
	}
}
