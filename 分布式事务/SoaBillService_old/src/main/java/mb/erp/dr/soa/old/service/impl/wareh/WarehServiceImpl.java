package mb.erp.dr.soa.old.service.impl.wareh;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.bean.ProdBean;
import mb.erp.dr.soa.bean.WarehBean;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.AdnMapper;
import mb.erp.dr.soa.old.dao.GdnMapper;
import mb.erp.dr.soa.old.dao.GrnMapper;
import mb.erp.dr.soa.old.dao.TbnMapper;
import mb.erp.dr.soa.old.dao.WarehMapper;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizDtlVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.old.vo.WarehCommitedTranVo;
import mb.erp.dr.soa.old.vo.WarehOrderTranVo;
import mb.erp.dr.soa.old.vo.WarehProdVo;
import mb.erp.dr.soa.old.vo.WarehTranVo;
import mb.erp.dr.soa.old.vo.Warehouse;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 库存服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括查询虚拟库存，增加减少实物库存、货位库存、可分配库存等的库存相关操作
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehServiceImpl
 * @since       全流通改造
 */
@Service
public class WarehServiceImpl implements WarehService {
	
	private final Logger logger = LoggerFactory.getLogger(WarehServiceImpl.class);

	final String WAREH_TRAN_ID = "WAREH_TRAN_ID"; // 仓库事务表 流水号获取：WAREH_TRAN_ID
	final String LOC_TRAN_ID = "LOC_TRAN_ID"; // 货位事务表
	final String WAREH_COMMIT_TRANID = "WAREH_COMMIT_TRANID"; // 已分配库存事务表 (本表为本次新加表) 流水号：WAREH_COMMIT_TRANID
	final String WAREH_ORDER_TRAN = "WAREH_ORDER_TRAN"; // 在购库存事务表 (本表为本次新加表) 流水号：WAREH_ORDER_TRAN
	@Resource
	private CommonService commonService;
	@Resource
	private WarehMapper warehMapper;
	@Resource
	private GdnMapper gdnMapper;
	@Resource
	private GrnMapper grnMapper;
	@Resource
	private AdnMapper adnMapper;
	@Resource
	private TbnMapper tbnMapper;
	
	@Value("${wareh.param.null}")
	private String  warehParamNull;
    
