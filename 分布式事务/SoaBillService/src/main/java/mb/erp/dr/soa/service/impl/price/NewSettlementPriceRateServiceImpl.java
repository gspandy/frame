package mb.erp.dr.soa.service.impl.price;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.BfOrgMapper;
import mb.erp.dr.soa.dao.PrAgentRateListMapper;
import mb.erp.dr.soa.dao.PrWprcListDtlMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.old.service.dubbo.SoaPriceDubboService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewSettlementPriceRateService;
import mb.erp.dr.soa.utils.ConbinePriceListUnit;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.PrWprcListDtlVo;
import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 结算价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取结算价格折率和根据批量商品编码获取结算价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         NewSettlementPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("newSettlementPriceRateService")
public class NewSettlementPriceRateServiceImpl implements NewSettlementPriceRateService{
	private final Logger logger = LoggerFactory.getLogger(NewSettlementPriceRateServiceImpl.class);
	@Resource
	private PrWprcListDtlMapper prWprcListDtlMapper;
	@Resource
	private BfOrgMapper bfOrgMapper;
	@Resource
	private PrAgentRateListMapper prAgentRateListMapper;
	@Resource
	private SoaPriceDubboService soaPriceDubboService;
	@Resource
	private NewERPCommonMapper newERPCommonMapper;
	@Resource
	private SettlementPriceRateService settlementPriceRateService;
	@Resource
	private NewERPCommonService newERPCommonService;
	@Value("${prodId.param.null}")
	private String prodIdParamNull;
	@Value("${prodIdList.param.null}")
	private String prodIdListParamNull;
	@Value("${unitId.param.null}")
	private String unitIdParamNull;
	@Value("${cpdUnitId.param.null}")
	private String cpdUnitIdParamNull;
	@Value("${cpdUnitIdList.param.null}")
	private String cpdUnitIdListParamNull;
	@Value("${currency.param.null}")
	private String currencyParamNull;
	@Value("${sixProdId.param.error}")
	private String sixProdIdParamError;
	@Value("${prodId.price.null}")
	private String prodIdPriceNull;
	@Value("${get.price.null}")
	private String getPriceNull;
	@Value("${settlement.price.null}")
	private String 	settlementPriceNull;

	
	/**
	 * 根据传入的商品款6位编码来查询传出价格和折率  结算价格
	 */
	public MsgVo getSettlementPriceRateByProdClsNum(String prod_cls_num,
			String unit_code, String cpd_unit_code, String currency)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_cls_num, unit_code, cpd_unit_code, currency);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			return msg;
		}
		if (prod_cls_num.length() != 6) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(sixProdIdParamError);
			throw new RuntimeException(msg.getMsg());
		}
        Map<String, String> map =  new HashMap<String,String>();
        map.put("prod_cls_num", prod_cls_num);
        map.put("unit_id", cpd_unit_code);
        map.put("owner_id", unit_code);
        map.put("currency", currency);
        //查询结算价格详细
        String owner_id = "";
        List<PrWprcListDtlVo> prWprcListDtls = prWprcListDtlMapper.selectNewSettlementPriceRateByProdClsNum(map);
        if (prWprcListDtls.size() == 0) {
        	//判断是否是门店（11）或者货流中心（7），如果是就用上级去查询
        	owner_id = bfOrgMapper.selectOwnerId(map);
        	if (SoaBillUtils.isNotBlank(owner_id)) {
        		msg.setMsg(MessageFormat.format(settlementPriceNull, owner_id));
        		map.put("unit_id", owner_id);
        		prWprcListDtls = prWprcListDtlMapper.selectNewSettlementPriceRateByProdClsNum(map);
			}
		}
        if (prWprcListDtls.size() == 0) {
        	String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getPriceNull, prod_cls_num,unit_code,cpd_unit_code));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有全面启用新ERP，则还需从老ERP取
        	return settlementPriceRateService.getSettlementPriceRateByProdClsNum(prod_cls_num, unit_code, cpd_unit_code, currency);
		}
        PrWprcListDtlVo prwprcLstDtl = prWprcListDtls.get(0);
        msg.setPrice(prwprcLstDtl.getPrice());
        cpd_unit_code = owner_id;
        //判断是合同折率 R、指定价格 P 和组合定价 M 分别用不同的方式计算
        if ("R".equals(prwprcLstDtl.getPrcPlcy())) {
        	map.put("agent_id", cpd_unit_code);
        	Double discRate = prAgentRateListMapper.selectAgentRateByProdCls(map);
        	msg.setDiscRate(discRate);
		}else if("P".equals(prwprcLstDtl.getPrcPlcy())){
			msg.setDiscRate(100.0);
		}else if ("M".equals(prwprcLstDtl.getPrcPlcy())) {
			msg.setDiscRate(prwprcLstDtl.getDiscRate());
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(prodIdPriceNull, prod_cls_num));
		}
		return msg;
	}

	public MsgVo getSettlementPriceRateByProdId(String prod_id, String unit_id,
			String cpd_unit_id, String currency)  {
		logger.warn("新ERP获取结算价格参数：prod_id: "+prod_id+" unit_id: "+unit_id+" cpd_unit_id: "+cpd_unit_id);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_id, unit_id, cpd_unit_id, currency);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		map.put("prod_id", prod_id);
		map.put("currency", currency);
		map.put("unit_id", cpd_unit_id);
		map.put("owner_id", unit_id);
		map.put("prod_color_num", prod_id.substring(6, 8));
		
    	//查询结算价格详细
    	List<PrWprcListDtlVo> prWprcListDtls = prWprcListDtlMapper.selectByPOUC(map);
    	String owner_id = "";
    	String isNerp = "";
    	if (prWprcListDtls.size() == 0) {
    		//判断一下是否是门店（11）或者货流中心（7），如果是就用上级去查询
    		owner_id = bfOrgMapper.selectOwnerId(map);
        	if (SoaBillUtils.isNotBlank(owner_id)) {
        		msg.setMsg(MessageFormat.format(settlementPriceNull, owner_id));
        		map.put("unit_id", owner_id);
        		prWprcListDtls = prWprcListDtlMapper.selectByPOUC(map);
			}
		}
    	
    	if (prWprcListDtls.size() == 0) {
        	isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getPriceNull, prod_id,unit_id,cpd_unit_id));
        		throw new RuntimeException(msg.getMsg());
        	}
        	//如果没有全面启用新ERP，则还需从老ERP取
        	return settlementPriceRateService.getSettlementPriceRateByProdId(prod_id, unit_id, cpd_unit_id, currency);
		}
    	PrWprcListDtlVo prWprcListDtl = prWprcListDtls.get(0);
    	msg.setPrice(prWprcListDtl.getPrice());
    	cpd_unit_id = owner_id;
    	//判断是合同折率 R、指定价格 P 和组合定价 M 分别用不同的方式计算
        if ("R".equals(prWprcListDtl.getPrcPlcy())) {
        	map.put("agent_id", cpd_unit_id);
        	map.put("unit_id", unit_id);
        	Double discRate = prAgentRateListMapper.selectAgentRateByProdID(map);
        	if (discRate == 0) {
        		if (TORF.TRUE.equals(isNerp)) {
        			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        			msg.setMsg(MessageFormat.format(getPriceNull, prod_id,unit_id,cpd_unit_id));
        			throw new RuntimeException(msg.getMsg());
        		}
        		//如果没有全面启用新ERP，则还需从老ERP取
        		msg.setDiscRate(soaPriceDubboService.selectObject(map));
            	return msg;
			}
        	msg.setDiscRate(discRate);
		}else if("P".equals(prWprcListDtl.getPrcPlcy())){
			msg.setDiscRate(100.0);
		}else if ("M".equals(prWprcListDtl.getPrcPlcy())) {
			msg.setDiscRate(prWprcListDtl.getDiscRate());
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(prodIdPriceNull, prod_id));
		}
        logger.warn("新ERP获取结算价格：" + msg.getPrice() + "折率：" + msg.getDiscRate());
		return msg;
	}
	
	/**
	 * 批量获取结算价格(商品款)
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdClsNum(
			List<String> prod_cls_numlist, String unit_code,
			List<String> cpd_unit_code, String currency)  {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prod_cls_numlist, unit_code, cpd_unit_code, "", currency,true);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		for (String cuc : cpd_unit_code) {
			for (String pcn : prod_cls_numlist) {
				MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
				MsgVo msgVo = getSettlementPriceRateByProdClsNum(pcn,unit_code,cuc,currency);
				msg.setWunitId(cuc);
				msg.setProdId(pcn);
				msg.setPrice(msgVo.getPrice());
				msg.setDiscRate(msgVo.getDiscRate());
				msg.setMsg(msgVo.getMsg());
				msgList.add(msg);
			}
		}
		String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			List<String> cpdUnitList = new ArrayList<String>();
			for(MsgListVo msg :msgList){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
					cpdUnitList.add(msg.getWunitId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = settlementPriceRateService.bulkGetSettlementPriceRateByProdClsNum(prodList, unit_code, cpdUnitList, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
			}
		}
		return msgList;
	}
	
	/**
	 *  批量获取结算价格(商品编码)
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdId(
			List<String> prod_numlist, String unit_code,
			List<String> cpd_unit_code, String currency)  {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prod_numlist, unit_code, cpd_unit_code, "", currency,true);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		for (String cuc : cpd_unit_code) {
			for (String pn : prod_numlist) {
				MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
				MsgVo msgVo = getSettlementPriceRateByProdId(pn,unit_code,cuc,currency);
				msg.setWunitId(cuc);
				msg.setProdId(pn);
				msg.setPrice(msgVo.getPrice());
				msg.setDiscRate(msgVo.getDiscRate());
				msg.setMsg(msgVo.getMsg());
				msgList.add(msg);
			}
		}
		String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			List<String> cpdUnitList = new ArrayList<String>();
			for(MsgListVo msg :msgList){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
					cpdUnitList.add(msg.getWunitId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = settlementPriceRateService.bulkGetSettlementPriceRateByProdId(prodList, unit_code, cpdUnitList, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
			}
		}
		return msgList;
	}
	
	/**
	 * 批量获取结算价格(商品编码) 临时表处理
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdIdList(
			List<String> prod_numlist, String unit_code,
			String cpd_unit_code, String currency)  {
		List<MsgListVo> msgListVos = new ArrayList<MsgListVo>();
		msgListVos = checkBulkParamIsNull(prod_numlist, unit_code, null, cpd_unit_code, currency,false);
		if (msgListVos.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgListVos.get(0).getCode())) {
			throw new RuntimeException(msgListVos.get(0).getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		currency = PriceCheckUnit.getCurreny(currency);
		map.put("owner_id", unit_code);
		map.put("unit_id", cpd_unit_code);
		map.put("currency", currency);
		//判断一下是否是门店（11）或者货流中心（7），如果是就用上级去查询
		String owner_id = bfOrgMapper.selectOwnerId(map);
    	if (SoaBillUtils.isNotBlank(owner_id)) {
    		cpd_unit_code = owner_id;
		}
		//往老ERP临时表插入数据
		newERPCommonMapper.saveProdToTmpBfOrgCode(prod_numlist);
		List<PrWprcListDtlVo> prWprcLstDtlList = prWprcListDtlMapper.bulkGetSettlePrice(map);
		map.put("agent_id", cpd_unit_code);
		map.put("unit_id", unit_code);
		List<PrWprcListDtlVo> agentBrandRateList = prAgentRateListMapper.bulkGetAgentBrandRateByProdID(map);
		for (String prodNum : prod_numlist) {
			MsgListVo msgList= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
			msgList.setWunitId(cpd_unit_code);
			msgList.setProdId(prodNum);
			MsgVo msg = getSettlePriceByProdNum(prWprcLstDtlList,agentBrandRateList,prodNum);
			if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
				msgList.setPrice(0.0);
				msgList.setDiscRate(0.0);
				msgList.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msgList.setMsg(MessageFormat.format(prodIdPriceNull, prodNum));
			}else {
				msgList.setPrice(msg.getPrice());
				msgList.setDiscRate(msg.getDiscRate());
				msgList.setMsg(msg.getMsg());
			}
			msgListVos.add(msgList);
		}
		String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
		if (TORF.FALSE.equals(isNerp)) {
			List<String> prodList = new ArrayList<String>();
			for(MsgListVo msg :msgListVos){
				if (msg.getPrice() == 0 || msg.getDiscRate() == 0) {
					prodList.add(msg.getProdId());
				}
			}
			if (prodList != null && prodList.size() > 0) {
				//没有启用新ERP(IS_NERP = F)，且没有取到价格的，则还需从老ERP取
				List<MsgListVo> oldmsgList = settlementPriceRateService.bulkGetSettlementPriceRateByProdIdList(prodList, unit_code, cpd_unit_code, currency);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgListVos,oldmsgList);
			}
		}
		return msgListVos;
	}
	
	/**
	 * 根据产品编码查询价格方式
	 * @param wprcLstDtlList
	 * @param agentBrandRateList
	 * @param prodNum
	 * @return
	 */
	public MsgVo getSettlePriceByProdNum(List<PrWprcListDtlVo> wprcLstDtlList, List<PrWprcListDtlVo> agentBrandRateList, String prodNum){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (wprcLstDtlList.size() == 0 || agentBrandRateList.size() == 0) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(prodIdPriceNull, prodNum));
			throw new RuntimeException(msg.getMsg());
		}
		for(PrWprcListDtlVo wprcLstDtl : wprcLstDtlList){
			if (prodNum.equals(wprcLstDtl.getProdNum())) {
				msg.setPrice(wprcLstDtl.getPrice());
				if ("R".equals(wprcLstDtl.getPrcPlcy())) {
					Double discRate = 0d;
					for (PrWprcListDtlVo agentBrandRate : agentBrandRateList) {
						if (prodNum.equals(agentBrandRate.getProdNum())) {
							discRate = agentBrandRate.getDiscRate();
						}
					}
					msg.setDiscRate(discRate);
				}else if ("P".equals(wprcLstDtl.getPrcPlcy())) {
					msg.setDiscRate(100.0);				
				}else if ("M".equals(wprcLstDtl.getPrcPlcy())) {
					msg.setDiscRate(wprcLstDtl.getDiscRate());
				}else {
					msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
					msg.setMsg(MessageFormat.format(prodIdPriceNull, prodNum));
				}
			}
		}
		return msg;
	}
	
	/**
	 * 校验参数是否为空
	 */
	public MsgVo checkParamIsNull(String prod_id, String unit_id, String cpd_unit_id, String currency){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (SoaBillUtils.isBlank(prod_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdParamNull);
		}else if(SoaBillUtils.isBlank(unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
		}else if(SoaBillUtils.isBlank(cpd_unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(cpdUnitIdParamNull);
		}else if (SoaBillUtils.isBlank(currency)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(currencyParamNull);
		}
		return msg;
	}
	
	/**
	 * 校验批量参数是否为空
	 */
	public List<MsgListVo> checkBulkParamIsNull(List<String> prod_idList, String unit_id, List<String> cpd_unit_code_list, String cpd_unit_code, String currency,boolean flg){
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
		}else if (flg && (cpd_unit_code_list == null || cpd_unit_code_list.size() == 0)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(cpdUnitIdListParamNull);
			msgList.add(msg);
		}else if (!flg && (SoaBillUtils.isBlank(cpd_unit_code))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(cpdUnitIdParamNull);
			msgList.add(msg);
		}else if (SoaBillUtils.isBlank(currency)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(currencyParamNull);
			msgList.add(msg);
		}
		return msgList;
	}

}
