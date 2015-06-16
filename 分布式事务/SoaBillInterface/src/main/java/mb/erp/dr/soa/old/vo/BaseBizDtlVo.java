package mb.erp.dr.soa.old.vo;

import java.io.Serializable;

/**
 * 表单详情信息 - 基类vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BaseBizDtlVo
 * @since       全流通改造
 */
public class BaseBizDtlVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6683339674675349287L;
	
	private String prodId; // 商品编码
	private Double unitPrice; // 单价
	private Double discRate; // 折率
    private String locId; //发货货位编码
    private String rcptLocId; //收货货位编码
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getDiscRate() {
		return discRate;
	}
	public void setDiscRate(Double discRate) {
		this.discRate = discRate;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public String getRcptLocId() {
		return rcptLocId;
	}
	public void setRcptLocId(String rcptLocId) {
		this.rcptLocId = rcptLocId;
	}
	
}
