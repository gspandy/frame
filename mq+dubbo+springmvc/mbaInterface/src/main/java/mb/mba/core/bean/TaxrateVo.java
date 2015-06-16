package mb.mba.core.bean;

import java.math.BigDecimal;

/**
 * 
 * @描述 税率辅助类
 * @author sun
 * @date 2015年6月2日
 */
public class TaxrateVo {

	private boolean all;

	private String unitCode;

	private String relationUnitCode;

	/**
	 * 税率1:货物税
	 */
	private BigDecimal rate1;
	/**
	 * 税率2:服务税
	 */
	private BigDecimal rate2;

	/**
	 * 税率3:预留字段
	 */
	private BigDecimal rate3;

	/**
	 * 税率1占比
	 */
	private BigDecimal rate1Sacle;
	/**
	 * 税率2占比
	 */
	private BigDecimal rate2Sacle;
	/**
	 * 税率3占比
	 */
	private BigDecimal rate3Sacle;

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getRelationUnitCode() {
		return relationUnitCode;
	}

	public void setRelationUnitCode(String relationUnitCode) {
		this.relationUnitCode = relationUnitCode;
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

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public TaxrateVo(boolean all, String unitCode, String relationUnitCode) {
		super();
		this.all = all;
		this.unitCode = unitCode;
		this.relationUnitCode = relationUnitCode;
	}

	public TaxrateVo() {
		super();
	}

}