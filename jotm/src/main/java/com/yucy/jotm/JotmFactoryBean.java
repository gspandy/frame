package com.yucy.jotm;

import javax.naming.NamingException;

import org.objectweb.jotm.Current;
import org.objectweb.jotm.Jotm;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * 类描述： 
 * @author:余从玉
 * @version   1.0
 * @since 2015年8月17日           
 */
public class JotmFactoryBean implements FactoryBean<Current>, DisposableBean {

	private Current jotmCurrent;
	private Jotm jotm;
	
	public JotmFactoryBean() throws NamingException{
		this.jotmCurrent = Current.getCurrent();
		
		// 
		if (this.jotmCurrent == null) {
			this.jotm = new Jotm(true, false);
			this.jotmCurrent = Current.getCurrent();
		}
	}
	
	public Jotm getJotm(){
		return this.jotm;
	}
	
	@Override
	public Current getObject() throws Exception {
		return this.jotmCurrent;
	}

	@Override
	public Class<?> getObjectType() {
		return this.jotmCurrent.getClass();
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}
	
	
	/**
	 * 功能描述：停止jotm的本地实例，如果它是被JotmFactory创建的
	 * @throws Exception
	 */
	@Override
	public void destroy() throws Exception {
		if (this.jotm != null) {
			this.jotm.stop();
		}
	}


}
