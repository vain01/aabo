package com.aabo.aspect.bean;

import lombok.Data;

/**
 * @author haoliang on 2018/10/8.
 */
@Data
public class User {
	private String name;
	private Integer age;

	@Override
	public String toString() {
		return "name:" + name + ",age:" + age;
	}
}
