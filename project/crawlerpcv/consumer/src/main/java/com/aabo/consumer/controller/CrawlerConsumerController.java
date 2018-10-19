package com.aabo.consumer.controller;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoliang on 2018/10/19.
 */
@RestController
@RequestMapping(value = "consumer")
public class CrawlerConsumerController {
	@Value("${connection.remoteAddress}")
	private String HOST;

	@RequestMapping(value = "handleMessage", method = RequestMethod.GET)
	public void handleMessage() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(HOST);

	}
}
