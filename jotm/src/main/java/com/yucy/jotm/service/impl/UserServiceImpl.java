package com.yucy.jotm.service.impl;

import javax.annotation.Resource;

import com.yucy.jotm.dao.GenericDao;
import com.yucy.jotm.service.UserService;

public class UserServiceImpl implements UserService {

	@Resource
	private GenericDao genericDao;
	
	@Override
	public void saveUser() throws Exception {
		String username = "user_"+Math.round(Math.random()*10000);
		System.out.println(username);
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_user(username,gender) values (? ,?);");
		Object[] objs = new Object[]{username,"1"};
		
		genericDao.save("A", sql.toString(), objs);
		
		sql.delete(0, sql.length());
		sql.append(" insert into t_user(name,sex) values(?,?);");
		objs = new Object[]{username,"1"}; // 值超出范围
		genericDao.save("B", sql.toString(), objs);
		
	}

}
