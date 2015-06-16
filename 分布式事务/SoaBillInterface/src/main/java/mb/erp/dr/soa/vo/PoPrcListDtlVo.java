package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

public class PoPrcListDtlVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -8817073270409158233L;

	private Long id;

	private Long vendeeId;

	private Long venderId;

	private String prcLstType;

	private String currency;

	private Long prodId;

	private Double price;

	private Date createDate;

	private Date lastModifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(Long vendeeId) {
		this.vendeeId = vendeeId;
	}

	public Long getVenderId() {
		return venderId;
	}

	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}

	public String getPrcLstType() {
		return prcLstType;
	}

	public void setPrcLstType(String prcLstType) {
		this.prcLstType = prcLstType == null ? null : prcLstType.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}