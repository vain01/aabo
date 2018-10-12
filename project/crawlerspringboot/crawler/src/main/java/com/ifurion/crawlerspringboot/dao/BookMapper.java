package com.ifurion.crawlerspringboot.dao;

import com.ifurion.crawlerspringboot.entity.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haoliang on 2018/10/11.
 */
@Mapper
@Component
public interface BookMapper {
	// 插入 并查询id 赋给传入的对象
	@Insert("INSERT INTO book(title,comments) VALUES(#{title}, #{comments})")
	@SelectKey(statement = "SELECT seq id FROM sqlite_sequence WHERE (name = 'book')", before = false, keyProperty = "id", resultType = int.class)
	int insert(Book model);

	// 查询全部
	@Select("SELECT id,title,comments FROM book")
	List<Book> selectAll();

}
