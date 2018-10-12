package com.ifurion.crawlerspringboot.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author haoliang on 2018/10/12.
 */
@Configuration
@Configurable
public class ThreadPoolConfig {
	@Bean
	public ThreadPoolTaskExecutor getT(){
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		// 		<!-- 核心线程数  -->
		threadPoolTaskExecutor.setCorePoolSize(100);
		// <!-- 最大线程数 -->
		threadPoolTaskExecutor.setMaxPoolSize(5000);
		// <!-- 队列最大长度 >=mainExecutor.maxSize -->
		threadPoolTaskExecutor.setQueueCapacity(5000);
		// <!-- 线程池维护线程所允许的空闲时间 -->
		threadPoolTaskExecutor.setKeepAliveSeconds(300);
		// <!-- 线程池对拒绝任务(无线程可用)的处理策略 -->
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return threadPoolTaskExecutor;

		// <property name="corePoolSize" value="50" />
		// <!-- 最大线程数 -->
		// <property name="maxPoolSize" value="5000" />
		// <!-- 队列最大长度 >=mainExecutor.maxSize -->
		// <property name="queueCapacity" value="5000" />
		// <!-- 线程池维护线程所允许的空闲时间 -->
		// <property name="keepAliveSeconds" value="300" />
		// <property name="rejectedExecutionHandler">
		// 	<bean class="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy" />
		// </property>
	}
}
