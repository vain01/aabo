package com.ifurion.crawlerspringboot.dao;

import com.ifurion.crawlerspringboot.entity.Book;
import com.ifurion.crawlerspringboot.entity.IP;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author haoliang on 2018/10/10.
 */
public interface BookDao {
	int insertOne(Book book);


	List<Book> selectAll();

	int deleteAll();

	List<Book> selectByName(@Param("name") String name);

	int selectAllCount();

	List<IP> selectIps();

	List<String> getCategories();

	void insertBatch(List<Book> bookList);
}
