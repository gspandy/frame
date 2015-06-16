package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

public class AgentBrandRateListVo implements Serializable {
	
	private static final long serialVersionUID = 5701711063862580138L;

	private String agentId;

	private String unitId;

	private String brandId;

	private String typeCode;

	private String abrNum;

	private Double discRate;

	private Double markupRate;

	private String crRateFml;

	private String crPrMthd;

	private String createUser;

	private Date createDate;

	private String updateUser;

	private Date updateDate;

	private String remark1;

	private String remark2;

	public String getAbrNum() {
		return abrNum;
	}

	public void setAbrNum(String abrNum) {
		this.abrNum = abrNum == null ? null : abrNum.trim();
	}

	public Double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(Double discRate) {
		this.discRate = discRate;
	}

	public Double getMarkupRate() {
		return markupRate;
	}

	public void setMarkupRate(Double markupRate) {
		this.markupRate = markupRate;
	}

	public String getCrRateFml() {
		return crRateFml;
	}

	public void setCrRateFml(String crRateFml) {
		this.crRateFml = crRateFml == null ? null : crRateFml.trim();
	}

	public String getCrPrMthd() {
		return crPrMthd;
	}

	public void setCrPrMthd(String crPrMthd) {
		this.crPrMthd = crPrMthd == null ? null : crPrMthd.trim();
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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1 == null ? null : remark1.trim();
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2 == null ? null : remark2.trim();
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}