 	//实物库存 TODO 实物库存
	/**
	 * 增加库存	给仓库增加商品库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseQty(WarehBean warehBean){
		MsgVo msg = validate(warehBean, false,true);
		convertProdBeanList(warehBean,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
		Integer updateResult = warehMapper.updateWarehProdQty();
		// 可能有部分商品以前没有入过库
		Integer insertResult = warehMapper.insertWarehProdQty();
		
		saveWarehtran(warehBean);// 保存仓库事务
		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
		return msg;
	}
	/**
	 * 扣减库存	给仓库扣减商品库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceQty(WarehBean warehBean){
		MsgVo msg = validate(warehBean, false,true);
		convertProdBeanList(warehBean,true);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
		Integer updateResult = warehMapper.updateWarehProdQty();
		// 可能有部分商品以前没有入过库
		Integer insertResult = warehMapper.insertWarehProdQty();
		
		saveWarehtran(warehBean);// 保存仓库事务
		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
		return msg;
	}
	/**
	 * 根据入库单增加库存
	 * @param warehBean
	 * @return msgVo
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public MsgVo increaseQtyByGRN(GrnVo grnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(grnVo.getUnitId());
		warehBean.setGrnNum(grnVo.getGrnNum());
		warehBean.setWarehId(grnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true); // 是否增加库存
		
		// 更新仓库库存
		warehMapper.updateWarehProdByGrn(warehBean);
		warehMapper.insertWarehProdByGrn(warehBean);
		// 保存仓库事务，保存事务在插入语句之前，否则计算余额有偏差
		saveWarehTranByDoc(warehBean,false,BillType.GRN);
		return msg;
	}
	/**
	 * 根据入库单扣减库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceQtyByGRN(GrnVo grnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(grnVo.getUnitId());
		warehBean.setGrnNum(grnVo.getGrnNum());
		warehBean.setWarehId(grnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		
		// 更新仓库库存
		warehMapper.updateWarehProdByGrn(warehBean);
		warehMapper.insertWarehProdByGrn(warehBean);
		// 保存仓库事务
		saveWarehTranByDoc(warehBean,true,BillType.GRN);
		return msg;
	}
	/**
	 * 根据出库单增加库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseQtyByGDN(GdnVo gdnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setGdnNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true); // 是否增加库存
		
		// 更新仓库库存
		warehMapper.updateWarehProdByGdn(warehBean);
		warehMapper.insertWarehProdByGdn(warehBean);
		// 保存仓库事务
		saveWarehTranByDoc(warehBean,false,BillType.GDN);
		return msg;
	}
	/**
	 * 根据出库单扣减库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceQtyByGDN(GdnVo gdnVo)  {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setGdnNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false); // 是否增加库存
		// 更新仓库库存
		warehMapper.updateWarehProdByGdn(warehBean);
		warehMapper.insertWarehProdByGdn(warehBean);
		
		// 保存仓库事务
		saveWarehTranByDoc(warehBean,true,BillType.GDN);
		return msg;
	}
	// ----------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------
	//货位库存 TODO 货位库存
//	/**
//	 * 增加货位库存	给仓库增加商品货位库存
//	 * @param warehBean
//	 * @return msgVo
//	 */
//	public MsgVo increaseLocQty(WarehBean warehBean){
//		MsgVo msg = validate(warehBean, true,true);
//		convertProdBeanList(warehBean,false);
//		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
//			throw new RuntimeException(msg.getMsg());
////			return msg;
//		}
//		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
//		Integer updateResult = warehMapper.updateLocProdQty();
//		// 可能有部分商品以前没有入过库
//		Integer insertResult = warehMapper.insertLocProdQty();
//		
//		saveLocTran(warehBean);// 保存仓库事务
//		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
//		return msg;
//	}
//	/**
//	 * 扣减货位库存	给仓库扣减商品货位库存
//	 * @param warehBean
//	 * @return msgVo
//	 */
//	public MsgVo reduceLocQty(WarehBean warehBean){
//		MsgVo msg = validate(warehBean, true,true);
//		convertProdBeanList(warehBean,true);
//		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
//			throw new RuntimeException(msg.getMsg());
////			return msg;
//		}
//		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
//		Integer updateResult = warehMapper.updateLocProdQty();
//		// 可能有部分商品以前没有入过库
//		Integer insertResult = warehMapper.insertLocProdQty();
//		
//		saveLocTran(warehBean);// 保存仓库事务
//		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
//		return msg;
//	}
//	
	/**
	 * 根据出库单增加货位库存
	 * @param warehBean
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseLocQtyByGDN(GdnVo gdnVo) {
		return modifyLocQtyByGDN(gdnVo,true);
	}
	/**
	 * 根据出库单扣减货位库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGDN(GdnVo gdnVo) {
		return modifyLocQtyByGDN(gdnVo,false);
	}
	
	/**
	 * 根据入库单增加货位库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseLocQtyByGRN(GrnVo grnVo) {
		return modifyLocQtyByGRN(grnVo,true);
	}
	/**
	 * 根据入库单扣减货位库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGRN(GrnVo grnVo) {
		return modifyLocQtyByGRN(grnVo,false);
	}
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//已分配库存 TODO 已分配库存
	/**
	 * 增加已分配库存	给仓库增加商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseCommitQty(WarehBean warehBean){
		MsgVo msg = validate(warehBean, false,true);
		convertProdBeanList(warehBean,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
		Integer updateResult = warehMapper.updateCommitTranProdQty();
		Integer insertResult = warehMapper.insertCommitTranProdQty();
		
		saveWarehCommitedTran(warehBean);// 保存已分配库存事务
		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
		return msg;
	}
	/**
	 * 扣减已分配库存	给仓库扣减商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceCommitQty(WarehBean warehBean){
		MsgVo msg = validate(warehBean, false,true);
		convertProdBeanList(warehBean,true);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Integer saveTemp = warehMapper.saveToTempTable(warehBean.getProdList());
		Integer updateResult = warehMapper.updateCommitTranProdQty();
		Integer insertResult = warehMapper.insertCommitTranProdQty();
		
		saveWarehCommitedTran(warehBean);// 保存已分配库存事务
		logger.info("-------------------increaseQty:"+saveTemp+"@"+updateResult+"@"+insertResult);
		return msg;
	}
	/**
	 * 根据计划配货单增加已分配库存	按计划配货单给仓库增加商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public MsgVo increaseCommitQtyByADN(AdnVo adnVo) {
		WarehBean warehBean = new WarehBean();
    	warehBean.setUnitId(adnVo.getVenderId());
    	warehBean.setAdnNum(adnVo.getAdnNum());
    	warehBean.setWarehId(adnVo.getWarehId());
    	// 配货单保存的时候，就已经验证过商品id，此处略过
//    	List<ProdBean> prodList = new ArrayList<ProdBean>();
//            for(AdnDtlVo vo : adnVo.getAdnDtlVos()){
//                ProdBean bean = new ProdBean();
//                bean.setProdId(vo.getProdId());
//                prodList.add(bean);
//            }
//        warehBean.setProdList(prodList);
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true);
		// 根据计划配货单更新已分配库存
		warehMapper.updateWarehCommitProdByAdn(warehBean);
		warehMapper.insertWarehCommitProdByAdn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,false,BillType.ADN);
		return msg;
	}
	/**
	 * 根据计划配货单扣减已分配库存	按计划配货单给仓库扣减商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceCommitQtyByADN(AdnVo adnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(adnVo.getVenderId());
		warehBean.setAdnNum(adnVo.getAdnNum());
		warehBean.setWarehId(adnVo.getWarehId()); //仓库编码
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		
		// 根据计划配货单更新已分配库存
		warehMapper.updateWarehCommitProdByAdn(warehBean);
		warehMapper.insertWarehCommitProdByAdn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,true,BillType.ADN);
		return msg;
	}
	/**
	 * 根据调配单给仓库增加商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseCommitQtyByTBN(TbnVo tbnVo) {
		WarehBean warehBean = new WarehBean();
        warehBean.setUnitId(tbnVo.getVenderId());
        warehBean.setTbnNum(tbnVo.getTbnNum());
        warehBean.setWarehId(tbnVo.getDispWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true);
		
		// 根据调配单更新已分配库存
		warehMapper.updateWarehCommitProdByTbn(warehBean);
		warehMapper.insertWarehCommitProdByTbn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,false,BillType.TBN);
		return msg;
	}
	/**
	 * 根据调配单给仓库扣减商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceCommitQtyByTBN(TbnVo tbnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(tbnVo.getVenderId());
		warehBean.setTbnNum(tbnVo.getTbnNum());
		warehBean.setWarehId(tbnVo.getDispWarehId()); // 发货仓库编码
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		
		// 根据调配单更新已分配库存
		warehMapper.updateWarehCommitProdByTbn(warehBean);
		warehMapper.insertWarehCommitProdByTbn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,true,BillType.TBN);
		return msg;
	}
	/**
	 * 根据出库单给仓库增加商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseCommitQtyByGDN(GdnVo gdnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setGdnNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true);
		
		// 根据出库单更新已分配库存
		warehMapper.updateWarehCommitProdByGdn(warehBean);
		warehMapper.insertWarehCommitProdByGdn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,false,BillType.GDN);
		return msg;
	}
	/**
	 * 根据出库单给仓库扣减商品已分配库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceCommitQtyByGDN(GdnVo gdnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setGdnNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		
		// 根据出库单更新已分配库存
		warehMapper.updateWarehCommitProdByGdn(warehBean);
		warehMapper.insertWarehCommitProdByGdn(warehBean);
		// 保存已分配事务
		saveWarehCommitedTranByDoc(warehBean,true,BillType.GDN);
		return msg;
	}
	/**
	 * 根据计划配货单增加再购库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo addQtyOnOrderByAdn(AdnVo adnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(adnVo.getVenderId());
		warehBean.setAdnNum(adnVo.getAdnNum());;
		warehBean.setWarehId(adnVo.getTranRcvWarehId());
		warehBean.setTranDate(adnVo.getDocDate());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true);
		// 根据计划配货单更新在购库存
		warehMapper.updateWarehOrderProdByAdn(warehBean);
		warehMapper.insertWarehOrderProdByAdn(warehBean);
		// 保存在购库存事务
		saveWarehOrderTran(warehBean,false,BillType.ADN);
		return msg;
	}
	
	/**
	 * 根据计划配货单减少在购库存
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo decQtyOnOrderByAdn(AdnVo adnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(adnVo.getVenderId());
		warehBean.setAdnNum(adnVo.getAdnNum());;
		warehBean.setWarehId(adnVo.getTranRcvWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		// 根据计划配货单更新在购库存
		warehMapper.updateWarehOrderProdByAdn(warehBean);
		warehMapper.insertWarehOrderProdByAdn(warehBean);
		// 保存在购库存事务
		saveWarehOrderTran(warehBean,true,BillType.ADN);
		return msg;
	}
	
	/**
	 * 根据出库单给仓库增加在途库存(调拨)
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseTransitQtyByGDN(GdnVo gdnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setGdnNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getRcvWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(true);
		
		// 根据出库单更新在途库存
		warehMapper.updateWarehTransitByGdn(warehBean);
		warehMapper.insertWarehTransitByGdn(warehBean);
		return msg;
	}
	/**
	 * 根据入库单给仓库扣减在途库存(调拨)
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceTransitQtyByGRN(GrnVo grnVo) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(grnVo.getUnitId());
		warehBean.setGrnNum(grnVo.getGrnNum());
		warehBean.setWarehId(grnVo.getWarehId());
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		warehBean.setIsIncrease(false);
		
		// 根据入库单更新在途库存
		warehMapper.updateWarehTransitByGrn(warehBean);
		warehMapper.insertWarehTransitByGrn(warehBean);
		return msg;
	}
	
	/**
	 * 根据入库单更新货位库存
	 * @param vo
	 * @param isAdd true:增加 false:减少
	 * @return
	 */
	public MsgVo modifyLocQtyByGRN(GrnVo grnVo, boolean isAdd) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(grnVo.getUnitId());
		warehBean.setDocNum(grnVo.getGrnNum());
		warehBean.setWarehId(grnVo.getWarehId());
		warehBean.setDocType(BillType.GRN);
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Warehouse warehouse = warehMapper.searchWarehouseInfo(warehBean.getWarehId());
		if (warehouse == null) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("仓库不存在，仓库编码:"+warehBean.getWarehId());
			throw new RuntimeException(msg.getMsg());
		}
		String flag = warehouse.getLocAdopted(); // 是否启用货位
		if (!O2OBillConstant.TORF.TRUE.equals(flag)) {
			msg.setMsg("仓库没有启用货位管理，仓库编码:"+warehBean.getWarehId());
			logger.warn(msg.getMsg());
			return msg;
		}
		String defultLocId = "";
		List<GrnDtlVo> list = grnMapper.selectGrnDtlWithOutLocId(grnVo);
		//按指定货位更新货位库存
		if(list == null || list.size() == 0){
			warehBean.setLocId(defultLocId);
			boolean isSuccess = updateLocQtyByBiz(warehBean,isAdd);
			if (!isSuccess){
				throw new RuntimeException("更新货位数量失败");
			} else {
				return msg;
			}
		}
		defultLocId = warehouse.getRcptLocId(); // 默认收货货位
		if (SoaBillUtils.isBlank(defultLocId)) {
			throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理,请设置收货货位", warehBean.getWarehId()));
		}
		
		warehBean.setLocId(defultLocId);
