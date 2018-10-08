package com.aabo.aspect.controller;

import com.aabo.aspect.aop.Log;
import com.aabo.aspect.bean.User;
import com.aabo.aspect.service.AspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoliang on 2018/10/8.
 */
@RestController
@RequestMapping(value = "/testAspect")
public class Controller {
	@Autowired
	AspectService aspectService;

	@GetMapping(value = "getUser")
	public User getUser() {
		User user = new User();
		user.setAge(14);
		user.setName("aabo");

		return aspectService.getUser(user);
	}
}
