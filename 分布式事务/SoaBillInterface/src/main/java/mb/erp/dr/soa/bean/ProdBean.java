package mb.erp.dr.soa.bean;

import java.io.Serializable;

/**
 * 商品bean
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         ProdBean
 * @since       全流通改造
 */
public class ProdBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6489817599761000305L;
	
	private String prodId ;// 商品编码
	private Double qty; // 数量
	private String warehId; // 仓库id 保存到临时表做批量插入时用到
	private String locId ; // 货位编码
	private String unitId ; // 组织id
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	public String getWarehId() {
		return warehId;
	}
	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}
