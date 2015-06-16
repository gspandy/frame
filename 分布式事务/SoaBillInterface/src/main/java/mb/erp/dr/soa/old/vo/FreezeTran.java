package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 冻结事务表
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         FreezeTran
 * @since       全流通改造
 */
public class FreezeTran  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4887586253141074215L;
	
	private String unitId ; // 供货方编码
	private Double tranNum ; // 交易编码
	private String fsclAcId ; // 往来账户编码
	private String currency ; // 币种
	private Double tranAmnt ; // 发生金额
	private Double balance ; // 余额
	private BillType docType ; // 单据类型
	private String docNum ; // 单据编码
	private Date tranDate ; // 发生日期
	private Date tranDatetime ; // 发生时间

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

	public String getFsclAcId() {
        return fsclAcId;
    }

    public void setFsclAcId(String fsclAcId) {
        this.fsclAcId = fsclAcId == null ? null : fsclAcId.trim();
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
        this.docNum = docNum == null ? null : docNum.trim();
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Date getTranDatetime() {
        return tranDatetime;
    }

    public void setTranDatetime(Date tranDatetime) {
        this.tranDatetime = tranDatetime;
    }

}