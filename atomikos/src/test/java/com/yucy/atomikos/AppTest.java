package com.yucy.atomikos;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucy.atomikos.service.UserService;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-atomikos.xml"})
public class AppTest{
	
	@Resource
	private UserService userService;
	
	@Test
	public void test_atomikos(){
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
