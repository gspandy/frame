package com.yucy.jotm;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucy.jotm.service.UserService;


/**
 * 测试jotm分布式事务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-jotm.xml"})
public class AppTest {
	
	@Resource
	private UserService userService;
	
	@Test
	public void test(){
		System.out.println("==============start...");
		try {
			userService.saveUser();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.out.println("==============finish...");
		}
	}
	
	@After
	public void distory(){
		System.gc();
		System.exit(0);
	}
}
