package mb.mba.core.entity;

import java.math.BigDecimal;
/**
 * 
 * 类描述： 组织对贸易伙伴税率
 * @author:sun@mb.com
 * @version   
 * @2015年6月5日
 */
public class TaxratePerEntity extends BaseEntity {

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
	 * 往来方组织id
	 */
	private String relationUnitCode;

	/**
	 * 税率1
	 */
	private BigDecimal rate1;

	/**
	 * 税率2
	 */
	private BigDecimal rate2;

	/**
	 * 税率3
	 */
	private BigDecimal rate3;

	/**
	 * 税率1比例
	 */
	private BigDecimal rate1Sacle;

	/**
	 * 税率2比例
	 */
	private BigDecimal rate2Sacle;

	/**
	 * 税率3比例
	 */
	private BigDecimal rate3Sacle;

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

	public String getRelationUnitCode() {
		return relationUnitCode;
	}

	public void setRelationUnitCode(String relationUnitCode) {
		this.relationUnitCode = relationUnitCode == null ? null
				: relationUnitCode.trim();
	}

	public BigDecimal getRate1() {
		return rate1;
	}

	public void setRate1(BigDecimal rate1) {
		this.rate1 = rate1;
	}

	public BigDecimal getRate2() {
		return rate2;
	}

	public void setRate2(BigDecimal rate2) {
		this.rate2 = rate2;
	}

	public BigDecimal getRate3() {
		return rate3;
	}

	public void setRate3(BigDecimal rate3) {
		this.rate3 = rate3;
	}

	public BigDecimal getRate1Sacle() {
		return rate1Sacle;
	}

	public void setRate1Sacle(BigDecimal rate1Sacle) {
		this.rate1Sacle = rate1Sacle;
	}

	public BigDecimal getRate2Sacle() {
		return rate2Sacle;
	}

	public void setRate2Sacle(BigDecimal rate2Sacle) {
		this.rate2Sacle = rate2Sacle;
	}

	public BigDecimal getRate3Sacle() {
		return rate3Sacle;
	}

	public void setRate3Sacle(BigDecimal rate3Sacle) {
		this.rate3Sacle = rate3Sacle;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}