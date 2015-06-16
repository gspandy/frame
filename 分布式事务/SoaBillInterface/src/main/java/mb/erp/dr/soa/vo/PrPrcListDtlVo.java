package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP商品零售价格实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-11-17
 * @see PrPrcListDtlVo
 * @since 全流通改造
 */
public class PrPrcListDtlVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -1126679052612311208L;

	private Long id;

	private Long prPrnId;

	private Long unitId;

	private String prcLstType;

	private String currency;

	private Long prodId;

	private String prcPlcy;

	private Double price;

	private Double discRate;

	private Date effDate;

	private Long ownerId;

	private Date execDate;

	private String prodType;

	private String prodColorNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrPrnId() {
		return prPrnId;
	}

	public void setPrPrnId(Long prPrnId) {
		this.prPrnId = prPrnId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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

	public String getPrcPlcy() {
		return prcPlcy;
	}

	public void setPrcPlcy(String prcPlcy) {
		this.prcPlcy = prcPlcy == null ? null : prcPlcy.trim();
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(Double discRate) {
		this.discRate = discRate;
	}

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Date getExecDate() {
		return execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType == null ? null : prodType.trim();
	}

	public String getProdColorNum() {
		return prodColorNum;
	}

	public void setProdColorNum(String prodColorNum) {
		this.prodColorNum = prodColorNum == null ? null : prodColorNum.trim();
	}
}