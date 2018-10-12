package com.ifurion.crawlerspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ifurion.crawlerspringboot.dao")
public class CrawlerspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerspringbootApplication.class, args);
	}
}
