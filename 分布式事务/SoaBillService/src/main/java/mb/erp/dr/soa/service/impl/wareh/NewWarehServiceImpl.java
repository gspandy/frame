package mb.erp.dr.soa.service.impl.wareh;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.DelivMode;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.RcptMode;
import mb.erp.dr.soa.constant.O2OMsgConstant.MSG_CODE;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.dao.SfWarehProdMapper;
import mb.erp.dr.soa.dao.SfWarehTranMapper;
import mb.erp.dr.soa.dao.SfWarehouseMapper;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.wareh.NewWarehService;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.SfWarehouseVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 新ERP 库存服务接口
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2014-12-18
 * @see         NewWarehService
 * @since       全流通改造
 */
@Service
public class NewWarehServiceImpl implements NewWarehService {
	
	private final Logger logger = LoggerFactory.getLogger(NewWarehServiceImpl.class);
	@Resource
    private SfDgnMapper sfDgnMapper;
    @Resource
    private SfGdnMapper sfGdnMapper;
    @Resource
    private SfRvdMapper sfRvdMapper;
    @Resource
    private SfGrnMapper sfGrnMapper;
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private SfWarehouseMapper sfWarehouseMapper;
    @Resource
    private SfWarehProdMapper sfWarehProdMapper;
    @Resource
    private SfWarehTranMapper sfWarehTranMapper;
    
    /**
     * 库存接口中的是非设定
     */
    final static String TRUE = "1",FALSE="0";
    
    public MsgVo increaseQtyInTransitByGdn(Long gdnId) {
		return modifyQtyInTransitByGdn(gdnId, true);
	}

	public MsgVo reduceQtyInTransitByGdn(Long gdnId) {
		return modifyQtyInTransitByGdn(gdnId, false);
	}

	public MsgVo increaseQtyInTransitByGrn(Long grnId) {
		return modifyQtyInTransitByGrn(grnId,true);
	}

	public MsgVo reduceQtyInTransitByGrn(Long grnId) {
		return modifyQtyInTransitByGrn(grnId,false);
	}

	public MsgVo increaseStockByGdn(Long gdnId) {
		return modifyStockByGdn(gdnId,true);
	}

	public MsgVo reduceStockByGdn(Long gdnId) {
		return modifyStockByGdn(gdnId,false);
	}

	public MsgVo increaseStockByGrn(Long grnId) {
		return modifyStockByGrn(grnId,true);
	}

	public MsgVo reduceStockByGrn(Long grnId) {
		return modifyStockByGrn(grnId,false);
	}
	
	public MsgVo increaseQtyCommittedByDgn(Long dgnId) {
		return modifyQtyCommittedByDgn(dgnId,true);
	}

	public MsgVo reduceQtyCommittedByDgn(Long dgnId) {
		return modifyQtyCommittedByDgn(dgnId,false);
	}
	
	/**
	 * 根据交货单增加在购库存
	 * @param dgnId
	 * @return MsgVo
	 */
	public MsgVo increaseQtyOnOrderByDgn(Long dgnId) {
		return modifyQtyOrderByDgn(dgnId,true);
	}

	/**
	 * 根据交货单减少在购库存
	 * @param dgnId
	 * @return MsgVo
	 */
	public MsgVo reduceQtyOnOrderByDgn(Long dgnId) {
		return modifyQtyOrderByDgn(dgnId,false);
	}
	
	/**
	 * 根据出库单增加货位库存
	 * @param Id
	 * @return MsgVo
	 */
	public MsgVo increaseLocQtyByGDN(Long Id) {
		return modifyLocQtyByGDN(Id, true);
	}

	/**
	 * 根据出库单减少货位库存
	 * @param Id
	 * @return MsgVo
	 */
	public MsgVo reduceLocQtyByGDN(Long Id) {
		return modifyLocQtyByGDN(Id, false);
	}

	/**
	 * 根据入库单增加货位库存
	 * @param Id
	 * @return MsgVo
	 */
	public MsgVo increaseLocQtyByGRN(Long Id) {
		return modifyLocQtyByGRN(Id, true);
	}

	/**
	 * 根据入库单减少货位库存
	 * @param Id
	 * @return MsgVo
	 */
	public MsgVo reduceLocQtyByGRN(Long Id) {
		return modifyLocQtyByGRN(Id, false);
	}
	
