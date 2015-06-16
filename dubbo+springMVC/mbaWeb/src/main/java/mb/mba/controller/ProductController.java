package mb.mba.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.mba.service.ICommonService;
import mb.mba.utils.RedisUtil;
import mb.mba.vo.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/proc")
public class ProductController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Resource
	private ICommonService commonService;
	@Value("${redis.key.pre}")
	private String redisKeyPre;
	
	@RequestMapping(value="/show/{id}",method=RequestMethod.GET)
	public ModelAndView showProduct(@PathVariable Integer id){
		logger.info(id+"");
		Product product = RedisUtil.getVo(redisKeyPre+"PRODUCT", id.toString());
		if (product == null) {
			product = commonService.getProductById(id);
			RedisUtil.setVo(redisKeyPre+"PRODUCT",  id.toString(), product);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("product", product);
		return render(data, "product");
	}
}
