package com.ifurion.demo.service;

import com.ifurion.demo.mapper.DemoMapper;
import com.ifurion.demo.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haoliang on 2018/10/11.
 */
@Component
public class DemoService {
	@Autowired
	private DemoMapper dao;

	public boolean insert(DemoModel model) {
		return dao.insert(model) > 0;
	}

	public DemoModel select(int id) {
		return dao.select(id);
	}

	public List<DemoModel> selectAll() {
		return dao.selectAll();
	}

	public boolean updateValue(DemoModel model) {
		return dao.updateValue(model) > 0;
	}

	public boolean delete(Integer id) {
		return dao.delete(id) > 0;
	}
}
