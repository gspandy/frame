package mb.erp.dr.soa.vo;

import java.io.Serializable;

public class NewBaseBizDtlVo implements Serializable{
	
	private static final long serialVersionUID = 5058779052521599484L;
	
	private Long prodId; // 商品编码
	private Double unitPrice; // 单价
	private Double discRate; // 折率
	
	// 以下字段方便验证处理
    private String prodCode ; // 商品code
    private String locCode; //发货货位编码
    private String rcptLocCode; //收货货位编码
	
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
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
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getRcptLocCode() {
		return rcptLocCode;
	}
	public void setRcptLocCode(String rcptLocCode) {
		this.rcptLocCode = rcptLocCode;
	}
	public String getLocCode() {
		return locCode;
	}
	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}
	
}