    /**
     * 入库更新在途库存
     * @param grnId
     * @param isAdd
     */
    public MsgVo modifyQtyInTransitByGrn(Long grnId, Boolean isAdd) {
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
        SfGrnVo grn = sfGrnMapper.selectGrnById(grnId);
        if (grn == null) throw new RuntimeException("入库单不存在");

        if (NewBillType.RVD.name().equals(grn.getSrcDocType())) throw new RuntimeException("入库单的到货通知单不存在");
        String rvdCode = grn.getSrcDocNum();
        SfRvdVo rvd = sfRvdMapper.selectSfRvdByCode(rvdCode);
        if (rvd == null) throw new RuntimeException("到货通知单不存在");

        if (RcptMode.AGOF.name().equals(grn.getRcptMode())
            || RcptMode.AGOR.name().equals(grn.getRcptMode())
            || RcptMode.SHOR.name().equals(grn.getRcptMode())
            || RcptMode.PRES.name().equals(grn.getRcptMode())
            || RcptMode.TRAN.name().equals(grn.getRcptMode())
            || RcptMode.AGRT.name().equals(grn.getRcptMode())
            || RcptMode.AGCR.name().equals(grn.getRcptMode())
            || RcptMode.SHCR.name().equals(grn.getRcptMode())){
            if (grn.getActualRcvWarehId() < 1) throw new RuntimeException("接收仓库不存在");

            if (RcptMode.PRES.name().equals(grn.getRootDocType())){
                Boolean isAgent =newERPCommonService.isAgent(grn.getUnitId());
                if (!isAgent) throw new RuntimeException("入库组织不是代理商");
            }
            
            Boolean isSuccess = UpdateQtyInTransit(rvd.getId(), isAdd);
            if (!isSuccess) throw new RuntimeException("更新在途库存出现异常");
            
            Map<String, Object> params = new HashMap<String, Object>();
        	params.put("grnId", grnId);
        	params.put("isAdd", isAdd?1:0);
            // 记录在途事务
            sfWarehTranMapper.insertSfWarehTransitTranByGrn(params);
        }
        else{
            throw new RuntimeException(MessageFormat.format("不支持的入库方式{0}", grn.getRcptMode()));
        }
    	return msgVo;
    }

    /**
     * 出库更新在途库存
     * @param gdnId
     * @param isAdd
     * @return msg
     */
    public MsgVo modifyQtyInTransitByGdn(Long gdnId, Boolean isAdd){
    	    MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
            SfGdnVo gdn = sfGdnMapper.selectGdnById(gdnId);
            if (gdn == null) throw new RuntimeException("出库单不存在");

            if (DelivMode.AGOF.name().equals(gdn.getDelivMode())
                || DelivMode.AGOR.name().equals(gdn.getDelivMode())
                || DelivMode.SHOR.name().equals(gdn.getDelivMode())
                || DelivMode.PRES.name().equals(gdn.getDelivMode())
                || DelivMode.TRAN.name().equals(gdn.getDelivMode())
                || DelivMode.AGRT.name().equals(gdn.getDelivMode())){
                if (gdn.getBfOrgRcvWarehId() < 1) throw new RuntimeException("接收仓库不存在");
                
                if (DelivMode.PRES.name().equals(gdn.getDelivMode())){
                    Boolean isAgent = newERPCommonService.isAgent(gdn.getBfOrgRcvUnitId());
                    if (!isAgent) throw new RuntimeException("接收方组织不是代理商");
                }
                Map<String, Object> params = new HashMap<String, Object>();
            	params.put("gdnId", gdnId);
            	params.put("isAdd", isAdd?1:0);
            	sfWarehProdMapper.updateQtyInTransitByGdn(params);
            	sfWarehProdMapper.insertQtyInTransitByGdn(params);
            	
            	// 保存在途事务
            	sfWarehTranMapper.insertSfWarehTransitTranByGdn(params);
            }
            else {
                throw new RuntimeException(MessageFormat.format("不支持的出库方式{0}", gdn.getDelivMode()));
            }
            return msgVo;
    	
    }