//		Long temp = warehMapper.selectLocByWarehIdAndLocId(warehBean);
//		if (temp == null || temp != 1) {
//			throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理,默认收货货位不存在", warehBean.getWarehId()));
//		} 
		boolean isSuccess = updateLocQtyByBiz(warehBean,isAdd);
		if (!isSuccess) throw new RuntimeException("更新货位数量失败");
		return msg;
	}
	
	/**
	 * 根据出库单更新货位库存
	 * @param gdnVo
	 * @param isAdd true:增加 false:减少
	 * @return MsgVo
	 */
	public MsgVo modifyLocQtyByGDN(GdnVo gdnVo, boolean isAdd) {
		WarehBean warehBean = new WarehBean();
		warehBean.setUnitId(gdnVo.getUnitId());
		warehBean.setDocNum(gdnVo.getGdnNum());
		warehBean.setWarehId(gdnVo.getWarehId());
		warehBean.setDocType(BillType.GDN);
		MsgVo msg = validate(warehBean, false,false);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		Warehouse warehouse = warehMapper.searchWarehouseInfo(warehBean.getWarehId());
		if (warehouse == null) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("仓库不存在，仓库编码:"+warehBean.getWarehId());
			throw new RuntimeException(msg.getMsg());
		}
		String flag = warehouse.getLocAdopted(); // 是否启用货位
		if (!O2OBillConstant.TORF.TRUE.equals(flag)) {
			msg.setMsg("仓库没有启用货位管理，仓库编码:"+warehBean.getWarehId());
			logger.warn(msg.getMsg());
			return msg;
		}
		String defultLocId = "";
		List<GdnDtlVo> list = gdnMapper.selectGdnDtlWithOutLocId(gdnVo);
		//按指定货位更新货位库存
		if(list == null || list.size() == 0){
			warehBean.setLocId(defultLocId);
			boolean isSuccess = updateLocQtyByBiz(warehBean,isAdd);
			if (!isSuccess){
				throw new RuntimeException("更新货位数量失败");
			} else {
				return msg;
			}
		}
		
		defultLocId = warehouse.getDispLocId(); // 默认发货货位
		if (SoaBillUtils.isBlank(defultLocId)) {
			throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理，请设置发货货位", warehBean.getWarehId()));
		}
		warehBean.setLocId(defultLocId);
