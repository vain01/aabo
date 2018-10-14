package com.ifurion.crawlerspringboot.util;

import com.ifurion.crawlerspringboot.entity.Book;
import com.ifurion.crawlerspringboot.entity.IP;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haoliang on 2018/10/9.
 */
public class HttpDocumentUtils {
	public static List<String> getBookCategoryLevel3Urls(String htmlContent) {
		String selectParent = "div[class=crumbs_fb_left]";
		String selectSelf = "div[class=select_frame]";
		String selectChild = "a[dd_name=面包屑3级]";
		return getBookCategoryUrls(htmlContent, selectParent, selectSelf, selectChild);
	}

	public static List<String> getBookCategoryLevel4Urls(String htmlContent) {
		String selectParent = "li[dd_name=分类]";
		String selectSelf = "div[class=clearfix]";
		String selectChild = "span";
		List<String> result = getBookCategoryUrls(htmlContent, selectParent, selectSelf, selectChild);
		return result;
	}

	public static List<Book> getBookList(String htmlContent) {
		//获取的数据，存放在集合中
		List<Book> result = new ArrayList<>();
		if (StringUtils.isBlank(htmlContent)) {
			return result;
		}
		//采用Jsoup解析
		Document doc = Jsoup.parse(htmlContent);
		//获取html标签中的内容
		Elements elements = doc
			.select("div[class=con shoplist]")
			.select("div[dd_name=普通商品区域]")
			.select("li");

		for (Element element : elements) {
			Book book = getBook(element);
			result.add(book);
		}

		//返回数据
		return result;
	}

	private static Book getBook(Element element) {
		Book book = new Book();
		book.setId(element.attr("id"));
		book.setTitle(element.select("a[class=pic]").attr("title"));
		book.setUrl(element.select("a[class=pic]").attr("href"));
		book.setComments(element.select("a[dd_name=单品评论]").text());
		String priceText = element.select("p[class=price]").select("span[class=search_now_price]").text();
		try {
			book.setPrice(Double.valueOf(priceText.substring(priceText.indexOf("¥") + 1)));
		} catch (Exception e) {
			book.setPrice(0.0);
		}
		return book;
	}

	public static List<IP> getIps(String htmlContent) {
		//获取的数据，存放在集合中
		List<IP> result = new ArrayList<>();
		if (StringUtils.isBlank(htmlContent)) {
			return result;
		}
		//采用Jsoup解析
		Document doc = Jsoup.parse(htmlContent);
		//获取html标签中的内容
		Elements elements = doc
			.select("table[id=ip_list]")
			.select("tbody")
			.select("tr");

		for (Element element : elements) {
			IP ip = new IP();
			ip.setIp(element.select("td").get(1).text());
			ip.setPort(element.select("td").get(2).text());
			result.add(ip);
		}

		//返回数据
		return result;
	}

	public static List<String> getBookCategoryUrls(String htmlContent, String parent, String self, String child) {
		//获取的数据，存放在集合中
		List<String> result = new ArrayList<>();
		if (StringUtils.isBlank(htmlContent)) {
			return result;
		}

		//采用Jsoup解析
		Document doc = Jsoup.parse(htmlContent);

		Elements elements = doc
			.select(parent)
			.select(self)
			.select(child);

		for (Element element : elements) {
			String href = element.select("a").attr("href");
			result.add(href);
		}

		//返回数据
		return result;
	}
}
