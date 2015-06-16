package mb.erp.dr.soa.old.service.proxy;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 老ERP 单据服务代理
 * 包含接口：保存，确认，审核，收发货等等。
 * @author     余从玉
 * @version    1.0, 2014-12-20
 * @see         IBillServiceHander
 * @since       全流通改造
 */
public interface IBillServiceHander {

	public <T extends BaseBizVo>  MsgVo save(T vo , BillType type)  ;
	
	public <T extends BaseBizVo>  MsgVo confirm(T vo , BillType type)  ;
	
	public <T extends BaseBizVo>  MsgVo audit(T vo , BillType type)  ;
	
	public <T extends BaseBizVo>  MsgVo cancel(T vo , BillType type)  ;
	
	public <T extends BaseBizVo> MsgVo orderFI(T vo , BillType type);
	
	public <T extends BaseBizVo> MsgVo orderAG(T vo , BillType type);
	
	public  <T extends BaseBizVo> MsgVo orderAD(T vo , BillType type);
	
	public  <T extends BaseBizVo> MsgVo orderDG(T vo , BillType type);
	
	public  <T extends BaseBizVo> MsgVo orderDD(T vo , BillType type);
	
	public  <T extends BaseBizVo> MsgVo orderRG(T vo , BillType type);

	public  <T extends BaseBizVo> MsgVo orderRD(T vo , BillType type);
}