    /**
     * 根据入库单修改库存
     * @param grnId
     * @param isAdd
     * @return msg
     */
    public MsgVo modifyStockByGrn(Long grnId,Boolean isAdd){
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
        SfGrnVo grn = sfGrnMapper.selectGrnById(grnId);
        if (grn == null) throw new RuntimeException("入库单不存在:"+grnId);

        //仓库库存
        Boolean isSuccess = updateStkOnHandByBiz(grnId, NewBillType.GRN, isAdd);
        if (!isSuccess) throw new RuntimeException("更新实际库存失败");

    	return msgVo;
    }

    /**
     * 根据出库单修改库存
     * @param gdnId
     * @param isAdd
     * @return msg
     */
    public MsgVo modifyStockByGdn(Long gdnId, Boolean isAdd){
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
        SfGdnVo gdn = sfGdnMapper.selectGdnById(gdnId);
        if (gdn == null) throw new RuntimeException("出库单不存在");

        //仓库库存
        Boolean isSuccess = updateStkOnHandByBiz(gdnId, NewBillType.GDN, isAdd);
        if (!isSuccess) throw new RuntimeException("更新实际库存失败");

    	return msgVo;
    }

    /**
     * 根据交货单修改已分配库存
     * @param dgnId
     * @param isAdd
     * @return
     */
    public MsgVo modifyQtyCommittedByDgn(Long dgnId , Boolean isAdd){
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
    	SfDgnVo dgnVo = sfDgnMapper.selectDgnById(dgnId);
    	if (dgnVo == null) throw new RuntimeException("交货单不存在");
        
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("dgnId", dgnId);
    	params.put("isAdd", isAdd?1:0);
    	sfWarehProdMapper.updateQtyCommittedByDgn(params);
    	sfWarehProdMapper.insertQtyCommittedByDgn(params);
    	
    	//已分配库存事务
        Integer isSuccess = sfWarehTranMapper.insertSfWarehCommitedTranByDgn(params);
        if (isSuccess==0) throw new RuntimeException("添加在购库存失败");
    	return msgVo;
    }
    
    /**
     * 根据交货单修改在购库存
     * @param dgnId
     * @param isAdd
     * @return
     */
    public MsgVo modifyQtyOrderByDgn(Long dgnId , Boolean isAdd){
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
    	SfDgnVo dgnVo = sfDgnMapper.selectDgnById(dgnId);
    	if (dgnVo == null) throw new RuntimeException("交货单不存在");
        
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("dgnId", dgnId);
    	params.put("isAdd", isAdd?1:0);
    	sfWarehProdMapper.updateQtyOnOrderByDgn(params);
    	sfWarehProdMapper.insertQtyOnOrderByDgn(params);
    	
    	//在购库存事务
        Integer isSuccess = sfWarehTranMapper.insertSfWarehOrderTranByDgn(params);
        if (isSuccess==0) throw new RuntimeException("添加在购库存失败");
    	return msgVo;
    }
    
    /**
     * 根据出库单修改货位库存
     * @param sfGdnId
     * @param isAdd
     * @return
     */
    public MsgVo modifyLocQtyByGDN(Long sfGdnId, boolean isAdd) {
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
        SfGdnVo gdn = sfGdnMapper.selectGdnById(sfGdnId);
        if (gdn == null) throw new RuntimeException("出库单不存在,单据号:"+sfGdnId);
        String warehCode = newERPCommonService.getUnitCodeByUnitId(gdn.getBfOrgWarehId());
    	//货位库存
        SfWarehouseVo warehouseInfo = sfWarehouseMapper.selectById(gdn.getBfOrgWarehId());
        if (warehouseInfo == null) throw new RuntimeException(MessageFormat.format("仓库{0}-仓库类型没设置。",warehCode));
        if (TRUE.equals(warehouseInfo.getLocAdopted())){
            Long dispLocId = 0l;
            List<SfGdnDtlVo> list= sfGdnMapper.selectGdnDtlWithoutLocId(sfGdnId);
            //按指定货位更新货位库存
            if(list == null || list.size() == 0){
            	boolean isSuccess = updateLocQtyByBiz(sfGdnId, NewBillType.GDN, dispLocId, isAdd);
                if (!isSuccess) {
                	throw new RuntimeException("更新货位数量失败");
                } else {
                	return msgVo;
                }
            }
            //如果启用箱管理的找B2B_DISP_LOC_ID这个字段 不然用DISP_LOC_ID
            if (TRUE.equals(warehouseInfo.getBoxAdopted())){
                dispLocId = warehouseInfo.getB2bDispLocId();
            } else {
                dispLocId = warehouseInfo.getDispLocId();
            }
            if (dispLocId == null) {
            	throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理,发货货位不存在:",warehCode));
            }
            Long temp = sfWarehouseMapper.selectLocById(dispLocId);
            if (temp != null && temp > 0){
            	Boolean isSuccess = updateLocQtyByBiz(sfGdnId, NewBillType.GDN, dispLocId, isAdd);
                if (!isSuccess) throw new RuntimeException("更新库位数量失败");
            } else {
                if (TRUE.equals(warehouseInfo.getBoxAdopted())){
                    throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理和箱管理，请设置箱发货货位", warehCode));
                } else {
                    throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理，请设置非箱发货货位", warehCode));
                }
            }
        } else {
        	logger.warn("仓库没有启用货位管理，仓库编码:"+warehCode);
        }
        
        return msgVo;
    }
    
