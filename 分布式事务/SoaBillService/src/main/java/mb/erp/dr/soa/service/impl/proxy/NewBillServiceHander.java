package mb.erp.dr.soa.service.impl.proxy;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.proxy.INewBillServiceHander;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.NewBaseBizVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.stereotype.Service;

/**
 * 新ERP 单据服务代理
 * 包含接口：保存，确认，审核，收发货等等。
 * @author     余从玉
 * @version    1.0, 2014-12-20
 * @see         INewBillServiceHander
 * @since       全流通改造
 */
@Service
public class NewBillServiceHander implements INewBillServiceHander{

	@Resource
	private NewBillService<DrTbnVo> sfTbnService;
	@Resource
	private NewBillService<SfDgnVo> sfDgnService;
	@Resource
	private NewBillService<SfGdnVo> sfGdnService;
	@Resource
	private NewBillService<SfGrnVo> sfGrnService;
	@Resource
	private NewBillService<SfIdtVo> sfIdtService;
	@Resource
	private NewBillService<SfRvdVo> sfRvdService;
	
	public <T extends NewBaseBizVo>  MsgVo save(T vo , NewBillType type)  {
		return get(type).save(vo);
	}
	
	public <T extends NewBaseBizVo> MsgVo confirm(T vo , NewBillType type)  {
		return get(type).confirm(vo);
	}
	
	public <T extends NewBaseBizVo> MsgVo audit(T vo , NewBillType type)  {
		return get(type).audit(vo);
	}
	
	public <T extends NewBaseBizVo> MsgVo cancel(T vo , NewBillType type)  {
		return get(type).cancel(vo);
	}
	
	public MsgVo orderAG(Long srcId, String code, Long sfGdrnId , AdnVo vo, NewBillType type){
		return get(type).orderAG(srcId, code, sfGdrnId,vo);
	}
	
	public MsgVo orderAD(Long srcId, String code, Long sfGdrnId , AdnVo vo, NewBillType type){
		return get(type).orderAD(srcId, code, sfGdrnId,vo);
	}
	
	public MsgVo orderDG(Long srcId, String code, Long sfGdrnId , NewBillType type) {
		return get(type).orderDG(srcId, code, sfGdrnId);
	}
	
	public MsgVo orderDD(Long srcId, String code, Long sfGdrnId , GdnVo vo, NewBillType type){
		return get(type).orderDD(srcId, code, sfGdrnId,vo);
	}
	
	public MsgVo orderRG(Long srcId, String code, Long sfGdrnId , GrnVo vo,NewBillType type){
		return get(type).orderRG(srcId, code, sfGdrnId,vo);
	}

	
	public MsgVo orderRD(Long srcId, String code, Long sfGdrnId , GrnVo vo,NewBillType type){
		return get(type).orderRD(srcId, code, sfGdrnId,vo);
	}

	@SuppressWarnings("unchecked")
	private <T extends NewBaseBizVo>  NewBillService<T>  get(NewBillType type){
		switch (type) {
		case TBN:
			return (NewBillService<T>) sfTbnService; 
		case IDT:
			return (NewBillService<T>) sfIdtService; 
		case DGN:
			return (NewBillService<T>) sfDgnService; 
		case GDN:
			return (NewBillService<T>) sfGdnService; 
		case RVD:
			return (NewBillService<T>) sfRvdService; 
		case GRN:
			return (NewBillService<T>) sfGrnService; 
		default:
			break;
		}
		return null;
	}
	
}
