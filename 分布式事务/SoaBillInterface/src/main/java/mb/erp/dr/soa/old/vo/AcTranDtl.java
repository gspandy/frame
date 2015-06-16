package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 往来事务明细表vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         AcTranDtl
 * @since       全流通改造
 */
public class AcTranDtl  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6075032945867300566L;
	
	private String unitId ; // 供货方编码
	private Double tranNum ; // 交易编码
	private Double lineNum ; // 行号
	private String fsclAcId ; // 往来账户编码
	private String debitOrCredit ; // 借或贷
	private String currency ; // 币种
	private Double tranAmnt ; // 发生金额
	private Date balanceDate ; // 余额变动日期
	private Double balance ; // 余额

    public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Double getTranNum() {
		return tranNum;
	}

	public void setTranNum(Double tranNum) {
		this.tranNum = tranNum;
	}

	public Double getLineNum() {
		return lineNum;
	}

	public void setLineNum(Double lineNum) {
		this.lineNum = lineNum;
	}

	public String getFsclAcId() {
        return fsclAcId;
    }

    public void setFsclAcId(String fsclAcId) {
        this.fsclAcId = fsclAcId == null ? null : fsclAcId.trim();
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit == null ? null : debitOrCredit.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Double getTranAmnt() {
        return tranAmnt;
    }

    public void setTranAmnt(Double tranAmnt) {
        this.tranAmnt = tranAmnt;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}