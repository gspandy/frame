package mb.erp.dr.soa.bean;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 资金接口参数bean
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BalanceBean
 * @since       全流通改造
 */
public class BalanceBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5831012947037761667L;
	private String uid; 
	private String cpdUid;
	private Double balance;
	private BillType docType;
	private String docNum;
	private Date docDate; // 单据时间，使用专项资金时，查询可支配金额用到
	private String fsclAcId; // 往来账号，使用专项资金时，查询可支配金额用到
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getCpdUid() {
		return cpdUid;
	}
	public void setCpdUid(String cpdUid) {
		this.cpdUid = cpdUid;
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
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}
	public String getFsclAcId() {
		return fsclAcId;
	}
	public void setFsclAcId(String fsclAcId) {
		this.fsclAcId = fsclAcId;
	}
	
}
