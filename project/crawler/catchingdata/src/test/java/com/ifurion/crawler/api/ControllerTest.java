package com.ifurion.crawler.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * @author haoliang on 2018/10/9.
 */
@Slf4j
public class ControllerTest {

	@Test
	public void testGetPageContent() throws Exception {
		Controller controller = new Controller();
		log.info(String.valueOf(controller.getBookList().size()));
	}

	// @Test
	public void testGetCategories() throws Exception {
		Controller controller = new Controller();
		log.info(JSON.toJSONString(controller.getCategoryUrls()));
	}
}