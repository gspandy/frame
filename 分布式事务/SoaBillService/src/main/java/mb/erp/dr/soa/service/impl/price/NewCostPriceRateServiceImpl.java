package mb.erp.dr.soa.service.impl.price;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.dao.FiGrpCostMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.old.service.price.CostPriceRateService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewCostPriceRateService;
import mb.erp.dr.soa.utils.ConbinePriceListUnit;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.FiGrpCostVo;
import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 成本价格服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取成本价格折率和根据批量商品编码获取成本价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         NewCostPriceRateServiceImpl
 * @since       全流通改造
 */
@Service("newCostPriceRateService")
public class NewCostPriceRateServiceImpl implements NewCostPriceRateService{
	@Resource
	private FiGrpCostMapper fiGrpCostMapper;
	@Resource
	private NewERPCommonMapper newERPCommonMapper;
	@Resource
	private CostPriceRateService costPriceRateService;
	@Resource
	private NewERPCommonService newERPCommonService;
	@Value("${prodId.param.null}")
	private String prodIdParamNull;
	@Value("${prodIdList.param.null}")
	private String prodIdListParamNull;
	@Value("${unitId.param.null}")
	private String unitIdParamNull;
	@Value("${warehId.param.null}")
	private String warehIdParamNull;
	@Value("${get.cost.price.null}")
	private String getCostPriceNull;
	
