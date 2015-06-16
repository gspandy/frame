package mb.erp.dr.soa.service.proxy;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.NewBaseBizVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 新ERP 单据服务代理
 * 包含接口：保存，确认，审核，收发货等等。
 * @author     余从玉
 * @version    1.0, 2014-12-20
 * @see         INewBillServiceHander
 * @since       全流通改造
 */
public interface INewBillServiceHander {

	public <T extends NewBaseBizVo>  MsgVo save(T vo , NewBillType type)  ;
	
	public <T extends NewBaseBizVo> MsgVo confirm(T vo , NewBillType type)  ;
	
	public <T extends NewBaseBizVo> MsgVo audit(T vo , NewBillType type)  ;
	
	public <T extends NewBaseBizVo> MsgVo cancel(T vo , NewBillType type)  ;
	
	public MsgVo orderAG(Long srcId, String code, Long sfGdrnId , AdnVo vo, NewBillType type);
	
	public MsgVo orderAD(Long srcId, String code, Long sfGdrnId , AdnVo vo, NewBillType type);
	
	public MsgVo orderDG(Long srcId, String code, Long sfGdrnId , NewBillType type);
	
	public MsgVo orderDD(Long srcId, String code, Long sfGdrnId , GdnVo vo, NewBillType type);
	
	public MsgVo orderRG(Long srcId, String code, Long sfGdrnId , GrnVo vo, NewBillType type);

	public MsgVo orderRD(Long srcId, String code, Long sfGdrnId , GrnVo vo, NewBillType type);
}