//		Long temp = warehMapper.selectLocByWarehIdAndLocId(warehBean);
//		if (temp == null || temp != 1) {
//			throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理,默认发货货位不存在", warehBean.getWarehId()));
//		} 
		boolean isSuccess = updateLocQtyByBiz(warehBean,isAdd);
		if (!isSuccess) throw new RuntimeException("更新货位数量失败");
		
		return msg;
	}
	
	/**
	 * 保存在购库存事务
	 * @param warehBean
	 * @param flag 是否减少库存，true：减少库存，false：增加库存
	 * @param DocType 单据类型
	 * @return
	 */
	private void saveWarehOrderTran(WarehBean warehBean ,boolean flag ,BillType docType) {
		// 查询单单据明细 bean
		OrderSearchBean orderSearchBean = new OrderSearchBean();
		orderSearchBean.setUnitId(warehBean.getUnitId());
		orderSearchBean.setDocNum(warehBean.getAdnNum());
		List<AdnDtlVo> adnDtlVos = adnMapper.selectDtl(orderSearchBean);
		if (adnDtlVos == null || adnDtlVos.size() <= 0){
			throw new RuntimeException("计划配货单明细不存在，单据号："+warehBean.getAdnNum());
		}
		List<WarehOrderTranVo> orderTranVoList = new ArrayList<WarehOrderTranVo>();
		for(AdnDtlVo dtlVo : adnDtlVos) {
			String prodId = dtlVo.getProdId();
			Double quantity = dtlVo.getAdmQty();
			if (flag) {
				quantity *= -1;
			}
			WarehOrderTranVo orderTranVo  = new WarehOrderTranVo();
			String primaryKey = commonService.getPrimaryKey(warehBean.getWarehId(), WAREH_ORDER_TRAN);
			Long orderTranId = Long.parseLong(primaryKey);
			orderTranVo.setWarehId(warehBean.getWarehId());
			orderTranVo.setOrderTranId(orderTranId);
			orderTranVo.setDocNum(warehBean.getAdnNum());
			orderTranVo.setDocType(docType);
			orderTranVo.setProdId(prodId);
			orderTranVo.setTranQty(quantity);
			
			// 已分配库存余额计算
			ProdBean searchProdBean = new ProdBean();
			searchProdBean.setWarehId(warehBean.getWarehId());
			searchProdBean.setProdId(prodId);
			WarehProdVo warehProdInfo = warehMapper.searchWarehInfo(searchProdBean);
			Double tempQuantity = warehProdInfo == null?0d:warehProdInfo.getQtyOnOrder();
			orderTranVo.setBalance(tempQuantity);// 已分配库存余额
			orderTranVoList.add(orderTranVo);
		}
		warehMapper.saveWarehOrderTran(orderTranVoList);
	}
	/**
	 * 保存仓库事务
	 * @param fsclAc
	 * @return
	 */
	private void saveWarehtran(WarehBean warehBean){
		List<WarehTranVo> warehTranVos = new ArrayList<WarehTranVo>();
		for (ProdBean prodBean : warehBean.getProdList()) {
			Long warehTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), WAREH_TRAN_ID));
			// System.out.println("++++++++++++++++saveWarehtran："+warehTranId);
			WarehTranVo warehTranVo = new WarehTranVo();
			warehTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
			warehTranVo.setWarehTranId(warehTranId);// 仓库事务编号
			warehTranVo.setProdId(prodBean.getProdId());// 商品编码(SKU)
			warehTranVo.setTranDate(new Date());// 发生日期
			warehTranVo.setDocType(warehBean.getDocType());// 单据类型
			warehTranVo.setDocNum(warehBean.getDocNum());// 单据编码
			warehTranVo.setTranQty(prodBean.getQty());// 发生数量
			warehTranVo.setBalance(1d);// 库存余额
			warehTranVos.add(warehTranVo);
		}
		warehMapper.saveWarehTran(warehTranVos);
	}
	
	/**
	 * 保存仓库事务-根据出（入）库单
	 * @param warehBean
	 * @param flag 是否减少库存，true：减少库存，false：增加库存
	 * @param DocType 单据类型
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	private void saveWarehTranByDoc(WarehBean warehBean,Boolean flag,BillType DocType) {
		List<? extends BaseBizDtlVo> dtlVos = null;
		Class<? extends BaseBizDtlVo> clazz = null;
		// 查询出（入）库单详情 bean
		OrderSearchBean orderSearchBean = new OrderSearchBean();
		orderSearchBean.setUnitId(warehBean.getUnitId());
		String docNum = null;
		if (BillType.GDN.equals(DocType)) {
			clazz = GdnDtlVo.class;
			docNum = warehBean.getGdnNum();
			orderSearchBean.setDocNum(docNum);
			dtlVos = gdnMapper.searchGdnDtl(orderSearchBean);
		}else if (BillType.GRN.equals(DocType)) {
			clazz = GrnDtlVo.class;
			docNum = warehBean.getGrnNum();
			orderSearchBean.setDocNum(docNum);
			dtlVos = grnMapper.searchGrnDtl(orderSearchBean);
		}
		List<WarehTranVo> warehTranVos = new ArrayList<WarehTranVo>();
		for (Object prodBean : dtlVos) {
			String prodId = null;
			Double quantity = 0d;
			try {
				prodId = (String) clazz.getMethod("getProdId").invoke(prodBean);
				quantity = (Double) clazz.getMethod("getQuantity").invoke(prodBean);
			} catch (Exception e) {
				logger.error("保存仓库事务时，取不到指定的商品id和数量"+e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			if (flag) { // 减少库存时，保存进事务的是负数
				quantity *= -1;
			}
			Long warehTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), WAREH_TRAN_ID));
			logger.warn("--------------------saveWarehTran->warehTranId : "+warehTranId+" , WarehId : "+warehBean.getWarehId()+" , prodId : "+prodId+" , quantity : "+quantity+" , docType : "+DocType+" , docNum : "+docNum);
			WarehTranVo warehTranVo = new WarehTranVo();
			warehTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
			warehTranVo.setWarehTranId(warehTranId);// 仓库事务编号
			warehTranVo.setProdId(prodId);// 商品编码(SKU)
			warehTranVo.setTranDate(new Date());// 发生日期
			warehTranVo.setDocType(DocType);// 单据类型
			warehTranVo.setDocNum(docNum);// 单据编码
			warehTranVo.setTranQty(quantity);// 发生数量
			// 实物库存余额计算
			ProdBean searchProdBean = new ProdBean();
			searchProdBean.setWarehId(warehBean.getWarehId());
			searchProdBean.setProdId(prodId);
			WarehProdVo warehProdInfo = warehMapper.searchWarehInfo(searchProdBean);
			Double tempQuantity = warehProdInfo == null?0d:warehProdInfo.getStkOnHand();
			warehTranVo.setBalance(tempQuantity);// 实物库存余额
			warehTranVos.add(warehTranVo);
		}
		warehMapper.saveWarehTran(warehTranVos);
	}
	
//	/**
//	 * 保存货位事务
//	 * @param fsclAc
//	 * @return
//	 */
//	private void saveLocTran(WarehBean warehBean){
//		List<LocTranVo> locTranVos = new ArrayList<LocTranVo>();
//		for (ProdBean prodBean : warehBean.getProdList()) {
//			Long locTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), LOC_TRAN_ID));
//			// System.out.println("++++++++++++++++saveLocTran："+locTranId);
//			StkDtlVo vo = warehMapper.searchStkDtl(prodBean);
//			LocTranVo locTranVo = new LocTranVo();
//			locTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
//			locTranVo.setLocTranId(locTranId);// 仓库事务编号
//			locTranVo.setLocId(warehBean.getLocId()); // 货位
//			locTranVo.setProdId(prodBean.getProdId());// 商品编码(SKU)
//			locTranVo.setTranDate(new Date());// 发生日期
//			locTranVo.setTranTime(new Date());
//			locTranVo.setDocType(warehBean.getDocType());// 单据类型
//			locTranVo.setDocNum(warehBean.getDocNum());// 单据编码
//			locTranVo.setTranQty(prodBean.getQty());// 发生数量
//			locTranVo.setBalance(vo.getStkOnHand());// 库存余额
//			locTranVos.add(locTranVo);
//		}
//		warehMapper.saveLocTran(locTranVos);
//	}
	
