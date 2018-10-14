package com.ifurion.crawlerspringboot.entity;

import lombok.Data;

/**
 * @author haoliang on 2018/10/9.
 */
@Data
public class Book {
	private String id;
	private String title;
	private String url;
	private String comments;
	private Integer popularity;
	private Double price;
}
