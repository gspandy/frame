package mb.erp.dr.soa.old.service.bill;

import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单据接口服务
 * 包含服务：保存，确认，审核，撤销，配货，发货，收货等功能
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BillService
 * @since       全流通改造
 */
public abstract class BillService<T extends BaseBizVo> {

	private final Logger logger = LoggerFactory.getLogger(BillService.class);
  /**
   * 保存原始单据
   * @param vo
   * @return
   * @
   */
  public MsgVo save(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 确认
   * @param vo
   * @return
   * @
   */
  public MsgVo confirm(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 审核
   * @param vo
   * @return
   * @
   */
  public MsgVo audit(T vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 撤销
   * @param vo
   * @return
   * @
   */
  public MsgVo cancel(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  
  /**
   * 原始单据配货中
   * @param vo
   * @return
   * @
   */
  public MsgVo orderAG(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据已配货
   * @param vo
   * @return
   * @
   */
  public MsgVo orderAD(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据发货中
   * 状态 ：AP(已审)或FI(过账中) -> DG(发货中)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderDG(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据已发货
   * 状态 ：DG(发货中) -> DD(已发货)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderDD(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据收货中
   * 状态 ：DD(已发货) -> RG(收货中)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderRG(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据已收货
   * 状态 ：RG(收货中) -> RD(已收货)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderRD(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据出库冲单
   * 状态 ：DD(已发货) -> AP(已审)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderGDDL(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据入库冲单
   * 状态 ：RD(已收货) -> DD(已发货)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderGDRV(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据出库撤单
   * 状态 ：DG(发货中) -> AP(已审)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderGDDLCancel(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据入库撤单
   * 状态 ：RG(收货中) -> DD(已发货)
   * @param vo
   * @return
   * @
   */
  public MsgVo orderGDRVCancel(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  
  /**
   * 原始单据过账中
   * @param vo
   * @return
   * @
   */
  public MsgVo orderFI(T  vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
}
