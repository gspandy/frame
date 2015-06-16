package mb.erp.dr.soa.old.service.impl.price;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.PrcLstDtlMapper;
import mb.erp.dr.soa.old.service.price.PurchasingPriceRateService;
import mb.erp.dr.soa.old.vo.PrcLstDtlVo;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 采购价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取采购价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         PurchasingPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("purchasingPriceRateService")
public class PurchasingPriceRateServiceImpl implements PurchasingPriceRateService{
	@Resource
	private PrcLstDtlMapper prcLstDtlMapper;
	@Value("${prodId.param.null}")
	private String prodIdParamNull;
	@Value("${unitId.param.null}")
	private String unitIdParamNull;
	@Value("${currency.param.null}")
	private String currencyParamNull;
	@Value("${get.purchasing.price.null}")
	private String getPurchasingPriceNull;
	
	
	public MsgVo getPurchasingPriceRateByProdId(String prod_id, String unit_id,
			String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (SoaBillUtils.isBlank(prod_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdParamNull);
			throw new RuntimeException(msg.getMsg());
		}else if(SoaBillUtils.isBlank(unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
			throw new RuntimeException(msg.getMsg());
		}else if (SoaBillUtils.isBlank(currency)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(currencyParamNull);
			throw new RuntimeException(msg.getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
        Map<String, String> map =  new HashMap<String,String>();
        map.put("prod_id", prod_id);
        map.put("unit_id", unit_id);
        map.put("currency", currency);
        map.put("ag_unit_id", "VDPS");
        map.put("color", prod_id.substring(6, 8));
        
        //根据组织ID 和PROD_ID来获取采购价格信息
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.selectByUnitIDProdID(map);
        if (prcLstDtlVos.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getPurchasingPriceNull, prod_id,unit_id));
			throw new RuntimeException(msg.getMsg());
		}
        msg.setPrice(prcLstDtlVos.get(0).getPrice());
        if (prcLstDtlVos.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prcLstDtlVos.get(0).getDiscRate());
		}
		return msg;
	}


	public MsgVo getPurchasePriceByProdId(int prod_id, int vendeeId,
			int venderId, String currency)  {
		// TODO Auto-generated method stub
		return null;
	}

}
