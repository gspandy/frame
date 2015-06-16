package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 结算价格表
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         FsclAc
 * @since       全流通改造
 */
public class FsclAc  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7036605417864587977L;
	
	private String unitId ; // 供货方编码
	private String fsclAcId ; // 往来账户ID
	private String cpdUnitId ; // 购货方编码
	private String acType ; // 账户类型
	private String acName ; // 账户名称
	private String currency ; // 币种
	private Double balance ; // 余额
	private Double frzBal ; // 冻结资金
	private Double minBal ; // 最小限额
	private Double psnBal ; // 折让金额
	private Double tempLoan ; // 临时借支
	private Date tlDeadline ; // 临时借支有效期
	private String exAcId ; // 外部账户ID
	private String regionCode ; // 区域编码
	private String status ; // 状态
	private Double evInTran ; // 补贴金额
	private Double fucBal ; // 期货定金
	private Double debitVal ; // 欠款金额
	private String synAction ; // 同步标志
	private Double fundVal ; // 基金金额


    public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getFsclAcId() {
		return fsclAcId;
	}

	public void setFsclAcId(String fsclAcId) {
		this.fsclAcId = fsclAcId;
	}

	public String getCpdUnitId() {
        return cpdUnitId;
    }

    public void setCpdUnitId(String cpdUnitId) {
        this.cpdUnitId = cpdUnitId == null ? null : cpdUnitId.trim();
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType == null ? null : acType.trim();
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName == null ? null : acName.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFrzBal() {
        return frzBal;
    }

    public void setFrzBal(Double frzBal) {
        this.frzBal = frzBal;
    }

    public Double getMinBal() {
        return minBal;
    }

    public void setMinBal(Double minBal) {
        this.minBal = minBal;
    }

    public Double getPsnBal() {
        return psnBal;
    }

    public void setPsnBal(Double psnBal) {
        this.psnBal = psnBal;
    }

    public Double getTempLoan() {
        return tempLoan;
    }

    public void setTempLoan(Double tempLoan) {
        this.tempLoan = tempLoan;
    }

    public Date getTlDeadline() {
        return tlDeadline;
    }

    public void setTlDeadline(Date tlDeadline) {
        this.tlDeadline = tlDeadline;
    }

    public String getExAcId() {
        return exAcId;
    }

    public void setExAcId(String exAcId) {
        this.exAcId = exAcId == null ? null : exAcId.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Double getEvInTran() {
        return evInTran;
    }

    public void setEvInTran(Double evInTran) {
        this.evInTran = evInTran;
    }

    public Double getFucBal() {
        return fucBal;
    }

    public void setFucBal(Double fucBal) {
        this.fucBal = fucBal;
    }

    public Double getDebitVal() {
        return debitVal;
    }

    public void setDebitVal(Double debitVal) {
        this.debitVal = debitVal;
    }

    public String getSynAction() {
        return synAction;
    }

    public void setSynAction(String synAction) {
        this.synAction = synAction == null ? null : synAction.trim();
    }

    public Double getFundVal() {
        return fundVal;
    }

    public void setFundVal(Double fundVal) {
        this.fundVal = fundVal;
    }
}