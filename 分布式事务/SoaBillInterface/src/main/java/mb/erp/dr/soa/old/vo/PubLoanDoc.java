package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款单vo
 * 使用专项资金时，会生成一张借款单
 * @author     余从玉
 * @version    1.0, 2015-2-5
 * @see         PubLoanDtl
 * @since       全流通改造
 */
public class PubLoanDoc implements Serializable {
	
	private static final long serialVersionUID = 7519264227812885027L;

	private String loanDocNum;
	private String unitId;
	private Date docDate;
    private String vendeeId;
    private String fsclAcid;
    private String docType;
    private String docNum;
    private String loanType;
    private Double loanAmount;
    private Date loanTime;
    private String genType;
    private String progress;
    private String approveUser;
    private Date approveTime;
    private String createUser;
    private Date createDate;
    private String lastModifiedUser;
    private Date lastModifiedDate;
    private String remark;

    
    public String getLoanDocNum() {
		return loanDocNum;
	}

	public void setLoanDocNum(String loanDocNum) {
		this.loanDocNum = loanDocNum;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(String vendeeId) {
        this.vendeeId = vendeeId == null ? null : vendeeId.trim();
    }

    public String getFsclAcid() {
        return fsclAcid;
    }

    public void setFsclAcid(String fsclAcid) {
        this.fsclAcid = fsclAcid == null ? null : fsclAcid.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum == null ? null : docNum.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getGenType() {
        return genType;
    }

    public void setGenType(String genType) {
        this.genType = genType == null ? null : genType.trim();
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser == null ? null : approveUser.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser == null ? null : lastModifiedUser.trim();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}