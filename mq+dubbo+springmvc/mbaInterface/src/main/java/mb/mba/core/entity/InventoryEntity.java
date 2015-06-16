package mb.mba.core.entity;

import java.math.BigDecimal;

/**
 * 
 * 类描述： 库存
 * 
 * @author:sun@mb.com
 * @version
 * @2015年6月5日
 */
public class InventoryEntity extends BaseEntity {
	private static final long serialVersionUID = -6940885167854477932L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 仓库id,统一为仓库code
	 */
	private String warehCode;

	/**
	 * 商品编码
	 */
	private String goodsCode;

	/**
	 * 批次信息
	 */
	private String bathInfo;

	/**
	 * 在仓库存
	 */
	private BigDecimal inventoryQuantity;

	/**
	 * 调拨在途
	 */
	private BigDecimal alloOntheway;

	/**
	 * 在购在途
	 */
	private BigDecimal purchOntheway;

	/**
	 * 库存地
	 */
	private String inventoryPlace;

	/**
	 * 记账仓
	 */
	private String acwarehCode;

	/**
	 * 记账仓name
	 */
	private String acwarehName;

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

	public String getWarehCode() {
		return warehCode;
	}

	public void setWarehCode(String warehCode) {
		this.warehCode = warehCode == null ? null : warehCode.trim();
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode == null ? null : goodsCode.trim();
	}

	public String getBathInfo() {
		return bathInfo;
	}

	public void setBathInfo(String bathInfo) {
		this.bathInfo = bathInfo == null ? null : bathInfo.trim();
	}

	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public BigDecimal getAlloOntheway() {
		return alloOntheway;
	}

	public void setAlloOntheway(BigDecimal alloOntheway) {
		this.alloOntheway = alloOntheway;
	}

	public BigDecimal getPurchOntheway() {
		return purchOntheway;
	}

	public void setPurchOntheway(BigDecimal purchOntheway) {
		this.purchOntheway = purchOntheway;
	}

	public String getInventoryPlace() {
		return inventoryPlace;
	}

	public void setInventoryPlace(String inventoryPlace) {
		this.inventoryPlace = inventoryPlace == null ? null : inventoryPlace
				.trim();
	}

	public String getAcwarehCode() {
		return acwarehCode;
	}

	public void setAcwarehCode(String acwarehCode) {
		this.acwarehCode = acwarehCode == null ? null : acwarehCode.trim();
	}

	public String getAcwarehName() {
		return acwarehName;
	}

	public void setAcwarehName(String acwarehName) {
		this.acwarehName = acwarehName == null ? null : acwarehName.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}