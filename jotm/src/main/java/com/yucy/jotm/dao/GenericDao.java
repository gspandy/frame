package com.yucy.jotm.dao;

public interface GenericDao {

	public int save(String ds ,String sql ,Object[] obj)  throws Exception;
	
	public int findRowCount(String ds ,String sql);
}
