/*
 * 文件名：MybatisTest.java 版权：Copyright 2014 MetersBonwe. All Rights Reserved. 描述：TODO 修改人：Weijf 修改时间：下午1:58:52 修改内容：
 */

package com.mtsbw.example;

import mb.erp.dr.soa.vo.common.MsgVo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit测试，对象消息
 * 
 * @author Weijf
 * @version
 * @see
 * @since
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mybatis.xml")
public class MybatisTest111 {
    @Test
    public void testInsertAndSendObj() {
        try {
        	MsgVo msg = new MsgVo();
        	
    		   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
