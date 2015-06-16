package mb.erp.dr.soa.old.service.impl.price;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.PrcLstDtlMapper;
import mb.erp.dr.soa.old.dao.SysUnitMapper;
import mb.erp.dr.soa.old.dao.TmpInvalidDefMapper;
import mb.erp.dr.soa.old.service.price.RetailPriceRateService;
import mb.erp.dr.soa.old.vo.PrcLstDtlVo;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 零售价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取零售价格折率和根据批量商品编码获取零售价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         RetailPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("retailPriceRateService")
public class RetailPriceRateServiceImpl implements RetailPriceRateService{
	private final Logger logger = LoggerFactory.getLogger(RetailPriceRateServiceImpl.class);
	@Resource
	private SysUnitMapper sysUnitMapper;
	@Resource
	private PrcLstDtlMapper prcLstDtlMapper;
	@Resource
	private TmpInvalidDefMapper tmpInvalidMapper;
	@Value("${prodId.param.null}")
	private String prodIdParamNull;
	@Value("${prodIdList.param.null}")
	private String prodIdListParamNull;
	@Value("${unitId.param.null}")
	private String unitIdParamNull;
	@Value("${currency.param.null}")
	private String currencyParamNull;
	@Value("${get.retail.price.null}")
	private String getRetailPriceNull;
	@Value("${sixProdId.param.error}")
	private String sixProdIdParamError;
	@Value("${eightProdId.param.error}")
	private String eightProdIdParamError;
	@Value("${elevenProdId.param.error}")
	private String elevenProdIdParamError;
	@Value("${prodId.price.null}")
	private String prodIdPriceNull;
	

