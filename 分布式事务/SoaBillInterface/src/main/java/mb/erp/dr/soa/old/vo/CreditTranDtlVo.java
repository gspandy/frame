package mb.erp.dr.soa.old.vo;

import java.io.Serializable;

/**
 * 信用额度事务明细表
 * 
 * @author 陈志杰
 * @version 1.0, 2015-1-29
 * @see CreditTranDtlVo
 * @since 全流通改造
 */
public class CreditTranDtlVo implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 4210291169639211812L;

	private String creditTranDtlId;// 流水号
	private String unitId;// 组织编码
	private String creditTranId;// 信用额度流水号
	private String docSetUpNum;
	private Double tranMoney;//发生金额
	private String docNum;//专项金额流水

	public String getCreditTranDtlId() {
		return creditTranDtlId;
	}

	public void setCreditTranDtlId(String creditTranDtlId) {
		this.creditTranDtlId = creditTranDtlId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCreditTranId() {
		return creditTranId;
	}

	public void setCreditTranId(String creditTranId) {
		this.creditTranId = creditTranId;
	}

	public String getDocSetUpNum() {
		return docSetUpNum;
	}

	public void setDocSetUpNum(String docSetUpNum) {
		this.docSetUpNum = docSetUpNum;
	}

	public Double getTranMoney() {
		return tranMoney;
	}

	public void setTranMoney(Double tranMoney) {
		this.tranMoney = tranMoney;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
}