    /**
     * 根据出库单修改货位库存
     * @param sfGRnId
     * @param isAdd
     * @return
     */
    public MsgVo modifyLocQtyByGRN(Long sfGRnId, boolean isAdd) {
    	MsgVo msgVo = new MsgVo(MSG_CODE.SUCCESS);
        SfGrnVo grn = sfGrnMapper.selectGrnById(sfGRnId);
        if (grn == null) throw new RuntimeException("入库单不存在,单据号:"+sfGRnId);
        String warehCode = newERPCommonService.getUnitCodeByUnitId(grn.getWarehId());
        //货位库存
        SfWarehouseVo warehouseInfo = sfWarehouseMapper.selectById(grn.getWarehId());
        if (warehouseInfo == null) throw new RuntimeException(MessageFormat.format("仓库{0}-仓库类型没设置。",warehCode));
        if (TRUE.equals(warehouseInfo.getLocAdopted())){
            Long rcptLocId = 0l;
            List<SfGrnDtlVo> list= sfGrnMapper.selectGrnDtlWithoutLocId(sfGRnId);
            //按指定货位更新货位库存
            if(list == null || list.size() == 0){
            	boolean isSuccess = updateLocQtyByBiz(sfGRnId, NewBillType.GRN, rcptLocId, isAdd);
                if (!isSuccess) {
                	throw new RuntimeException("更新货位数量失败");
                } else {
                	return msgVo;
                }
            }
            //按默认货位或指定货位更新货位库存
            if (TRUE.equals(warehouseInfo.getBoxAdopted())){
                rcptLocId = warehouseInfo.getBoxRcptLocId();
            }else{
                rcptLocId = warehouseInfo.getRcptLocId();
            }
            
            if (rcptLocId == null) {
            	throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理,收货货位不存在",warehCode));
            }
            Long temp = sfWarehouseMapper.selectLocById(rcptLocId);
            if (temp != null && temp > 0){
                boolean isSuccess = updateLocQtyByBiz(sfGRnId, NewBillType.GRN, rcptLocId, isAdd);
                if (!isSuccess) throw new RuntimeException("更新货位数量失败");
            }else{
                if (TRUE.equals(warehouseInfo.getBoxAdopted())){
                    throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理和箱管理，请设置箱收货货位", warehCode));
                }else{
                    throw new RuntimeException(MessageFormat.format("仓库{0}已启用货位管理，请设置非箱收货货位", warehCode));
                }
            }
        } else {
        	logger.warn("仓库没有启用货位管理，仓库编码:"+warehCode);
        }
        
        return msgVo;
    }
    
    

	/**
     * 更新在途库存
     * @param rvdId 到货通知单 单据ID
     * @param isAdd ture:增加 false:扣减
     * @return
     */
    private Boolean UpdateQtyInTransit(Long rvdId, Boolean isAdd){
        try{
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("rvdId", rvdId);
        	params.put("isAdd", isAdd?1:0);
        	sfWarehProdMapper.updateQtyInTransit(params);
        	sfWarehProdMapper.insertQtyInTransit(params);
            return true;
        }catch (Exception ex){
        	throw new RuntimeException(ex.getMessage());
        }
    }
    
