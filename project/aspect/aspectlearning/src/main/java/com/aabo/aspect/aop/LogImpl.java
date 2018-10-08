package com.aabo.aspect.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author haoliang on 2018/10/8.
 */
@Aspect
@Order(100)
@Component
@Slf4j
public class LogImpl {
	private final String START_STRING = "<<<<<<";
	private final String END_STRING = ">>>>>>";

	@Pointcut("execution( * com.aabo.aspect..*(..))")
	public void serviceLog() {

	}

	@Around("serviceLog()")
	public Object handleLog(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			//获取注解信息
			MethodSignature targetMethodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
			Method targetMethod = targetMethodSignature.getMethod();
			Class<?> targetClass = targetMethod.getDeclaringClass();

			Log classAnnotation = targetClass.getAnnotation(Log.class);
			Log methodAnnotation = targetMethod.getAnnotation(Log.class);

			//注解处理
			if (classAnnotation != null && classAnnotation.ignore()
				|| methodAnnotation != null && methodAnnotation.ignore()) {
				return proceedingJoinPoint.proceed();
			}
			String targetClassAndMethodName = targetClass.getName() + "#" + targetMethod.getName();
			String targetMethodParam = JSON.toJSONString(proceedingJoinPoint.getArgs());

			log.info(START_STRING);
			log.info("开始调用方法:{} 参数:{}", targetClassAndMethodName, targetMethodParam);

			Object result = proceedingJoinPoint.proceed();

			log.info("结束调用方法:{} 返回值:{}", targetClassAndMethodName, JSON.toJSONString(result));
			log.info(END_STRING);

			return result;
		} catch (Throwable throwable) {
			log.debug(throwable.getMessage());
			return null;
		}
	}
}
