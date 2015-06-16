package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP单据流实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-12-10
 * @see SfDocFlowVo
 * @since 全流通改造
 */
public class SfDocFlowVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 7351288514874839772L;

	private Long id;

	private String srcDocType;

	private String srcUnitCode;

	private String srcDocCode;

	private Integer docFlowState;

	private String docType;

	private String unitCode;

	private String docCode;

	private String operUser;

	private Date operDate;

	private String dataSource;

	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSrcDocType() {
		return srcDocType;
	}

	public void setSrcDocType(String srcDocType) {
		this.srcDocType = srcDocType == null ? null : srcDocType.trim();
	}

	public String getSrcUnitCode() {
		return srcUnitCode;
	}

	public void setSrcUnitCode(String srcUnitCode) {
		this.srcUnitCode = srcUnitCode == null ? null : srcUnitCode.trim();
	}

	public String getSrcDocCode() {
		return srcDocCode;
	}

	public void setSrcDocCode(String srcDocCode) {
		this.srcDocCode = srcDocCode == null ? null : srcDocCode.trim();
	}

	public Integer getDocFlowState() {
		return docFlowState;
	}

	public void setDocFlowState(Integer docFlowState) {
		this.docFlowState = docFlowState;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType == null ? null : docType.trim();
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode == null ? null : unitCode.trim();
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode == null ? null : docCode.trim();
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser == null ? null : operUser.trim();
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource == null ? null : dataSource.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}