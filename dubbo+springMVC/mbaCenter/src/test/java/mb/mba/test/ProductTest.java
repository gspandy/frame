package mb.mba.test;

import javax.annotation.Resource;

import mb.mba.service.ICommonService;
import mb.mba.vo.Product;

import org.junit.Test;

/**
 * 产品表方法测试
 * @author cyyu
 */
public class ProductTest extends MbaTestBase {
	
	@Resource
	ICommonService commonService;
	
	@Test
	public void testProc(){
		Product p = commonService.getProductById(2);
		System.out.println(p.getName());
	}
}
