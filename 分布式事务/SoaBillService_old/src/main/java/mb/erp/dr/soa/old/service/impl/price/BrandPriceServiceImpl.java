package mb.erp.dr.soa.old.service.impl.price;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.ProdClsMapper;
import mb.erp.dr.soa.old.service.price.BrandPriceService;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service("brandPriceService")
public class BrandPriceServiceImpl implements BrandPriceService{
	private final Logger logger = LoggerFactory.getLogger(BrandPriceServiceImpl.class);
	@Resource
	private ProdClsMapper prodClsMapper;
	@Value("${select.brandPrice.error}")
	private String selectBrandPriceError;
	
	/**
	 * 获取商品的吊牌价格
	 * @return
	 * @
	 */
	public MsgVo getProductOnBrandPrice(String ProdNum)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		Map<String, String> map =  new HashMap<String,String>();
		map.put("prod_id", ProdNum);
		Double onBrandPrc = prodClsMapper.selectOnBrandPrice(map);
		if (onBrandPrc == 0) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(selectBrandPriceError, ProdNum));
			throw new RuntimeException(msg.getMsg());
		}
		msg.setPrice(onBrandPrc);
		msg.setDiscRate(100.0);
		logger.warn("获取商品吊牌价格，商品："+ProdNum + "价格：" + msg.getPrice() + "折率：" + msg.getDiscRate());
		return msg;
	}
}
