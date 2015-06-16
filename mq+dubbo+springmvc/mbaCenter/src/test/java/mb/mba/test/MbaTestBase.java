package mb.mba.test;

import java.io.IOException;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 单元测试基类
 * @author cyyu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class MbaTestBase {

	@After
	public void distory() throws IOException{
		System.gc();
		System.exit(0);
//		System.in.read();
	}
}
