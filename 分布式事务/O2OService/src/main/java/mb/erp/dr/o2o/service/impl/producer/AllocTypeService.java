package mb.erp.dr.o2o.service.impl.producer;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.SRC_DOC_TYPE;
import mb.erp.dr.soa.old.service.dubbo.SoaJmsDubboService;
import mb.erp.dr.soa.old.vo.SysUnitVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 判断配货模式
 * 判断调拨，直配，调配，3方调配，转配，转配+调配，直配+转配，调配+转配，调配+3方调配 等模式
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         AllocTypeService
 * @since       全流通改造
 */
@Service
public class AllocTypeService {
	private final Logger logger = LoggerFactory.getLogger(AllocTypeService.class);
	@Resource
	private SoaJmsDubboService soaJmsDubboService;
	@Value("${allocType.wareh.error}")
	private String allocTypeWarehError;
	@Value("${allocType.shopCode.error}")
	private String allocTypeShopCodeError;
	@Value("${allocType.unitId.error}")
	private String allocTypeUnitIdError;
	
	/**
	 * 
	 * @param vo SfSchTaskExecOosVo
	 * @return result
	 */
	public Map<String, Object> getAllocType(SfSchTaskExecOosVo vo){
 		Map<String, Object> result = new HashMap<String, Object>();
		// 根据发货仓库获取贸易供货方和上级组织
		String pDispWarehCode = vo.getDispWarehCode(); // 发货仓库
		String pRcvWarehCode = vo.getShopCode(); // 接收仓库
		vo.setUpDispWarehId(pDispWarehCode);
		vo.setUpRcvWarehId(pRcvWarehCode);
		Map<String, String> map = new HashMap<String, String>();
		map.put("unit_id", pDispWarehCode);
		SysUnitVo dispUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (dispUnit == null) {
			logger.error(MessageFormat.format(allocTypeWarehError, pDispWarehCode));
			result.put("alloc", AllocType.XXXO);
			return result;
		}
		// 根据收货仓库获取贸易供货方和上级组织
		map.put("unit_id", pRcvWarehCode);
		SysUnitVo rcvUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (rcvUnit == null) {
			logger.error(MessageFormat.format(allocTypeShopCodeError, pRcvWarehCode));
			result.put("alloc", AllocType.XXXO);
			return result;
		}
		
		String vender = dispUnit.getOwnerId();// 最终供货方
		String vendee = rcvUnit.getOwnerId();// 最终购货方
		result.put("lastVender", vender); // 预处理单据流里用到
		result.put("lastvendee", vendee);// 预处理单据流里用到
		
		//直营退货
		if (dispUnit.getOwnerId().equals(rcvUnit.getOwnerId()) && SRC_DOC_TYPE.IS_TH.equals(vo.getSrcDocType())){
			result.put("vendee", rcvUnit.getOwnerId());// 购货方
			result.put("vender", rcvUnit.getOwnerId());// 供货方和购货方相等
			result.put("alloc", AllocType.XXTH);
			return result;
		}
		
		//调拨（直配，配发给下属代理商门店） A01339S001(disp) - A01339 - A01339S011(rcv)
		if (dispUnit.getOwnerId().equals(rcvUnit.getOwnerId())) {
			result.put("vendee", rcvUnit.getOwnerId());// 购货方
			result.put("vender", rcvUnit.getOwnerId());// 供货方和购货方相等
			result.put("alloc", AllocType.XTFO);
			return result;
		}
		
		
		//直配 HQ01W001(disp) - HQ01 -　A00030 - A00030S001(rcv)
		if (dispUnit.getOwnerId().equals(rcvUnit.getCusId())) {
			result.put("vendee", vendee);// 购货方(A00030)
			result.put("vender", vender);// 供货方(HQ01)
			result.put("alloc", AllocType.XXXS);
			return result;
		}
		
		//调配 A00030S001(disp) - A00030 - HQ01 - HQ01W001(rcv)
		if (dispUnit.getCusId().equals(rcvUnit.getOwnerId())) {
			result.put("vendee", vendee);// 购货方(HQ01)
			result.put("vender", vender);// 供货方(A00030)
			result.put("alloc", AllocType.XXXT);
			return result;
		}
		
		//3方调配  HQ06W001(rcv) - HQ06 - HQ01 - A54320 -  A54320S001(disp)）
		if (dispUnit.getCusId().equals(rcvUnit.getCusId())) {
			result.put("vendee", rcvUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender", rcvUnit.getCusId());// 供货方(HQ01) ， 本次三方调配先走的是直配，然后调配
			result.put("upVendee", vender);// 最终供货方(A54320) 
			result.put("alloc", AllocType.XX3T);
			return result;
		}
		
		//根据RCV_OWNER_ID 获取收货仓库的上级组织的贸易供货方和上级组织
		map.put("unit_id", rcvUnit.getOwnerId());
		SysUnitVo rcvUpperUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (rcvUpperUnit == null) {
			logger.error(MessageFormat.format(allocTypeUnitIdError, rcvUnit.getOwnerId()));
			result.put("alloc", AllocType.XXXO);
			return result;
		}
		
		//转配 HQ01W001(disp) - HQ01 - HQ06 - A00045 - A00045S001(rcv)
		if (dispUnit.getOwnerId().equals(rcvUpperUnit.getCusId())) {
			result.put("vendee", rcvUnit.getOwnerId());// 购货方(A00045)
			result.put("vender", rcvUnit.getCusId());// 供货方(HQ06)
			result.put("upVendee", dispUnit.getOwnerId());// 最终供货方(HQ01) 
			result.put("alloc", AllocType.XX3Z);
			return result;
		}
		
		//根据RCV_CUS_ID 获取接收仓库的上级组织的贸易供货方
		map.put("unit_id", rcvUnit.getCusId());
		SysUnitVo rcvCusUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (rcvCusUnit == null) {
			logger.error(MessageFormat.format(allocTypeUnitIdError, rcvUnit.getCusId()));
			result.put("alloc", AllocType.XXXO);
			return result;
		}
		
		//直配+转配 HQ01W001(disp)-HQ01-HQ06-A00045-A00051-A00051S001(rcv)
		if (dispUnit.getOwnerId().equals(rcvCusUnit.getCusId())) {
			// 直配队列
			result.put("vendee", rcvUnit.getOwnerId());// 购货方(A00051)
			result.put("vender", rcvUnit.getCusId());// 供货方(A00045)
			// 转配队列
			result.put("vendee1", rcvUnit.getCusId());// 购货方(A00045)
			result.put("vender1", rcvCusUnit.getOwnerId());// 供货方(HQ06)
			result.put("upVendee1", dispUnit.getOwnerId());// 供货方(HQ01)
			result.put("alloc", AllocType.XS3Z);
			return result;
		}
		
		//三方调配+直配	A00065S001(rcv)-A00065-A00042-HQ01-A0030-A0030S001(disp)
		if (rcvCusUnit.getOwnerId().equals(dispUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(HQ01)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A0030)
			// 转配队列 
			result.put("vendee1", rcvUnit.getOwnerId());// 购货方(A00065)
			result.put("vender1", rcvUnit.getCusId());// 供货方(A00042)
			result.put("upVendee1", dispUnit.getCusId());// 最终供货方(HQ01) 
			result.put("alloc", AllocType.XT3Z);
			return result;
		}
		
		//三方调配+转配	A00065S001(rcv)-A00065-A00042-HQ06-HQ01-A0030-A0030S001(disp)
		if (rcvCusUnit.getCusId().equals(dispUnit.getCusId())) {
			// 三方调配队列 
			result.put("vendee", rcvCusUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender", dispUnit.getCusId());// 供货方(HQ01)
			result.put("upVendee", dispUnit.getOwnerId());// 最终供货方(A0030) 
			// 转配队列
			result.put("vendee1", rcvUnit.getOwnerId());// 购货方(A00065)
			result.put("vender1", rcvUnit.getCusId());// 供货方(A00042)
			result.put("upVendee1", rcvCusUnit.getOwnerId());//  最终供货方(HQ06) 
			result.put("alloc", AllocType.X3TZ);
			return result;
		}
		
		//根据DISP_WAREH_OWNER_ID 获取供货仓库的上级组织
		map.put("unit_id", dispUnit.getOwnerId());
		SysUnitVo dispOwnerUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (dispOwnerUnit == null) {
			logger.error(MessageFormat.format(allocTypeUnitIdError, dispUnit.getOwnerId()));
			result.put("alloc", AllocType.XXXO);
			return result;
		}
		
		// 调配+调配 HQ01W001(rcv)-HQ01-A0030-A00650-A0065S020(disp)
		if (dispOwnerUnit.getCusId().equals(rcvUnit.getOwnerId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A0030)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", rcvUnit.getOwnerId());// 购货方(HQ01)
			result.put("vender1", dispOwnerUnit.getOwnerId());// 供货方(A0030)
			result.put("alloc", AllocType.XXTT);
			return result;
		}
		
		//调配+三方调配  HQ06W001(rcv)-HQ06-HQ01-A0030-A00650-A0065S020(disp)
		if (dispOwnerUnit.getCusId().equals(rcvUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A0030)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 三方调配队列 
			result.put("vendee1", rcvUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender1", rcvUnit.getCusId());// 供货方(HQ01)
			result.put("upVendee1", dispUnit.getCusId());// 最终供货方(A0030)  
			result.put("alloc", AllocType.XT3T);
			return result;
		}
		
		//根据DISP_WAREH_CUS_ID 获取供货仓库上级组织的贸易供货方
		map.put("unit_id", dispUnit.getCusId());
		SysUnitVo dispCusUnit = soaJmsDubboService.selectCusIDAndOwnerID(map);
		if (dispCusUnit == null) {
			logger.error(MessageFormat.format(allocTypeUnitIdError, dispUnit.getCusId()));
			result.put("alloc", AllocType.XXXO);
			return result;
		}

		// 调配+调配+调配	HQ01W001(rcv)-HQ01-A0030-A00042-A00650-A0065S020(disp)
		if (rcvUnit.getOwnerId().equals(dispCusUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A00042)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", dispCusUnit.getOwnerId());// 购货方(A0030)
			result.put("vender1", dispUnit.getCusId());// 供货方(A00042)
			// 调配队列 
			result.put("vendee2", rcvUnit.getOwnerId());// 购货方(HQ01)
			result.put("vender2", dispCusUnit.getOwnerId());// 供货方(A0030)
			result.put("alloc", AllocType.XTTT);
			return result;
		}
		
		// 调配+调配+三方调配	HQ06W001(rcv)-HQ06-HQ01-A0030-A00042-A00650-A0065S020(disp)
		if (rcvUnit.getCusId().equals(dispCusUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A00042)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", dispCusUnit.getOwnerId());// 购货方(A0030)
			result.put("vender1", dispUnit.getCusId());// 供货方(A00042)
			// 三方调配队列 
			result.put("vendee2", rcvUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender2", rcvUnit.getCusId());// 供货方(HQ01)
			result.put("upVendee2", dispCusUnit.getOwnerId());// 最终供货方(A0030)  
			result.put("alloc", AllocType.TT3T);
			return result;
		}

		// 调配+调配+转配	A00042S001(rcv)-A00042-HQ06-HQ01-A0030-A00650-A0065S020(disp)
		if (rcvCusUnit.getOwnerId().equals(dispCusUnit.getOwnerId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A0030)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", dispCusUnit.getOwnerId());// 购货方(HQ01)
			result.put("vender1", dispUnit.getCusId());// 供货方(A0030)
			// 转配队列 
			result.put("vendee2", rcvUnit.getOwnerId());// 购货方(A00042)
			result.put("vender2", rcvUnit.getCusId());// 供货方(HQ06)
			result.put("upVendee2", rcvCusUnit.getOwnerId());// 最终供货方(HQ01)  
			result.put("alloc", AllocType.XTTZ);
			return result;
		}
		//调配+三方调配+转配 和 调配+调配+转配  代码顺序不可以调换，否则组织关系会错误
		//调配+三方调配+转配	A00065S001(rcv)-A00065-A00042-HQ06-HQ01-A0030-A00650-A0065S020(disp)
		if (rcvCusUnit.getCusId().equals(dispOwnerUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A0030)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 三方调配队列 
			result.put("vendee1", rcvCusUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender1", dispOwnerUnit.getCusId());// 供货方(HQ01)
			result.put("upVendee1", dispUnit.getCusId());// 最终供货方(A0030) 
			// 转配队列
			result.put("vendee2", rcvUnit.getOwnerId());// 购货方(A00065)
			result.put("vender2", rcvUnit.getCusId());// 供货方(A00042)
			result.put("upVendee2", rcvCusUnit.getOwnerId());//  最终供货方(HQ06) 
			result.put("alloc", AllocType.T3TZ);
			return result;
		}

		// 调配+调配+三方调配+直配	A00080S001(rcv)-A00080-HQ06-HQ01-A0030-A00042-A00650-A0065S020(disp)
		if (rcvCusUnit.getOwnerId().equals(dispCusUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A00042)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", dispCusUnit.getOwnerId());// 购货方(A0030)
			result.put("vender1", dispUnit.getCusId());// 供货方(A00042)
			// 三方调配队列 
			result.put("vendee2", rcvUnit.getCusId());// 购货方(HQ06)
			result.put("vender2", rcvCusUnit.getOwnerId());// 供货方(HQ01)
			result.put("upVendee2", dispCusUnit.getOwnerId());// 最终供货方(A0030)  
			// 直配队列
			result.put("vendee3", rcvUnit.getOwnerId());// 购货方(A00080)
			result.put("vender3", rcvUnit.getCusId());// 供货方(HQ06)
			result.put("alloc", AllocType.TT3TS);
			return result;
		}

		// 调配+调配+三方调配+转配	A00090S001(rcv)-A00090-A00080-HQ06-HQ01-A0030-A00042-A00650-A0065S020(disp)
		if (rcvCusUnit.getCusId().equals(dispCusUnit.getCusId())) {
			// 调配队列
			result.put("vendee", dispUnit.getCusId());// 购货方(A00042)
			result.put("vender", dispUnit.getOwnerId());// 供货方(A00650)
			// 调配队列
			result.put("vendee1", dispCusUnit.getOwnerId());// 购货方(A0030)
			result.put("vender1", dispUnit.getCusId());// 供货方(A00042)
			// 三方调配队列 
			result.put("vendee2", rcvCusUnit.getOwnerId());// 购货方(HQ06)
			result.put("vender2", rcvCusUnit.getCusId());// 供货方(HQ01)
			result.put("upVendee2", dispCusUnit.getOwnerId());// 最终供货方(A0030)  TODO 如果是先走两方调配，那么保存的就是最终购货方（上同）
			// 转配队列 
			result.put("vendee3", rcvUnit.getOwnerId());// 购货方(A00090)
			result.put("vender3", rcvUnit.getCusId());// 供货方(A00080)
			result.put("upVendee3", rcvCusUnit.getOwnerId());// 最终供货方(HQ06)  TODO 如果是先走两方调配，那么保存的就是最终购货方（上同）
			result.put("alloc", AllocType.TT3TZ);
			return result;
		}
		result.put("alloc", AllocType.XXXO);
		return result;
	}
	
}
