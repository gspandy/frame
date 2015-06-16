package mb.mba.core.entity;

import java.util.Date;

/**
 * 类描述： 成本组会计期间表
 * @author:陈志杰
 * @version   
 * @2015年6月5日           
 */
public class CostAccountingPeriodEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9203120816536000833L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 成本组id
	 */
	private Long costId;

	/**
	 * 成本组名称
	 */
	private String costAccountingName;

	/**
	 * 组织code
	 */
	private String unitCode;

	/**
	 * 会计年度
	 */
	private String acYear;

	/**
	 * 会计月份
	 */
	private String acMonth;

	/**
	 * 会计帐期开始日期
	 */
	private Date startDate;

	/**
	 * 会计帐期结束日期
	 */
	private Date endDate;

	/**
	 * 会计帐期状态
	 */
	private String acStatus;

	/**
	 * 是否成本结帐
	 */
	private String acCheckout;

	/**
	 * 是否有效
	 */
	private String isvalid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public String getCostAccountingName() {
		return costAccountingName;
	}

	public void setCostAccountingName(String costAccountingName) {
		this.costAccountingName = costAccountingName == null ? null
				: costAccountingName.trim();
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode == null ? null : unitCode.trim();
	}

	public String getAcYear() {
		return acYear;
	}

	public void setAcYear(String acYear) {
		this.acYear = acYear == null ? null : acYear.trim();
	}

	public String getAcMonth() {
		return acMonth;
	}

	public void setAcMonth(String acMonth) {
		this.acMonth = acMonth == null ? null : acMonth.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAcStatus() {
		return acStatus;
	}

	public void setAcStatus(String acStatus) {
		this.acStatus = acStatus == null ? null : acStatus.trim();
	}

	public String getAcCheckout() {
		return acCheckout;
	}

	public void setAcCheckout(String acCheckout) {
		this.acCheckout = acCheckout == null ? null : acCheckout.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}