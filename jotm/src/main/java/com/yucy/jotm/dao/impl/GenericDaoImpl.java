package com.yucy.jotm.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yucy.jotm.dao.GenericDao;

public class GenericDaoImpl implements GenericDao {
	
	@Resource
	private JdbcTemplate jdbcTemplateA;
	
	@Resource
	private JdbcTemplate jdbcTemplateB;

	@Override
	public int save(String ds, String sql, Object[] obj) throws Exception{
		if (null == ds || "".equals(ds)) {
			return -1;
		}
		try {
			if (ds.equals("A")) {
				return jdbcTemplateA.update(sql, obj);
			}else {
				return this.jdbcTemplateB.update(sql, obj);
			}
		} catch (Exception  e) {
			e.printStackTrace();
			throw new Exception("执行"+ds+"数据库时失败");
		}
	}

	@Override
	public int findRowCount(String ds, String sql) {
		if (null == ds || "".equals(ds)) {
			return -1;
		}
		if (ds.equals("A")) {
			return this.jdbcTemplateA.queryForInt(sql);
		}else {
			return this.jdbcTemplateB.queryForInt(sql);
		}
	}

}