//	/**
//	 * 保存货位事务-根据出（入）库单
//	 * @param warehBean
//	 * @param flag 是否减少库存，true：减少库存，false：增加库存
//	 * @param DocType 单据类型
//	 * @return
//	 * @throws NoSuchMethodException 
//	 * @throws InvocationTargetException 
//	 * @throws IllegalAccessException 
//	 * @throws SecurityException 
//	 * @throws IllegalArgumentException 
//	 */
//	private void saveLocTranByDoc(WarehBean warehBean,Boolean flag,BillType DocType) {
//		List<? extends BaseBizDtlVo> dtlVos = null;
//		Class<? extends BaseBizDtlVo> clazz = null;
//		// 查询出（入）库单详情 bean
//		OrderSearchBean orderSearchBean = new OrderSearchBean();
//		orderSearchBean.setUnitId(warehBean.getUnitId());
//		String docNum = null;
//		if (BillType.GDN.equals(DocType)) {
//			clazz = GdnDtlVo.class;
//			docNum = warehBean.getGdnNum();
//			orderSearchBean.setDocNum(docNum);
//			dtlVos = gdnMapper.searchGdnDtl(orderSearchBean);
//		}else if (BillType.GRN.equals(DocType)) {
//			clazz = GrnDtlVo.class;
//			docNum = warehBean.getGrnNum();
//			orderSearchBean.setDocNum(docNum);
//			dtlVos = grnMapper.searchGrnDtl(orderSearchBean);
//		}
//		List<LocTranVo> locTranVos = new ArrayList<LocTranVo>();
//		for (Object prodBean : dtlVos) {
//			String prodId= null,locId = null;
//			Double quantity = 0d;
//			try {
//				locId = (String) clazz.getMethod("getLocId").invoke(prodBean);
//				prodId = (String) clazz.getMethod("getProdId").invoke(prodBean);
//				quantity = (Double) clazz.getMethod("getQuantity").invoke(prodBean);
//			} catch (Exception e) {
//				logger.error("保存货位事务时，取不到指定的商品id和数量"+e.getMessage());
//				throw new RuntimeException(e.getMessage());
//			}
//			if (flag) { // 减少库存时，保存进事务的是负数
//				quantity *= -1;
//			}
//			Long locTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), LOC_TRAN_ID));
//			LocTranVo locTranVo = new LocTranVo();
//			locTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
//			locTranVo.setLocTranId(locTranId);// 仓库事务编号
//			if (SoaBillUtils.isNotBlank(locId)) {
//				locTranVo.setLocId(locId); // 货位
//			}else {
//				locTranVo.setLocId(warehBean.getLocId()); // 货位
//			}
//			logger.warn("--------------------saveLocTran->locTranId : "+locTranId+" , LocId : "+locTranVo.getLocId()+" , prodId : "+prodId+" , quantity : "+quantity+" , docType : "+DocType+" , docNum : "+docNum);
//			locTranVo.setProdId(prodId);// 商品编码(SKU)
//			locTranVo.setTranDate(new Date());// 发生日期
//			locTranVo.setDocType(DocType);// 单据类型
//			locTranVo.setDocNum(docNum);// 单据编码
//			locTranVo.setTranQty(quantity);// 发生数量
//			// 货位库存余额计算
//			ProdBean searchProdBean = new ProdBean();
//			searchProdBean.setWarehId(warehBean.getWarehId());
//			searchProdBean.setProdId(prodId);
//			searchProdBean.setLocId(warehBean.getLocId());
//			StkDtlVo stkDtlVo = warehMapper.searchStkDtl(searchProdBean);// 查询货位库存
//			Double tempQuantity = stkDtlVo == null?0d:stkDtlVo.getStkOnHand();
//			locTranVo.setBalance(tempQuantity+quantity);// 货位库存余额
//			locTranVos.add(locTranVo);
//		}
//		warehMapper.saveLocTran(locTranVos);
//	}
	
	/**
	 * 已分配库存事务
	 * @param fsclAc
	 * @return
	 */
	private void saveWarehCommitedTran(WarehBean warehBean){
		List<WarehCommitedTranVo> warehCommitTranVos = new ArrayList<WarehCommitedTranVo>();
		for (ProdBean prodBean : warehBean.getProdList()) {
			Long warehTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), WAREH_COMMIT_TRANID));
			// System.out.println("++++++++++++++++saveWarehCommitedTran："+warehTranId);
			WarehCommitedTranVo warehCommitedTranVo = new WarehCommitedTranVo();
			warehCommitedTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
			warehCommitedTranVo.setCommitedTranId(warehTranId);// 事务编号
			warehCommitedTranVo.setProdId(prodBean.getProdId());// 商品编码
			warehCommitedTranVo.setTranDate(new Date());// 发生日期
			warehCommitedTranVo.setDocType(warehBean.getDocType());// 单据类型
			warehCommitedTranVo.setDocNum(warehBean.getDocNum());// 单据编码
			warehCommitedTranVo.setTranQty(prodBean.getQty());// 发生数量
			warehCommitedTranVo.setBalance(1d);// 库存余额
			warehCommitTranVos.add(warehCommitedTranVo);
		}
		warehMapper.saveWarehCommitTran(warehCommitTranVos);
	}
	
	/**
	 * 保存已分配事务-根据出库单，计划配货单，调配单
	 * @param warehBean
	 * @param flag 是否减少库存，true：减少库存，false：增加库存
	 * @param DocType 单据类型
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	private void saveWarehCommitedTranByDoc(WarehBean warehBean,Boolean flag,BillType DocType) {
		List<? extends BaseBizDtlVo> dtlVos = null;
		Class<? extends BaseBizDtlVo> clazz = null;
		// 查询单单据明细 bean
		OrderSearchBean orderSearchBean = new OrderSearchBean();
		orderSearchBean.setUnitId(warehBean.getUnitId());
		String docNum = null;
		if (BillType.GDN.equals(DocType)) {
			clazz = GdnDtlVo.class;
			docNum = warehBean.getGdnNum();
			orderSearchBean.setDocNum(docNum);
			dtlVos = gdnMapper.searchGdnDtl(orderSearchBean);
		}else if (BillType.TBN.equals(DocType)) {
			clazz = TbnDtlVo.class;
			docNum = warehBean.getTbnNum();
			orderSearchBean.setDocNum(docNum);
			dtlVos = tbnMapper.searchTbnDtl(orderSearchBean);
		}else if (BillType.ADN.equals(DocType)) {
			DocType = BillType.AAD;
			clazz = AdnDtlVo.class;
			docNum = warehBean.getAdnNum();
			orderSearchBean.setDocNum(docNum);
			dtlVos = adnMapper.selectDtl(orderSearchBean);
		}
		List<WarehCommitedTranVo> warehCommitTranVos = new ArrayList<WarehCommitedTranVo>();
		for (Object prodBean : dtlVos) {
			String prodId= null;
			Double quantity = 0d;
			try {
				prodId = (String) clazz.getMethod("getProdId").invoke(prodBean);
				if (clazz.equals(AdnDtlVo.class)){
					quantity = (Double) clazz.getMethod("getAdmQty").invoke(prodBean);
				}
				if (clazz.equals(TbnDtlVo.class)){
					quantity = (Double) clazz.getMethod("getExpdQty").invoke(prodBean);
				}
				if (clazz.equals(GdnDtlVo.class)) {
					quantity = (Double) clazz.getMethod("getQuantity").invoke(prodBean);
				}
			} catch (Exception e) {
				logger.error("保存货位事务时，取不到指定的商品id和数量"+e.getMessage());
				throw new RuntimeException(e.getMessage());
			}
			if (flag) { // 减少库存时，保存进事务的是负数
				quantity *= -1;
			}
			
			Long commitTranId = Long.parseLong(commonService.getPrimaryKey(warehBean.getWarehId(), WAREH_COMMIT_TRANID));
			logger.warn("--------------------saveWarehCommitedTran->commitTranId : "+commitTranId+" , WarehId : "+warehBean.getWarehId()+" , prodId : "+prodId+" , quantity : "+quantity+" , docType : "+DocType+" , docNum : "+docNum);
			WarehCommitedTranVo warehCommitedTranVo = new WarehCommitedTranVo();
			warehCommitedTranVo.setWarehId(warehBean.getWarehId());// 仓库编码
			warehCommitedTranVo.setCommitedTranId(commitTranId);// 仓库事务编号
			warehCommitedTranVo.setProdId(prodId);// 商品编码(SKU)
			warehCommitedTranVo.setTranDate(new Date());// 发生日期
			warehCommitedTranVo.setDocType(DocType);// 单据类型
			warehCommitedTranVo.setDocNum(docNum);// 单据编码
			warehCommitedTranVo.setTranQty(quantity);// 发生数量
			// 已分配库存余额计算
			ProdBean searchProdBean = new ProdBean();
			searchProdBean.setWarehId(warehBean.getWarehId());
			searchProdBean.setProdId(prodId);
			WarehProdVo warehProdInfo = warehMapper.searchWarehInfo(searchProdBean);
			Double tempQuantity = warehProdInfo == null?0d:warehProdInfo.getQtyCommitted();
			warehCommitedTranVo.setBalance(tempQuantity);// 已分配库存余额
			warehCommitTranVos.add(warehCommitedTranVo);
		}
		warehMapper.saveWarehCommitTran(warehCommitTranVos);
	}
	
	/**
	 * 空验证
	 * @param warehBean
	 * @param flag 是否检查货位编码
	 * @param prodCheckFlag 是否检查商品编码
	 * @return
	 */
	private String validateNull(WarehBean warehBean,boolean flag,boolean prodCheckFlag ){
		String result = null;
		if (SoaBillUtils.isBlank(warehBean.getWarehId()) ) {
			result = "仓库编码";
		}else if (flag && SoaBillUtils.isBlank(warehBean.getLocId())) {
			result = "货位编码";
		}
		if (warehBean.getProdList() != null && warehBean.getProdList().size() > 0) {
			for (ProdBean temp : warehBean.getProdList()) {
				if (temp == null || temp.getProdId() == null) {
					result = "商品编码(其中一条)";
					break;
				}
			}
		}
//		else if(prodCheckFlag){
//			result = "商品编码";
//		}
		if (result != null) {
			result = MessageFormat.format(warehParamNull, result);
		}
		return result;
	}
	
	/**
	 * 参数验证
	 * @param warehBean
	 * @param locFlag 是否验证货位编码啊
	 * @param prodCheckFlag 是否验证商品列表
	 * @return
	 */
	private MsgVo  validate(WarehBean warehBean,boolean locFlag,boolean prodCheckFlag){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		// 空验证
		String nullProperty = validateNull(warehBean,locFlag,prodCheckFlag);
		if (nullProperty != null) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			logger.error(nullProperty);
			return msg;
		}
