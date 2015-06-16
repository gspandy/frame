package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.LimitType;

/**
 * 信用额度事务表
 * 
 * @author 陈志杰
 * @version 1.0, 2015-1-29
 * @see CreditTranVo
 * @since 全流通改造
 */
public class CreditTranVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 6365268594672342567L;

	private String creditTranId; // 流水号
	private String unitId; // 上级组织编码
	private String vendeeId; // 下级组织编码
	private Date tranDate; // 发生日期
	private Date tranTime; // 发生时间
	private LimitType limitType; // 资金类型
	private Double limitMoney; // 信用额度发生金额
	private Double balance; // 信用额度余额
	private BillType docType;// 原始单据类型
	private String docNum;// 单据号
	private List<CreditTranDtlVo> creditTranDtlVos;//事务明细

	public String getCreditTranId() {
		return creditTranId;
	}

	public void setCreditTranId(String creditTranId) {
		this.creditTranId = creditTranId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public LimitType getLimitType() {
		return limitType;
	}

	public void setLimitType(LimitType limitType) {
		this.limitType = limitType;
	}

	public Double getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(Double limitMoney) {
		this.limitMoney = limitMoney;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public BillType getDocType() {
		return docType;
	}

	public void setDocType(BillType docType) {
		this.docType = docType;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public List<CreditTranDtlVo> getCreditTranDtlVos() {
		return creditTranDtlVos;
	}

	public void setCreditTranDtlVos(List<CreditTranDtlVo> creditTranDtlVos) {
		this.creditTranDtlVos = creditTranDtlVos;
	}
}