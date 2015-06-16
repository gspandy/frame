package mb.mba.core.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 类描述： 税率种类
 * @author:sun@mb.com
 * @version   
 * @2015年6月5日
 */
public class TaxRateEntity extends BaseEntity {

	private static final long serialVersionUID = 2050713385677115500L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 生效日期
	 */
	private Date startDate;

	/**
	 * 失效日期
	 */
	private Date endDate;

	/**
	 * 税种code
	 */
	private String taxcode;

	/**
	 * 税率
	 */
	private BigDecimal taxrate;

	/**
	 * 税种name
	 */
	private String taxname;

	/**
	 * 是否启用
	 */
	private String isvalid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode == null ? null : taxcode.trim();
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public String getTaxname() {
		return taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname == null ? null : taxname.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}