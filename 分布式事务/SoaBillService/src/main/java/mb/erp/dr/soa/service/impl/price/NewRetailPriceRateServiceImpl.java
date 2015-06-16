package mb.erp.dr.soa.service.impl.price;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.dao.BfOrgMapper;
import mb.erp.dr.soa.dao.PrPrcListDtlMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.old.service.price.RetailPriceRateService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewRetailPriceRateService;
import mb.erp.dr.soa.utils.ConbinePriceListUnit;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.PrPrcListDtlVo;
import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 零售价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取零售价格折率和根据批量商品编码获取零售价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         NewRetailPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("newRetailPriceRateService")
public class NewRetailPriceRateServiceImpl implements NewRetailPriceRateService{
	@Resource
	private BfOrgMapper bfOrgMapper;
	@Resource
	private PrPrcListDtlMapper prPrcListDtlMapper;
	@Resource
	private NewERPCommonMapper newERPCommonMapper;
	@Resource
	private RetailPriceRateService retailPriceRateService;
	@Resource
	private NewERPCommonService newERPCommonService;
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
	
	/**
	 * 根据商品款编码获取零售价格
	 */
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
        Map<String, String> map =  new HashMap<String,String>();
        map.put("prod_cls_num", prod_cls_num);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
        
        //根据传入的门店(组织)ID和组织机构查询
        //获取门店的上级组织
        String agUnitID = getAgentID(unit_code);
        map.put("ag_unit_id", agUnitID);
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.selectByPrddClsNum(map);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        //根据商品款编码递归获取零售价格
        if (prPrcListDtls.size() == 0) {
        	String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getRetailPriceNull, prod_cls_num,unit_code));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有启用新ERP，则还需从老ERP取
        	return retailPriceRateService.getRetailPriceRateByProdClsNum(prod_cls_num, unit_code, currency);
		}
        msg.setPrice(prPrcListDtls.get(0).getPrice());
        if ("P".equals(prPrcListDtls.get(0).getPrcPlcy()) || prPrcListDtls.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prPrcListDtls.get(0).getDiscRate());
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
        //获取门店的上级组织
	    String agUnitID = getAgentID(unit_id);
        map.put("ag_unit_id", agUnitID);
        map.put("prod_num", prod_id);
        map.put("unit_id", unit_id);
        map.put("currency", currency);
        map.put("prod_color_num", prod_id.substring(6, 8));
        //根据组织ID 和PROD_ID来获取零售价格信息
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.selectByProdIdUnitID(map);
        if (prPrcListDtls.size() == 0) {
        	String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getRetailPriceNull, prod_id,unit_id));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有启用新ERP，则还需从老ERP取
        	return retailPriceRateService.getRetailPriceRateByProdId(prod_id, unit_id, currency);
		}
        msg.setPrice(prPrcListDtls.get(0).getPrice());
        if ("P".equals(prPrcListDtls.get(0).getPrcPlcy()) || prPrcListDtls.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prPrcListDtls.get(0).getDiscRate());
		}
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
		//获取门店的上级组织
	    String agUnitID = getAgentID(unit_code);
        map.put("ag_unit_id", agUnitID);
		String prod_id = prod_color_num.substring(0,6);
        String prod_color = prod_color_num.substring(6, 8);
	    map.put("prod_color_num", prod_color);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
        map.put("prod_cls_num", prod_id);
        //根据组织ID 和PROD_ID来获取零售价格信息
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.selectByProdColorUnitID(map);
        if (prPrcListDtls.size() == 0) {
        	String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getRetailPriceNull, prod_color_num,unit_code));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有启用新ERP，则还需从老ERP取
        	return retailPriceRateService.getRetailPriceRateByProdColorNum(prod_color_num, unit_code, currency);
		}
        msg.setPrice(prPrcListDtls.get(0).getPrice());
        if ("P".equals(prPrcListDtls.get(0).getPrcPlcy()) || prPrcListDtls.get(0).getDiscRate() == 0) {
			msg.setDiscRate(100.0);
		}else {
			msg.setDiscRate(prPrcListDtls.get(0).getDiscRate());
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
		//获取门店的上级组织
	    String agUnitID = getAgentID(unit_code);
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
        newERPCommonMapper.saveProdToTmpBfOrgCode(prod_cls_numlist);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.priceBulkGetNewRetailPriceRate(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrPrcListDtlVo prPrcLstDtl : prPrcListDtls){
					if (msgl.getProdId().equals(prPrcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prPrcLstDtl.getPrice());
				        if ("P".equals(prPrcLstDtl.getPrcPlcy()) || prPrcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prPrcLstDtl.getDiscRate());
						}
					}
				}
				if (!flg) {
					msgl.setMsg(MessageFormat.format(prodIdPriceNull, msgl.getProdId()));
				}
			}
        }
        //是否全面启用新ERP
        String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			for(MsgListVo msg :msgList){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = retailPriceRateService.bulkGetRetailPriceRateByProdClsNum(prodList, unit_code, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
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
		//获取门店的上级组织
	    String agUnitID = getAgentID(unit_code);
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
        newERPCommonMapper.saveProdToTmpBfOrgCode(prod_numlist);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.priceBulkGetNewRetailPriceRateByProdNum(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrPrcListDtlVo prPrcLstDtl : prPrcListDtls){
					if (msgl.getProdId().equals(prPrcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prPrcLstDtl.getPrice());
				        if ("P".equals(prPrcLstDtl.getPrcPlcy()) || prPrcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prPrcLstDtl.getDiscRate());
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
        //是否全面启用新ERP
        String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			for(MsgListVo msg :msgList){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = retailPriceRateService.bulkGetRetailPriceRateByProdId(prodList, unit_code, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
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
		//获取门店的上级组织
	    String agUnitID = getAgentID(unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("unit_id", unit_code);
        map.put("currency", currency);
		for (String prod_color_num : prod_color_numlist) {
			map.put("prod_num", prod_color_num);
			List<String> prodNumList = prPrcListDtlMapper.selectProdNumByProdColorNum(map);
			List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.selectByProdColorUnitID2(map);
			for (String prodNum : prodNumList) {
				map.put("prod_num", prodNum);
				List<PrPrcListDtlVo> prPrcListDtl3s = prPrcListDtlMapper.selectByProdColorUnitID3(map);
				MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
				msg.setProdId(prodNum);
				msg.setWunitId(unit_code);
				if (prPrcListDtl3s.size() ==0 ) {
					if (prPrcListDtls.size() == 0) {
						msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
						msg.setMsg(MessageFormat.format(getRetailPriceNull, prod_color_num,unit_code));
						msg.setPrice(0.0);
						msg.setDiscRate(0.0);
						msgList.add(msg);
					}else {
						msg.setPrice(prPrcListDtls.get(0).getPrice());
						if ("P".equals(prPrcListDtls.get(0).getPrcPlcy()) || prPrcListDtls.get(0).getDiscRate() == 0) {
							msg.setDiscRate(100.0);
						}else {
							msg.setDiscRate(prPrcListDtls.get(0).getDiscRate());
						}
						msgList.add(msg);
					}
				}else {
					msg.setPrice(prPrcListDtl3s.get(0).getPrice());
					if ("P".equals(prPrcListDtl3s.get(0).getPrcPlcy()) || prPrcListDtl3s.get(0).getDiscRate() == 0) {
						msg.setDiscRate(100.0);
					}else {
						msg.setDiscRate(prPrcListDtl3s.get(0).getDiscRate());
					}
					msgList.add(msg);
				}
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
	    String agUnitID = getAgentID(unit_code);
        for(String inTnlBc : inTnlBcList){
        	MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setWunitId(unit_code);
        	msg.setProdId(inTnlBc);
        	msgList.add(msg);
        }
        //将商品编码存入临时表
        newERPCommonMapper.saveProdToTmpBfOrgCode(inTnlBcList);
        
        //根据组织ID 和PROD_ID来获取零售价格信息
        map.put("unit_id", unit_code);
        map.put("ag_unit_id", agUnitID);
        map.put("currency", currency);
        List<PrPrcListDtlVo> prPrcListDtls = prPrcListDtlMapper.priceBulkGetNewRetailPriceRateByIntnlBc(map);
        for(MsgListVo msgl : msgList){
        	if (SoaBillUtils.isBlank(msgl.getMsg())) {
        		boolean flg = false;
				for(PrPrcListDtlVo prPrcLstDtl : prPrcListDtls){
					if (msgl.getProdId().equals(prPrcLstDtl.getProdId())) {
						flg = true;
						msgl.setPrice(prPrcLstDtl.getPrice());
				        if ("P".equals(prPrcLstDtl.getPrcPlcy()) || prPrcLstDtl.getDiscRate() == 0) {
				        	msgl.setDiscRate(100.0);
						}else {
							msgl.setDiscRate(prPrcLstDtl.getDiscRate());
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
        //是否全面启用新ERP
        String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			for(MsgListVo msg :msgList){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = retailPriceRateService.bulkGetRetailPriceRateByIntnlBc(prodList, unit_code, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
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
	
	/**
	 * 获取门店的上级组织
	 * @param unit_code
	 * @return
	 */
	public String getAgentID(String unit_code){
		Map<String, String> map = new HashMap<String, String>();
		map.put("unit_code", unit_code);
		String unit_id = bfOrgMapper.selectAgentID(map);
		String agUnitID = "";
        if (SoaBillUtils.isBlank(unit_id)) {
        	agUnitID = unit_code;//说明传进来的是代理商
		}else {
			agUnitID = unit_id;
		}
		return agUnitID;
	}

}
