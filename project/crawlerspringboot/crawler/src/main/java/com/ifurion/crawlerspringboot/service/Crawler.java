package com.ifurion.crawlerspringboot.service;

import com.ifurion.crawlerspringboot.dao.BookDao;
import com.ifurion.crawlerspringboot.entity.Book;
import com.ifurion.crawlerspringboot.entity.IP;
import com.ifurion.crawlerspringboot.util.HttpClientUtils;
import com.ifurion.crawlerspringboot.util.HttpDocumentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ifurion.crawlerspringboot.constant.Constant.*;


/**
 * @author haoliang on 2018/10/10.
 */
@Slf4j
@Service
public class Crawler {
	@Resource
	private BookDao bookDao;

	@Autowired
	private ThreadPoolTaskExecutor threadPool;

	/**
	 * 获取每个类目下的计算机书目信息
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Book> getBookList(Boolean isRapid) throws Exception {
		if (HttpClientUtils.ipPool.size() == 0) {
			HttpClientUtils.ipPool = bookDao.selectIps();
		}
		List<String> categoryUrls = getCategoryUrls(isRapid);
		if (CollectionUtils.isEmpty(categoryUrls)) {
			return null;
		}
		List<Book> books = new ArrayList<>();
		for (int i = 0; i < categoryUrls.size(); i++) {
			String categoryUrl = categoryUrls.get(i);
			//并发执行
			this.threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						for (int j = 1; j <= 100; j++) {
							String url = compileUrl(categoryUrl, j, POSTFIX_SCORE_DESC);
							fetchBooksInfo(books, url);
						}
					} catch (Exception e) {
					}
				}
			});
		}

		return books;
	}

	private void fetchBooksInfo(List<Book> books, String url) throws Exception {
		String htmlContent = HttpClientUtils.getHtmlContent(url, true);
		if (StringUtils.isBlank(htmlContent)) {
			return;
		}
		List<Book> bookList = HttpDocumentUtils.getBookList(htmlContent);
		if (!CollectionUtils.isEmpty(bookList)) {
			bookDao.insertBatch(bookList);
			books.addAll(bookList);
		}
	}

	private String compileUrl(String categoryUrl, int pageNo, String postfix) {
		String path = categoryUrl
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
	public List<String> getCategoryUrls(Boolean isRapid) throws Exception {
		List<String> result = new ArrayList<>();
		if (isRapid != null && isRapid == true) {
			return bookDao.getCategories();
		}
		String url = CATEGORY_LEVEL_3_URL;
		String htmlContent = HttpClientUtils.getHtmlContent(url);
		List<String> categoryLevel3 = HttpDocumentUtils.getBookCategoryLevel3Urls(htmlContent);
		for (int i = 0; i < categoryLevel3.size(); i++) {
			url = HOST + categoryLevel3.get(i);

			//取出第4级分类
			htmlContent = HttpClientUtils.getHtmlContent(url);
			List<String> categoryLevel4 = HttpDocumentUtils.getBookCategoryLevel4Urls(htmlContent);

			if (CollectionUtils.isNotEmpty(categoryLevel4)) {
				result.addAll(categoryLevel4);
			} else {
				//如果当前分类下面没有子分类,直接去父分类url
				result.add(categoryLevel3.get(i));
			}
		}
		if (result.size() == 0) {
			result = bookDao.getCategories();
		}
		return result;
	}

	public List<Book> selectAll() {
		return bookDao.selectAll();
	}

	public int selectAllCount() {
		return bookDao.selectAllCount();
	}

	public int deleteAll() {
		return bookDao.deleteAll();
	}

	public int insertOne() {
		Book book = getExampleBook();
		return bookDao.insertOne(book);
	}

	private Book getExampleBook() {
		Book book = new Book();
		book.setId("P99900");
		book.setTitle("计算机网络基础");
		book.setUrl("http://product.dangdang.com/23219375.html");
		book.setComments("11条评论");
		String valueOfComments = book.getComments().replace("条评论", "");
		book.setPopularity(Integer.valueOf(valueOfComments));
		book.setPrice(9.0);
		return book;
	}

	public List<Book> selectByName(String name) {
		return bookDao.selectByName(name);
	}

	public String init() throws IOException {
		HttpClientUtils.ipPool.addAll(getIpPool());
		String result = "ips:" + String.valueOf(HttpClientUtils.ipPool.size());
		result += "\norigin:" + selectAllCount();
		insertOne();
		result += "\ninserted:" + selectAllCount();
		deleteAll();
		result += "\ndeletedall:" + selectAllCount();
		return result;
	}

	private List<IP> getIpPool() throws IOException {
		List<IP> result = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			String url = PROXY_URL + i;
			String htmlContent = HttpClientUtils.getHtmlContent(url);
			if (StringUtils.isNotBlank(htmlContent)) {
				List<IP> ips = HttpDocumentUtils.getIps(htmlContent);
				if (ips != null) {
					result.addAll(ips);
				}
			}
		}
		if (HttpClientUtils.ipPool.size() == 0) {
			result = bookDao.selectIps();
		}
		return result;
	}


}
