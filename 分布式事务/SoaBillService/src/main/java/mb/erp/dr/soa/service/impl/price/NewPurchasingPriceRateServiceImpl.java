package mb.erp.dr.soa.service.impl.price;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.dao.PoPrcListDtlMapper;
import mb.erp.dr.soa.dao.PrPrcListDtlMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.old.service.price.PurchasingPriceRateService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewPurchasingPriceRateService;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.PoPrcListDtlVo;
import mb.erp.dr.soa.vo.PrPrcListDtlVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 采购价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取采购价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         NewPurchasingPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("newPurchasingPriceRateService")
public class NewPurchasingPriceRateServiceImpl implements NewPurchasingPriceRateService{
	@Resource
	private PrPrcListDtlMapper prPrcListDtlMapper;
	@Resource
	private NewERPCommonMapper newERPCommonMapper;
	@Resource
	private PoPrcListDtlMapper poPrcListDtlMapper;
	@Resource
	private PurchasingPriceRateService purchasingPriceRateService;
	@Resource
	private NewERPCommonService newERPCommonService;
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
        map.put("prod_num", prod_id);
        map.put("unit_id", unit_id);
        map.put("currency", currency);
        map.put("ag_unit_id", "VDPS");
        
        //根据组织ID 和PROD_ID来获取采购价格信息
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.selectByProdIdUnitID(map);
        if (prPrcListDtls.size() == 0) {
        	String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getPurchasingPriceNull, prod_id,unit_id));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有启用新ERP，则还需从老ERP取
        	return purchasingPriceRateService.getPurchasingPriceRateByProdId(prod_id, unit_id, currency);
		}
        msg.setPrice(prPrcListDtls.get(0).getPrice());
        if (prPrcListDtls.get(0).getDiscRate() ==0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prPrcListDtls.get(0).getDiscRate());
		}
		return msg;
	}
	
	/**
	 * 根据传入的商品11位码的ID来查询供应商往来价格(采购价格)，折率默认为100
	 * @param prod_id
	 * @param vendeeId
	 * @param venderId
	 * @param currency
	 * @return
	 * @
	 */
	public MsgVo getPurchasePriceByProdId(int prod_id, int vendeeId,
			int venderId,String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg.setDiscRate(100d);
		currency = PriceCheckUnit.getCurreny(currency);
        Map<String ,Object> map =  new HashMap<String ,Object>();
        map.put("prod_id", prod_id);
        map.put("vender_id", venderId);
        map.put("currency", currency);
        map.put("vendee_id", vendeeId);
        map.put("prc_lst_type", "VDPS");
        //根据组织ID 和PROD_ID来获取采购价格信息
        List<PoPrcListDtlVo> poPrcListDtls = poPrcListDtlMapper.priceUdfacePoPrcLstDtlGetByID(map);
        if (poPrcListDtls == null || poPrcListDtls.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
    		msg.setMsg(MessageFormat.format(getPurchasingPriceNull, prod_id,vendeeId));
    		throw new RuntimeException(msg.getMsg());
		}
        msg.setPrice(poPrcListDtls.get(0).getPrice());
		return msg;
	}

}
