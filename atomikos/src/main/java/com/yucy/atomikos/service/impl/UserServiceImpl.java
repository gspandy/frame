package com.yucy.atomikos.service.impl;

import javax.annotation.Resource;

import com.yucy.atomikos.dao.GenericDao;
import com.yucy.atomikos.service.UserService;

public class UserServiceImpl implements UserService {

	@Resource
	private GenericDao genericDao;
	
	@Override
	public void saveUser() throws Exception {
		String username = "atomikos_user_"+Math.round(Math.random()*10000);
		System.out.println(username);
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into t_user(username,gender) values (? ,?);");
		Object[] objs = new Object[]{username,"1"};
		
		genericDao.save("A", sql.toString(), objs);
		
		sql.delete(0, sql.length());
		sql.append(" insert into t_user(name,sex) values(?,?);");
		objs = new Object[]{username,"0"}; // 值超出范围
		genericDao.save("B", sql.toString(), objs);
		
	}

}
