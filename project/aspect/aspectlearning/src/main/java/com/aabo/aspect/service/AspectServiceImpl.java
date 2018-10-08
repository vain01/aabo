package com.aabo.aspect.service;

import com.aabo.aspect.aop.Log;
import com.aabo.aspect.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author haoliang on 2018/10/8.
 */
@Service
public class AspectServiceImpl implements AspectService {
	@Override
	@Log(ignore = true)
	public User getUser(User user) {
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setAge(888);
		return newUser;
	}
}