	public MsgVo getCostPriceRateByProdId(String prod_id, String unit_id)
			 {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_id, unit_id, "twoParam");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		map.put("prod_num", prod_id);
		map.put("unit_id", unit_id);
		 //根据组织ID 和PROD_ID来获取成本价格信息
		List<FiGrpCostVo> fiGrpCostVos = fiGrpCostMapper.SelectByUnitIDProdId(map);
		if (fiGrpCostVos.size() == 0) {
			String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getCostPriceNull, prod_id,unit_id));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有全面启用新ERP，则还需从老ERP取
        	return costPriceRateService.getCostPriceRateByProdId(prod_id, unit_id);
		}
		msg.setPrice(fiGrpCostVos.get(0).getUnitCost());
		msg.setDiscRate(100.0);
		return msg;
	}

	public MsgVo getCostPriceRateByProd_Id(String prod_id, String unit_id,
			String wareh_id)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		msg = checkParamIsNull(prod_id, unit_id, wareh_id);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		map.put("prod_num", prod_id);
		map.put("unit_id", unit_id);
		map.put("code", wareh_id);
		 //根据组织ID 和PROD_ID来获取成本价格信息
		List<FiGrpCostVo> fiGrpCostVos = fiGrpCostMapper.selectByUnitIDProdIdWarethID(map);
		if (fiGrpCostVos.size() == 0) {
			String isNerp = newERPCommonService.getMainSysParamValue("IS_NERP");
        	if (TORF.TRUE.equals(isNerp)) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        		msg.setMsg(MessageFormat.format(getCostPriceNull, prod_id,unit_id));
        		throw new RuntimeException(msg.getMsg());
			}
        	//如果没有全面启用新ERP，则还需从老ERP取
        	return costPriceRateService.getCostPriceRateByProd_Id(prod_id, unit_id, wareh_id);
		}
		msg.setPrice(fiGrpCostVos.get(0).getUnitCost());
		msg.setDiscRate(100.0);
		return msg;
	}
	
	/**
	 * 批量获取成本价格
	 */
	public List<MsgListVo> bulkGetCostPriceRateByProdId(
			List<String> prodIdList, String unit_code)  {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prodIdList, unit_code, "twoParam");
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		//往新ERP临时表插入数据
		newERPCommonMapper.saveProdToTmpBfOrgCode(prodIdList);
        map.put("unit_id", unit_code);
        
        //获取成本价格
        List<FiGrpCostVo> fiGrpCostVos = fiGrpCostMapper.buklGetByUnitIDProdId(map);
        if (fiGrpCostVos == null || fiGrpCostVos.size() == 0) {
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.ERROR,"","");
			msg.setMsg(MessageFormat.format(getCostPriceNull, "",unit_code));
			msgList.add(msg);
			return msgList;
		}
        
        for(String prodId : prodIdList){
        	boolean flg = false;
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setWunitId(unit_code);
        	msg.setProdId(prodId);
        	for(FiGrpCostVo fiGrpCost : fiGrpCostVos){
        		if (prodId.equals(fiGrpCost.getProdId())) {
        			flg = true;
					msg.setPrice(fiGrpCost.getUnitCost());
					msg.setDiscRate(100.0);
					msgList.add(msg);
				}
        	}
        	if (!flg) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setPrice(0.0);
				msg.setDiscRate(0.0);
				msg.setMsg(MessageFormat.format(getCostPriceNull, msg.getProdId(),unit_code));
				msgList.add(msg);
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
				List<MsgListVo> oldmsgList = costPriceRateService.bulkGetCostPriceRateByProdId(prodList, unit_code);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
			}
		}
		return msgList;
	}
	
	/**
	 * 根据仓库ID批量获取成本价格
	 */
	public List<MsgListVo> bulkGetCostPriceRateByProdIdWarehCode(
			List<String> prodIdList, String unit_code, String wareh_id)
			 {
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		msgList = checkBulkParamIsNull(prodIdList, unit_code, wareh_id);
		if (msgList.size() > 0 && O2OMsgConstant.MSG_CODE.ERROR.equals(msgList.get(0).getCode())) {
			throw new RuntimeException(msgList.get(0).getMsg());
		}
		Map<String, String> map =  new HashMap<String,String>();
		//往新ERP临时表插入数据
		newERPCommonMapper.saveProdToTmpBfOrgCode(prodIdList);
        map.put("unit_id", unit_code);
        map.put("wareh_code", wareh_id);
        
        //获取成本价格
        List<FiGrpCostVo> fiGrpCostVos = fiGrpCostMapper.buklGetByUnitIDProdIdWarehID(map);
        if (fiGrpCostVos.size() == 0) {
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.ERROR,"","");
			msg.setMsg(MessageFormat.format(getCostPriceNull, msg.getProdId(),unit_code));
			msgList.add(msg);
			throw new RuntimeException(msgList.get(0).getMsg());
		}
        
        for(String prodId : prodIdList){
        	boolean flg = false;
        	MsgListVo msg= new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
        	msg.setWunitId(unit_code);
        	msg.setProdId(prodId);
        	for(FiGrpCostVo fiGrpCost : fiGrpCostVos){
        		if (prodId.equals(fiGrpCost.getProdId())) {
        			flg = true;
					msg.setPrice(fiGrpCost.getUnitCost());
					msg.setDiscRate(100.0);
					msgList.add(msg);
				}
        	}
        	if (!flg) {
        		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setPrice(0.0);
				msg.setDiscRate(0.0);
				msg.setMsg(MessageFormat.format(getCostPriceNull, msg.getProdId(),unit_code));
				msgList.add(msg);
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
				List<MsgListVo> oldmsgList = costPriceRateService.bulkGetCostPriceRateByProdIdWarehCode(prodList, unit_code, wareh_id);
				return ConbinePriceListUnit.conbinePriceFromNewErpAndOldByWunit(msgList,oldmsgList);
			}
		}
		return msgList;
	}
	
	/**
	 * 校验参数是否为空
	 */
	public MsgVo checkParamIsNull(String prod_id, String unit_id, String wareh_id){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		if (SoaBillUtils.isBlank(prod_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdParamNull);
		}else if(SoaBillUtils.isBlank(unit_id)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
		}else if (!"twoParam".endsWith(wareh_id) && SoaBillUtils.isBlank(wareh_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(warehIdParamNull);
		}
		return msg;
	}
	
	/**
	 * 校验批量参数是否为空
	 */
	public List<MsgListVo> checkBulkParamIsNull(List<String> prodIdList, String unit_code, String wareh_id){
		List<MsgListVo> msgList = new ArrayList<MsgListVo>();
		MsgListVo msg = new MsgListVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","");
		if (prodIdList == null || prodIdList.size() == 0) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(prodIdListParamNull);
			msgList.add(msg);
		}else if(SoaBillUtils.isBlank(unit_code)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(unitIdParamNull);
			msgList.add(msg);
		}else if (!"twoParam".endsWith(wareh_id) && SoaBillUtils.isBlank(wareh_id)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(warehIdParamNull);
			msgList.add(msg);
		}
		return msgList;
	}

}
