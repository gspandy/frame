package mb.erp.dr.soa.old.service.wareh;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 出入库服务
 * 包含：出入库，出入库冲单，出入库撤单
 * @author cyyu
 *
 */
public interface CgdrnService {

	/**
	 * 单据出库服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGdn(GdnVo vo ,BillType billType);
	/**
	 * 单据入库服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGrn(GrnVo vo ,BillType billType);
	/**
	 * 单据出库冲单服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGddl(GdnVo vo ,BillType billType);
	/**
	 * 单据入库冲单服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGdrv(GrnVo vo ,BillType billType);
}
