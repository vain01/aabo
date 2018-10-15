package com.ifurion.demo.controller;

import com.ifurion.demo.model.DemoModel;
import com.ifurion.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author haoliang on 2018/10/11.
 */
@Controller
public class DemoController {
	@Autowired
	private DemoService service;

	/**
	 * JSP 测试
	 * @return JSP 视图
	 */
	@GetMapping("test")
	public ModelAndView test() {
		// 对应 demo.jsp 路径
		ModelAndView mv = new ModelAndView("demo");
		mv.addObject("value", "测试值");
		return mv;
	}

	/**
	 * 接口测试
	 * @return JSON 字符串
	 */
	@GetMapping("demos")
	@ResponseBody
	public List<DemoModel> allDemo() {
		DemoModel model = new DemoModel();
		model.setKey("测试key1");
		model.setValue("测试value1");

		// insert
		boolean result = service.insert(model);

		return service.selectAll();
	}
	// @ResponseBody 如果返回的是对象 会自动转为json字符串，如果返回的是String 则返回该字符串
}