    /**
     * 更新实际库存
     * @param id
     * @param bizType SF_GDN， SF_GRN
     * @param isAdd
     * @param String
     * @return
     */
    public Boolean updateStkOnHandByBiz(Long id, NewBillType bizType, Boolean isAdd){
        try{
            Map<String, Object> params = new HashMap<String, Object>();
        	params.put("isAdd", isAdd?1:0);
            if (NewBillType.GDN.equals(bizType)){
            	params.put("gdnId", id);
                sfWarehProdMapper.insertStkOnHandSfGdn(params);
                sfWarehProdMapper.updateStkOnHandSfGdn(params);
            }else if(NewBillType.GRN.equals(bizType)){
            	params.put("grnId", id);
            	sfWarehProdMapper.insertStkOnHandSfGrn(params);
                sfWarehProdMapper.updateStkOnHandSfGrn(params);
            }else{
            	logger.warn(MessageFormat.format("不支持此类单据[{0}]", bizType));
                return false;
            }
            
            //判断是否需要更新仓库库存成本
            Boolean isUpdateWAREH_PROD = true;
            if (NewBillType.GDN.equals(bizType)){
                String calType = sfWarehProdMapper.getGdnCalType(id);
                if (null == calType || ("0").equals(calType)){
                	isUpdateWAREH_PROD = false;
                }
//                isUpdateWAREH_PROD = calType.equals("0") ? false : true;
            } else if (NewBillType.GRN.equals(bizType)) {
            	String calType = sfWarehProdMapper.getGrnCalType(id);
            	if (null == calType || ("0").equals(calType)){
                	isUpdateWAREH_PROD = false;
                }
//                isUpdateWAREH_PROD = calType.equals("0") ? false : true;
            }
            if (isUpdateWAREH_PROD){
                //更新仓库库存成本
            	if (NewBillType.GDN.equals(bizType)){
                    sfWarehProdMapper.updateSfWarehProdSfGdn(params);
                }else if(NewBillType.GRN.equals(bizType)){
                    sfWarehProdMapper.updateSfWarehProdSfGrn(params);
                }
            }
            // 添加仓库商品事务
            if (NewBillType.GDN.equals(bizType)){
            	sfWarehTranMapper.insertSfWarehTranByGdn(params);
            }else if(NewBillType.GRN.equals(bizType)){
            	sfWarehTranMapper.insertSfWarehTranByGrn(params);
            }
            return true;
        }catch (Exception ex){
        	logger.error(ex.getMessage());
            return false;
        }
    }
    
    /**
     * 更新库位数量
     * @param id 单据号
     * @param bizType 出库单‘SF_GRN’,出库单’SF_GDN’
     * @param locId 库位号
     * @param isAdd
     * @return
     */
    public Boolean updateLocQtyByBiz(Long id, NewBillType bizType, Long locId, Boolean isAdd){
        try{
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("isAdd", isAdd?1:0);
        	params.put("sfWarehouseLocId", locId);
            if (NewBillType.GRN.equals( bizType)){
            	params.put("grnId", id);
                sfWarehProdMapper.insertLocByGrn(params);
                sfWarehProdMapper.updateLocByGrn(params);
            }else if (NewBillType.GDN.equals( bizType)) {
            	params.put("gdnId", id);
            	sfWarehProdMapper.insertLocByGdn(params);
                sfWarehProdMapper.updateLocByGdn(params);
            }else{
            	logger.warn(MessageFormat.format("不支持此类单据[{0}]", bizType));
                return false;
            }
            return InsertSF_LOC_TRANByBiz(id,locId, bizType, isAdd);
        }catch (Exception ex){
        	logger.error(ex.getMessage());
            return false;
        }
    }
    
    /**
     * 添加仓库货位事务
     * @param id
     * @param locId
     * @param docType
     * @param isAdd
     * @return
     */
    public Boolean InsertSF_LOC_TRANByBiz(Long id, Long locId,NewBillType docType, Boolean isAdd){
        try{
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("isAdd", isAdd?1:0);
        	params.put("locId", locId);
        	params.put("docType", docType.name());
        	if (NewBillType.GDN .equals(docType)){
        		params.put("gdnId", id);
        		sfWarehTranMapper.insertSfLocTranByGdn(params);
            }else if(NewBillType.GRN.equals(docType)){
            	params.put("grnId", id);
            	sfWarehTranMapper.insertSfLocTranByGrn(params);
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    
}
