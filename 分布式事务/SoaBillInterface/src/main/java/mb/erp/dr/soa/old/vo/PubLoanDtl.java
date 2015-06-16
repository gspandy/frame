package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款单明细vo
 * 使用专项资金时，会生成一张借款单，不过不用保存明细，还款的时候生成明细
 * @author     余从玉
 * @version    1.0, 2015-2-5
 * @see         PubLoanDoc
 * @since       全流通改造
 */
public class PubLoanDtl implements Serializable {
	
	private static final long serialVersionUID = -6209475604935239086L;
	
	private String docNum;
    private String unitId;
    private String loanDocNum;
    private Double payAmount;
    private String payDocNum;
    private Date payTime;
    private String remark;
    private String creditTranNum;

    public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getLoanDocNum() {
        return loanDocNum;
    }

    public void setLoanDocNum(String loanDocNum) {
        this.loanDocNum = loanDocNum == null ? null : loanDocNum.trim();
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayDocNum() {
        return payDocNum;
    }

    public void setPayDocNum(String payDocNum) {
        this.payDocNum = payDocNum == null ? null : payDocNum.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreditTranNum() {
        return creditTranNum;
    }

    public void setCreditTranNum(String creditTranNum) {
        this.creditTranNum = creditTranNum == null ? null : creditTranNum.trim();
    }
}