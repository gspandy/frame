package mb.erp.dr.soa.service.bill;

import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.NewBaseBizVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新ERP单据接口服务
 * 包含服务：保存，确认，审核，撤销，配货，发货，收货等功能
 * @author     余从玉
 * @version    1.0, 2014-11-21
 * @see         NewBillService
 * @since       全流通改造
 */
public abstract class NewBillService<T extends NewBaseBizVo> {

	private final Logger logger = LoggerFactory.getLogger(NewBillService.class);
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
  public MsgVo orderAG(Long srcId,String code,Long sfGdrnId,AdnVo vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
  /**
   * 原始单据已配货
   * @param vo
   * @return
   * @
   */
  public MsgVo orderAD(Long srcId,String code,Long sfGdrnId,AdnVo vo) {
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
  public MsgVo orderDG(Long srcId,String code,Long sfGdrnId) {
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
  public MsgVo orderDD(Long srcId,String code,Long sfGdrnId,GdnVo vo) {
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
  public MsgVo orderRG(Long srcId,String code,Long sfGdrnId,GrnVo vo) {
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
  public MsgVo orderRD(Long srcId,String code,Long sfGdrnId,GrnVo vo) {
		logger.error("can not find the method of save in the child class");
		return null;
	}
}
