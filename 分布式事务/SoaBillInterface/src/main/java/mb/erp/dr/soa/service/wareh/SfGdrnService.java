package mb.erp.dr.soa.service.wareh;

import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 出入库服务
 * 包含：出入库
 * @author cyyu
 *
 */
public interface SfGdrnService {

	/**
	 * 单据出库服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGdn(SfGdnVo vo ,String newBillType);
	/**
	 * 单据入库服务
	 * @param vo 必填：unitId,gdnNum
	 * @param billType 必填
	 * @return
	 */
	public MsgVo billGrn(SfGrnVo vo ,String newBillType);
}
