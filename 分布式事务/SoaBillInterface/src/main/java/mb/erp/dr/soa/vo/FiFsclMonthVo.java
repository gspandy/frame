package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

public class FiFsclMonthVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -64927411354677733L;

	private Long id;

	private Long unitId;

	private Integer yearVal;

	private Integer monthVal;

	private Date fromDate;

	private Date untilDate;

	private String remark;

	private Date lastModifiedDate;

	private Long costGrpId;

	private String isClosed;

	private String isCheckout;

	private Date lastCostTime;

	private Date lastCaTime;

	private Date lastUnCaTime;

	private String lastCostUser;

	private String lastCaUser;

	private String lastUnCaUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Integer getYearVal() {
		return yearVal;
	}

	public void setYearVal(Integer yearVal) {
		this.yearVal = yearVal;
	}

	public Integer getMonthVal() {
		return monthVal;
	}

	public void setMonthVal(Integer monthVal) {
		this.monthVal = monthVal;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getUntilDate() {
		return untilDate;
	}

	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getCostGrpId() {
		return costGrpId;
	}

	public void setCostGrpId(Long costGrpId) {
		this.costGrpId = costGrpId;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed == null ? null : isClosed.trim();
	}

	public String getIsCheckout() {
		return isCheckout;
	}

	public void setIsCheckout(String isCheckout) {
		this.isCheckout = isCheckout == null ? null : isCheckout.trim();
	}

	public Date getLastCostTime() {
		return lastCostTime;
	}

	public void setLastCostTime(Date lastCostTime) {
		this.lastCostTime = lastCostTime;
	}

	public Date getLastCaTime() {
		return lastCaTime;
	}

	public void setLastCaTime(Date lastCaTime) {
		this.lastCaTime = lastCaTime;
	}

	public Date getLastUnCaTime() {
		return lastUnCaTime;
	}

	public void setLastUnCaTime(Date lastUnCaTime) {
		this.lastUnCaTime = lastUnCaTime;
	}

	public String getLastCostUser() {
		return lastCostUser;
	}

	public void setLastCostUser(String lastCostUser) {
		this.lastCostUser = lastCostUser == null ? null : lastCostUser.trim();
	}

	public String getLastCaUser() {
		return lastCaUser;
	}

	public void setLastCaUser(String lastCaUser) {
		this.lastCaUser = lastCaUser == null ? null : lastCaUser.trim();
	}

	public String getLastUnCaUser() {
		return lastUnCaUser;
	}

	public void setLastUnCaUser(String lastUnCaUser) {
		this.lastUnCaUser = lastUnCaUser == null ? null : lastUnCaUser.trim();
	}
}