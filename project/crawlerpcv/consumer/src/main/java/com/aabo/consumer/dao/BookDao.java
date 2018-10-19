package com.aabo.consumer.dao;

import com.aabo.consumer.entity.Book;
import com.aabo.consumer.entity.IP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haoliang on 2018/10/10.
 */
public interface BookDao {
	int insertBook(Book book);

	List<Book> selectAllBooks();

	int deleteAllBooks();

	List<Book> selectBookByName(@Param("name") String name);

	int selectAllBookCount();

	List<IP> selectAllProxyIps();

	List<String> getAllCategories();

	void insertBookBatch(List<Book> bookList);

	void insertCategoryBatch(List<String> categoryUrls);

	void deleteAllCategories();
}
