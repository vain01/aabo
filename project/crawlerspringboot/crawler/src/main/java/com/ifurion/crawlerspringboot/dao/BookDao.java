package com.ifurion.crawlerspringboot.dao;

import com.ifurion.crawlerspringboot.entity.Book;

import java.util.List;

/**
 * @author haoliang on 2018/10/10.
 */
public interface BookDao {
	int insert(Book book);

	List<Book> selectAll();
}