//		// 存在性验证
//		String msgContent = null;
//		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
//		// 仓库编码
//		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
//		tDefVo2.setCode(warehBean.getWarehId());
//		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
//		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
//		TmpInvalidDefVo tDefVo2_1 = new TmpInvalidDefVo();
//		tDefVo2_1.setCode(warehBean.getWarehId());
//		tDefVo2_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
//		tDefVo2_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
//		
//		if (locFlag) {
//			// 货位编码
//			TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
//			tDefVo3.setCode(warehBean.getLocId());
//			tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
//			tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
//			TmpInvalidDefVo tDefVo3_1 = new TmpInvalidDefVo();
//			tDefVo3_1.setCode(warehBean.getLocId());
//			tDefVo3_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
//			tDefVo3_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
//		}
		
//		if (warehBean.getProdList() == null || warehBean.getProdList().size() == 0) {
//			msg.setMsg(prodIdListParamNull);
//			logger.error(msg.getMsg());
//			return msg;
//		}else {
//			for (ProdBean prodBean : warehBean.getProdList()) {
//				// 商品
//				TmpInvalidDefVo temp = new TmpInvalidDefVo();
//				temp.setCode(prodBean.getProdId());
//				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
//				temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
//				tmpInvalidDefVos.add(temp);
//				temp = new TmpInvalidDefVo();
//				temp.setCode(prodBean.getProdId());
//				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
//				temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
//				tmpInvalidDefVos.add(temp);
//			}
//		}
		
