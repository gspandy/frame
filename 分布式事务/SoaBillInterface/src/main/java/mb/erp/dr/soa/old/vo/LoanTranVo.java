package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.LimitType;
import mb.erp.dr.soa.constant.O2OBillConstant.TranType;

/**
 * 借款事务表
 * 
 * @author 陈志杰
 * @version 1.0, 2015-1-29
 * @see LoanTranVo
 * @since 全流通改造
 */
public class LoanTranVo implements Serializable {

	private static final long serialVersionUID = 3327929602454092837L;

	private String unitId; // 上级组织编码
	private String loanTranId; // 流水号
	private String vendeeId; // 下级组织编码
	private Date tranDate; // 发生日期
	private Date tranTime; // 发生时间
	private TranType tranType; // 借款事务类型
	private Double tranMoney; // 发生金额
	private BillType docType;// 原始单据类型
	private String docNum;// 单据号

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getLoanTranId() {
		return loanTranId;
	}

	public void setLoanTranId(String loanTranId) {
		this.loanTranId = loanTranId;
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

	public TranType getTranType() {
		return tranType;
	}

	public void setTranType(TranType tranType) {
		this.tranType = tranType;
	}

	public Double getTranMoney() {
		return tranMoney;
	}

	public void setTranMoney(Double tranMoney) {
		this.tranMoney = tranMoney;
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
}