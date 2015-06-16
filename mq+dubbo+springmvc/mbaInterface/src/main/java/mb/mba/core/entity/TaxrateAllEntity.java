package mb.mba.core.entity;

import java.math.BigDecimal;
/**
 * 
 * 类描述： 组织全局税率
 * @author:sun@mb.com
 * @version   
 * @2015年6月5日
 */
public class TaxrateAllEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 组织id
	 */
	private String unitCode;

	/**
	 * 范围
	 */
	private String scope;

	/**
	 * 税率
	 */
	private BigDecimal rate;

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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode == null ? null : unitCode.trim();
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope == null ? null : scope.trim();
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}

}