//		MsgVo  invalidMsg = invalidService.isValidity(tmpInvalidDefVos);
//		if (invalidMsg != null) {
//			msg.setMsg(msg.getMsg()+ "的参数填写有误。");
//			logger.error(msg.getMsg());
//			return msg;
//		}else {
//			List<TmpInvalidDefVo> list = invalidService.findInvalid(tmpInvalidDefVos);
//			TmpInvalidDefVo errorVo = null;
//			if (list!= null && list.size() > 0 ) {
//				errorVo = (TmpInvalidDefVo) list.get(0);
//					String codeType = errorVo.getType();
//					String errMsg = "";
//					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
//						errMsg = "组织码:"+errorVo.getCode();
//					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
//						errMsg = "品牌编码:"+errorVo.getCode();
//					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
//						errMsg = "商品编码:"+errorVo.getCode();
//					}
//					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
//						msgContent = MessageFormat.format(saveParamExist, "出库单", errMsg);
//					}else {
//						msgContent = MessageFormat.format(saveParamSta, "出库单", errMsg);
//					}
//			}
//		}
//		
//		// 存在性验证
//		if (msgContent != null) {
//			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
//			msg.setMsg(msgContent);
//			logger.error(msgContent);
//			return msg;
//		}
		
		return msg;
	}
	
	/**
	 * 改造List<ProdBean>，方便临时表做批量动作
	 * 根据单据增加库存的不需要走这一个方法
	 * @param warehBean
	 * @param flag 是否扣减库存 true:扣减库存 , false:增加库存
	 */
	private void convertProdBeanList(WarehBean warehBean,boolean flag){
		List<ProdBean> list = warehBean.getProdList();
		for (ProdBean temp : list) {
			temp.setLocId(warehBean.getLocId());
			temp.setWarehId(warehBean.getWarehId());
			if (flag) { // 如果是扣减库存，则需要减库存
				temp.setQty(temp.getQty()*-1); 
			}
		}
	}
	
	 /**
     * 更新库位数量
     * @param warehBean 单据信息
     * @param isAdd
     * @return
     */
    private Boolean updateLocQtyByBiz(WarehBean warehBean,Boolean isAdd){
        try{
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("isAdd", isAdd?1:0);
        	params.put("locId", warehBean.getLocId());
        	params.put("unitId",warehBean.getUnitId());
        	params.put("docNum", warehBean.getDocNum());
        	BillType bizType = warehBean.getDocType();
            if (BillType.GRN.equals(bizType)){
                warehMapper.insertStkDtlByGrn(params);
                warehMapper.updateStkDtlByGrn(params);
            }else if (BillType.GDN.equals( bizType)) {
            	warehMapper.insertStkDtlByGdn(params);
            	warehMapper.updateStkDtlByGdn(params);
            }else{
            	logger.warn(MessageFormat.format("不支持此类单据[{0}]", bizType));
                return false;
            }
            return saveLocTran(warehBean, isAdd);
        }catch (Exception ex){
        	logger.error(ex.getMessage());
            return false;
        }
    }
    
    /**
     * 添加仓库货位事务
     * @param warehBean
     * @param isAdd
     * @return boolean
     */
    private Boolean saveLocTran(WarehBean warehBean, Boolean isAdd){
        try{
        	BillType docType = warehBean.getDocType();
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("isAdd", isAdd?1:0);
        	params.put("locId", warehBean.getLocId());
        	params.put("docType", docType);
        	params.put("unitId",warehBean.getUnitId());
        	params.put("docNum", warehBean.getDocNum());
        	if (BillType.GDN .equals(docType)){
        		warehMapper.insertLocTranByGdn(params);
            }else if(BillType.GRN.equals(docType)){
            	warehMapper.insertLocTranByGrn(params);
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
	
	/**
	 * 查询虚拟仓库
	 * @param param : venderId brandId
	 * @return
	 */
	public String searchVirtualWarehouse(String venderId, String brandId){
		Map<String, String> param = new HashMap<String, String>();
		param.put("venderId", venderId);
		param.put("brandId", brandId);
		String virtualWarehouse = warehMapper.searchVirtualWarehouse(param);
		if (SoaBillUtils.isBlank(virtualWarehouse)) {
			logger.error("查询不到虚拟仓库,venderId："+venderId+" , brandId:"+brandId+", virtualWarehouse:"+virtualWarehouse);
		}
		return virtualWarehouse;
	}
}
