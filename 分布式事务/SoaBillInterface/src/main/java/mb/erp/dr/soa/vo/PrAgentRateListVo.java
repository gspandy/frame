package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

public class PrAgentRateListVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -1604817827370596523L;

	private Long id;

	private Long agentId;

	private Long unitId;

	private Long prodLayerId;

	private String typeCode;

	private String code;

	private Double discRate;

	private Double markupRate;

	private String crRateFml;

	private String crPrMthd;

	private Double docState;

	private String createUser;

	private Date createDate;

	private String lastModifiedUser;

	private Date lastModifiedDate;

	private String remark;

	private String prodType;

	private String listType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getProdLayerId() {
		return prodLayerId;
	}

	public void setProdLayerId(Long prodLayerId) {
		this.prodLayerId = prodLayerId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode == null ? null : typeCode.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
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

	public Double getDocState() {
		return docState;
	}

	public void setDocState(Double docState) {
		this.docState = docState;
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
		this.lastModifiedUser = lastModifiedUser == null ? null
				: lastModifiedUser.trim();
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

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType == null ? null : prodType.trim();
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType == null ? null : listType.trim();
	}
}