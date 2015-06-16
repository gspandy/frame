package mb.erp.dr.soa.old.service.impl.price;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.AgentBrandRateListMapper;
import mb.erp.dr.soa.old.dao.SysUnitMapper;
import mb.erp.dr.soa.old.dao.TmpInvalidDefMapper;
import mb.erp.dr.soa.old.dao.WprcLstDtlMapper;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.old.vo.WprcLstDtlVo;
import mb.erp.dr.soa.utils.PriceCheckUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
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
 * @see         SettlementPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("settlementPriceRateService")
public class SettlementPriceRateServiceImpl implements SettlementPriceRateService{
	private final Logger logger = LoggerFactory.getLogger(SettlementPriceRateServiceImpl.class);
	@Resource
	private WprcLstDtlMapper wprcLstDtlMapper;
	@Resource
	private SysUnitMapper sysUnitMapper;
	@Resource
	private AgentBrandRateListMapper agentBrandRateListMapper;
	@Resource
	private TmpInvalidDefMapper tmpInvalidMapper;
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
		String prod_prop = prod_cls_num.substring(0,1);
        String prod_sort = prod_cls_num.substring(1, 3);
        String prod_style = prod_cls_num.substring(3, 6);
        currency = PriceCheckUnit.getCurreny(currency);
        Map<String, String> map =  new HashMap<String,String>();
        map.put("prod_prop", prod_prop);
        map.put("prod_sort", prod_sort);
        map.put("prod_style", prod_style);
        map.put("prod_id", prod_cls_num);
        map.put("unit_id", unit_code);
        map.put("wunit_id", cpd_unit_code);
        map.put("currency", currency);
        //获取价格
        List<WprcLstDtlVo> wprcLstDtlVos = wprcLstDtlMapper.selectSettlementPriceRateByProdClsNum(map);
        if (wprcLstDtlVos.size() == 0) {
        	//判断一下是否是门店(sh)或者是仓库(gc)
        	String owner_id = sysUnitMapper.selectOwnerID(map);
        	if (SoaBillUtils.isNotBlank(owner_id)) {
        		msg.setMsg(MessageFormat.format(settlementPriceNull, owner_id));
        		cpd_unit_code = owner_id;
        		map.put("wunit_id", cpd_unit_code);
        		wprcLstDtlVos = wprcLstDtlMapper.selectSettlementPriceRateByProdClsNum(map);
			}
		}
        if (wprcLstDtlVos.size() == 0) {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getPriceNull, unit_code,cpd_unit_code,currency,prod_cls_num));
			throw new RuntimeException(msg.getMsg());
		}
        WprcLstDtlVo wprcLstDtl = wprcLstDtlVos.get(0);
        msg.setPrice(wprcLstDtl.getPrice());
        
        //判断是合同折率 R、指定价格 P 和组合定价 M 分别用不同的方式计算
        if ("R".equals(wprcLstDtl.getPrcPlcy())) {
        	map.put("agent_id", cpd_unit_code);
        	Double discRate = agentBrandRateListMapper.selectObjectByProdCls(map);
        	msg.setDiscRate(discRate);
		}else if("P".equals(wprcLstDtl.getPrcPlcy())){
			msg.setDiscRate(100.0);
		}else if ("M".equals(wprcLstDtl.getPrcPlcy())) {
			msg.setDiscRate(wprcLstDtl.getDiscRate());
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(prodIdPriceNull, prod_cls_num));
		}
        logger.warn("获取结算价格(prod_cls_num)：" + msg.getPrice() + "折率：" + msg.getDiscRate());
		return msg;
	}

	public MsgVo getSettlementPriceRateByProdId(String prod_id, String unit_id,
			String cpd_unit_id, String currency)  {
		logger.warn("获取结算价格参数：prod_id: "+prod_id+" unit_id: "+unit_id+" cpd_unit_id: "+cpd_unit_id);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		try {
			msg = checkParamIsNull(prod_id, unit_id, cpd_unit_id, currency);
			if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
				throw new RuntimeException(msg.getMsg());
			}
			Map<String, String> map =  new HashMap<String,String>();
			currency = PriceCheckUnit.getCurreny(currency);
			map.put("prod_id", prod_id);
			map.put("currency", currency);
			map.put("unit_id", unit_id);
			map.put("wunit_id", cpd_unit_id);
			//判断一下是否是门店(sh)或者是仓库(gc)
			String owner_id = sysUnitMapper.selectOwnerID(map);
			if (SoaBillUtils.isNotBlank(owner_id)) {
				cpd_unit_id = owner_id;
				map.put("wunit_id", cpd_unit_id);
			}
			//获取价格
			List<WprcLstDtlVo> wprcLstDtlVos = wprcLstDtlMapper.selectSettlementPriceRateByProdId(map);
			
			if (wprcLstDtlVos.size() == 0) {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg(MessageFormat.format(getPriceNull, unit_id,cpd_unit_id,currency,prod_id));
				throw new RuntimeException(msg.getMsg());
			}
			WprcLstDtlVo wprcLstDtl = wprcLstDtlVos.get(0);
			msg.setPrice(wprcLstDtl.getPrice());
			
			//判断是合同折率 R、指定价格 P 和组合定价 M 分别用不同的方式计算
			if ("R".equals(wprcLstDtl.getPrcPlcy())) {
				map.put("agent_id", cpd_unit_id);
				Double discRate = agentBrandRateListMapper.selectObject(map);
				msg.setDiscRate(discRate);
			}else if("P".equals(wprcLstDtl.getPrcPlcy())){
				msg.setDiscRate(100.0);
			}else if ("M".equals(wprcLstDtl.getPrcPlcy())) {
				msg.setDiscRate(wprcLstDtl.getDiscRate());
			}else {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg(MessageFormat.format(prodIdPriceNull, prod_id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(getPriceNull, unit_id,cpd_unit_id,currency,prod_id));
			throw new RuntimeException(msg.getMsg());
		}
		logger.warn("获取结算价格：" + msg.getPrice() + "折率：" + msg.getDiscRate()+"prod_id: "+prod_id+" unit_id: "+unit_id+" cpd_unit_id: "+cpd_unit_id);
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
		map.put("wunit_id", cpd_unit_code);
		map.put("unit_id", unit_code);
		map.put("currency", currency);
		String owner_id = sysUnitMapper.selectOwnerID(map);
		if (SoaBillUtils.isNotBlank(owner_id)) {
			cpd_unit_code = owner_id;
			map.put("wunit_id", cpd_unit_code);
		}
		//往老ERP临时表插入数据
		tmpInvalidMapper.saveTempProductNum(prod_numlist);
		map.put("agent_id", cpd_unit_code);
		List<WprcLstDtlVo> wprcLstDtlList = wprcLstDtlMapper.bulkGetSettlePriceOld(map);
		List<WprcLstDtlVo> agentBrandRateList = agentBrandRateListMapper.bulkGetAgentBrandRateOld(map);
		for (String prodNum : prod_numlist) {
			MsgListVo msgList= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
			msgList.setWunitId(cpd_unit_code);
			msgList.setProdId(prodNum);
			MsgVo msg = getSettlePriceByProdNum(wprcLstDtlList,agentBrandRateList,prodNum);
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
		return msgListVos;
	}
	
	/**
	 * 根据产品编码查询价格方式
	 * @param wprcLstDtlList
	 * @param agentBrandRateList
	 * @param prodNum
	 * @return
	 */
	public MsgVo getSettlePriceByProdNum(List<WprcLstDtlVo> wprcLstDtlList, List<WprcLstDtlVo> agentBrandRateList, String prodNum){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (wprcLstDtlList.size() == 0) {
			logger.error("根据产品编码查询价格方式,wprcLstDtlList为空");
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(MessageFormat.format(prodIdPriceNull, prodNum));
			throw new RuntimeException(msg.getMsg());
		}
		for(WprcLstDtlVo wprcLstDtl : wprcLstDtlList){
			if (prodNum.equals(wprcLstDtl.getProdId())) {
				msg.setPrice(wprcLstDtl.getPrice());
				if ("R".equals(wprcLstDtl.getPrcPlcy())) {
					Double discRate = 0D;
					if ( agentBrandRateList.size() == 0) {
						logger.error("根据产品编码查询价格方式,agentBrandRateList为空");
						msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
						msg.setMsg(MessageFormat.format(prodIdPriceNull, prodNum));
						throw new RuntimeException(msg.getMsg());
					}
					for (WprcLstDtlVo agentBrandRate : agentBrandRateList) {
						if (prodNum.equals(agentBrandRate.getProdId())) {
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