	public MsgVo getRetailPriceRateByProdClsNum(String prod_cls_num,
			String unit_code, String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_cls_num,unit_code,currency);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		if (prod_cls_num.length() != 6) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(sixProdIdParamError);
			throw new RuntimeException(msg.getMsg());
		}
		String prod_prop = prod_cls_num.substring(0,1);
        String prod_sort = prod_cls_num.substring(1, 3);
        String prod_style = prod_cls_num.substring(3, 6);
        Map<String, String> map =  new HashMap<String,String>();
        map.put("prod_prop", prod_prop);
        map.put("prod_sort", prod_sort);
        map.put("prod_style", prod_style);
        map.put("prod_id", prod_cls_num);
        map.put("wunit_id", unit_code);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
        
        //获取门店的上级组织
        String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        map.put("ag_unit_id", agUnitID);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        //根据商品款编码递归获取零售价格
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.selectByProdClsNum(map);
        if (prcLstDtlVos.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getRetailPriceNull, agUnitID,currency,prod_cls_num));
			return msg;
		}
        msg.setPrice(prcLstDtlVos.get(0).getPrice());
        if ("P".equals(prcLstDtlVos.get(0).getPrcPlcy()) || prcLstDtlVos.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prcLstDtlVos.get(0).getDiscRate());
		}
		return msg;
	}

	public MsgVo getRetailPriceRateByProdId(String prod_id, String unit_id,
			String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_id,unit_id,currency);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		Map<String, String> map =  new HashMap<String,String>();
	    map.put("wunit_id", unit_id);
		//获取门店的上级组织
        String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_id;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        map.put("ag_unit_id", agUnitID);
        map.put("prod_id", prod_id);
        map.put("unit_id", unit_id);
        map.put("currency", currency);
        map.put("color", prod_id.substring(6, 8));
        logger.warn("获取零售价格参数："+"商品："+prod_id+" 组织："+unit_id+" ag_unit_id："+agUnitID+" color："+prod_id.substring(6, 8));
        //根据组织ID 和PROD_ID来获取零售价格信息
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.selectByUnitIDProdID(map);
        if (prcLstDtlVos.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getRetailPriceNull, agUnitID,currency,prod_id));
			throw new RuntimeException(msg.getMsg());
		}
        msg.setPrice(prcLstDtlVos.get(0).getPrice());
        if ("P".equals(prcLstDtlVos.get(0).getPrcPlcy()) || prcLstDtlVos.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prcLstDtlVos.get(0).getDiscRate());
		}
        logger.warn("获取零售价格："+msg.getPrice()+"折率："+msg.getDiscRate()+"商品："+prod_id+"组织："+unit_id);
		return msg;
	}

	public MsgVo getRetailPriceRateByProdColorNum(String prod_color_num,
			String unit_code, String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_color_num,unit_code,currency);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		if (prod_color_num.length() != 8) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(eightProdIdParamError);
			throw new RuntimeException(msg.getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		String prod_prop = prod_color_num.substring(0,1);
        String prod_sort = prod_color_num.substring(1, 3);
        String prod_style = prod_color_num.substring(3, 6);
        String prod_color = prod_color_num.substring(6, 8);
	    map.put("wunit_id", unit_code);
		//获取门店的上级组织
        String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        map.put("prod_prop", prod_prop);
        map.put("prod_sort", prod_sort);
        map.put("prod_style", prod_style);
        map.put("ag_unit_id", agUnitID);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
        map.put("color", prod_color);
        //根据组织ID 和PROD_ID来获取零售价格信息
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.selectByProdColorNum(map);
        if (prcLstDtlVos.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getRetailPriceNull, agUnitID,currency,prod_color_num));
			return msg;
		}
        msg.setPrice(prcLstDtlVos.get(0).getPrice());
        if ("P".equals(prcLstDtlVos.get(0).getPrcPlcy()) || prcLstDtlVos.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prcLstDtlVos.get(0).getDiscRate());
		}
		return msg;
	}

   /**
    * 批量获取零售价格(商品款)
    */
	public List<MsgListVo> bulkGetRetailPriceRateByProdClsNum(
			List<String> prod_cls_numlist, String unit_code, String currency)
			 {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prod_cls_numlist, unit_code, currency);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		Map<String, String> map =  new HashMap<String,String>();
		map.put("wunit_id", unit_code);
		//先查询组织是否是门店或者仓库
		String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        for (String pcn : prod_cls_numlist) {
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setProdId(pcn);
        	msg.setWunitId(unit_code);
        	if (pcn.length() != 6) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg(sixProdIdParamError);
			}
        	msgList.add(msg);
		}
        //将商品编码存入临时表
        tmpInvalidMapper.saveTempProductNum(prod_cls_numlist);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.bulkGetRetailPriceRateByProdClsNum(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrcLstDtlVo prcLstDtl : prcLstDtlVos){
					if (msgl.getProdId().equals(prcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prcLstDtl.getPrice());
				        if ("P".equals(prcLstDtl.getPrcPlcy()) || prcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prcLstDtl.getDiscRate());
						}
					}
				}
				if (!flg) {
					msgl.setMsg(MessageFormat.format(prodIdPriceNull, msgl.getProdId()));
				}
			}
        }
		return msgList;
	}

	/**
	 * 批量获取零售价格(商品编码)
	 */
	public List<MsgListVo> bulkGetRetailPriceRateByProdId(
			List<String> prod_numlist, String unit_code, String currency)
			 {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prod_numlist, unit_code, currency);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		Map<String, String> map =  new HashMap<String,String>();
		map.put("wunit_id", unit_code);
		//先查询组织是否是门店或者仓库
		String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        for (String pcn : prod_numlist) {
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setProdId(pcn);
        	msg.setWunitId(unit_code);
        	if (pcn.length() != 11) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg(elevenProdIdParamError);
			}
        	msgList.add(msg);
		}
        //将商品编码存入临时表
        tmpInvalidMapper.saveTempProductNum(prod_numlist);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.bulkGetRetailPriceUnitIDProdIDOld(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrcLstDtlVo prcLstDtl : prcLstDtlVos){
					if (msgl.getProdId().equals(prcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prcLstDtl.getPrice());
				        if ("P".equals(prcLstDtl.getPrcPlcy()) || prcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prcLstDtl.getDiscRate());
						}
					}
				}
				if (!flg) {
					msgl.setCode(O2OMsgConstant.MSG_CODE.ERROR);
					msgl.setMsg(MessageFormat.format(prodIdPriceNull, msgl.getProdId()));
					msgl.setPrice(0.0);
					msgl.setDiscRate(0.0);
				}
			}
        }
		return msgList;
	}
	
	/**
	 * 批量获取零售价格(商品颜色)
	 */
	public List<MsgListVo> bulkGetRetailPriceRateByProdColorNum(
			List<String> prod_color_numlist, String unit_code, String currency)
			 {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prod_color_numlist, unit_code, currency);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		Map<String, String> map =  new HashMap<String,String>();
	    map.put("wunit_id", unit_code);
		//获取门店的上级组织
        String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        map.put("ag_unit_id", agUnitID);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
		for (String prod_color_num : prod_color_numlist) {
			map.put("prod_id", prod_color_num);
			if (prod_color_num.length() != 8) {
				MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.ERROR,"","");
				msg.setWunitId(unit_code);
				msg.setProdId(prod_color_num);
				msg.setMsg(eightProdIdParamError);
				msgList.add(msg);
				return msgList;
			}
	       
	        //根据组织ID 和PROD_ID来获取零售价格信息
	        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.selectByProdColorNum2(map);
	        if (prcLstDtlVos.size() == 0) {
	        	MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.ERROR,"","");
	        	msg.setWunitId(unit_code);
				msg.setProdId(prod_color_num);
				msg.setMsg(MessageFormat.format(getRetailPriceNull, agUnitID,currency,prod_color_num));
				msgList.add(msg);
				return msgList;
			}
	        for(PrcLstDtlVo prcLstDtl : prcLstDtlVos){
	        	MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
	        	msg.setWunitId(unit_code);
				msg.setProdId(prcLstDtl.getProdId());
				msg.setPrice(prcLstDtl.getPrice());
				if ("P".equals(prcLstDtl.getPrcPlcy()) || prcLstDtl.getDiscRate() == 0) {
					msg.setDiscRate(100.0);
				}else {
					msg.setDiscRate(prcLstDtlVos.get(0).getDiscRate());
				}
				msgList.add(msg);
	        }
		}
		return msgList;
	}
	
	public List<MsgListVo> bulkGetRetailPriceRateByIntnlBc(
			List<String> inTnlBcList, String unit_code, String currency)
			 {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(inTnlBcList, unit_code, currency);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		currency = PriceCheckUnit.getCurreny(currency);
		Map<String, String> map =  new HashMap<String,String>();
	    map.put("wunit_id", unit_code);
		//获取门店的上级组织
        String owner_id = sysUnitMapper.selectOwnerID(map);
        String agUnitID = "";
        if (SoaBillUtils.isBlank(owner_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = owner_id;
		}
        for(String inTnlBc : inTnlBcList){
        	MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setWunitId(unit_code);
        	msg.setProdId(inTnlBc);
        	msgList.add(msg);
        }
        //将商品编码存入临时表
        tmpInvalidMapper.saveTempProductNum(inTnlBcList);
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrcLstDtlVo> prcLstDtlVos = prcLstDtlMapper.bulkGetRetailPriceRateByIntnlBc(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrcLstDtlVo prcLstDtl : prcLstDtlVos){
					if (msgl.getProdId().equals(prcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prcLstDtl.getPrice());
				        if ("P".equals(prcLstDtl.getPrcPlcy()) || prcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prcLstDtl.getDiscRate());
						}
					}
				}
				if (!flg) {
					msgl.setCode(O2OMsgConstant.MSG_CODE.ERROR);
					msgl.setMsg(MessageFormat.format(prodIdPriceNull, msgl.getProdId()));
					msgl.setPrice(0.0);
					msgl.setDiscRate(0.0);
				}
			}
        }
		return msgList;
	}
	
	/**
	 * 校验参数是否为空
	 */
	public MsgVo checkParamIsNull(String prod_id, String unit_id, String currency){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (SoaBillUtils.isBlank(prod_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdParamNull);
		}else if(SoaBillUtils.isBlank(unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
		}else if (SoaBillUtils.isBlank(currency)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(currencyParamNull);
		}
		return msg;
	}
	
	/**
	 * 校验批量参数是否为空
	 */
	public List<MsgListVo> checkBulkParamIsNull(List<String> prod_idList, String unit_id, String currency){
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
		if (prod_idList == null || prod_idList.size() == 0) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdListParamNull);
			msgList.add(msg);
		}else if(SoaBillUtils.isBlank(unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
			msgList.add(msg);
		}else if (SoaBillUtils.isBlank(currency)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(currencyParamNull);
			msgList.add(msg);
		}
		return msgList;
	}
		
}
