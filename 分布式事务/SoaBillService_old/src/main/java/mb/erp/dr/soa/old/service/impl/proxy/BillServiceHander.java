package mb.erp.dr.soa.old.service.impl.proxy;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.proxy.IBillServiceHander;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.stereotype.Service;

/**
 * 新ERP 单据服务代理
 * 包含接口：保存，确认，审核，收发货等等。
 * @author     余从玉
 * @version    1.0, 2014-12-20
 * @see         IBillServiceHander
 * @since       全流通改造
 */
@Service
public class BillServiceHander implements IBillServiceHander{

	@Resource
	private BillService<TbnVo> tbnService;
	@Resource
	private BillService<AdnVo> adnService;
	@Resource
	private BillService<GdnVo> gdnService;
	@Resource
	private BillService<GrnVo> grnService;
	@Resource
	private BillService<IdtVo> idtService;
	@Resource
	private BillService<BgrVo> bgrService;
	@Resource
	private BillService<ScnVo> scnService;
	
	public <T extends BaseBizVo>  MsgVo save(T vo , BillType type)  {
		return get(type).save(vo);
	}
	
	public <T extends BaseBizVo> MsgVo confirm(T vo , BillType type)  {
		return get(type).confirm(vo);
	}
	
	public <T extends BaseBizVo> MsgVo audit(T vo , BillType type)  {
		return get(type).audit(vo);
	}
	
	public <T extends BaseBizVo> MsgVo cancel(T vo , BillType type)  {
		return get(type).cancel(vo);
	}
	
	public <T extends BaseBizVo> MsgVo orderFI(T vo, BillType type) {
		return get(type).orderFI(vo);
	} 
	
	public <T extends BaseBizVo> MsgVo orderAG(T vo, BillType type) {
		return get(type).orderAG(vo);
	}

	public <T extends BaseBizVo> MsgVo orderAD(T vo, BillType type) {
		return get(type).orderAD(vo);
	}

	public <T extends BaseBizVo> MsgVo orderDG(T vo, BillType type) {
		return get(type).orderDG(vo);
	}

	public <T extends BaseBizVo> MsgVo orderDD(T vo, BillType type) {
		return get(type).orderDD(vo);
	}

	public <T extends BaseBizVo> MsgVo orderRG(T vo, BillType type) {
		return get(type).orderRG(vo);
	}

	public <T extends BaseBizVo> MsgVo orderRD(T vo, BillType type) {
		return get(type).orderRD(vo);
	}
	@SuppressWarnings("unchecked")
	private <T extends BaseBizVo>  BillService<T>  get(BillType type){
		switch (type) {
		case TBN:
			return (BillService<T>) tbnService; 
		case IDT:
			return (BillService<T>) idtService; 
		case ADN:
			return (BillService<T>) adnService; 
		case GDN:
			return (BillService<T>) gdnService; 
		case GRN:
			return (BillService<T>) grnService; 
		case SBG:
			return (BillService<T>) bgrService;
		case SSC:
			return (BillService<T>) scnService;
		default:
			break;
		}
		return null;
	}

}
