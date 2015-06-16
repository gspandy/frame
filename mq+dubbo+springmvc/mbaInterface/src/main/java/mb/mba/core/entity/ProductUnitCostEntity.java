package mb.mba.core.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 类描述：商品成本表 
 * @author:陈志杰
 * @version   
 * @2015年6月5日
 */
public class ProductUnitCostEntity extends BaseEntity {

	private static final long serialVersionUID = -8444358479220671156L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 成本组id
	 */
	private Long costId;

	/**
	 * 商品编码
	 */
	private String prodNum;

	/**
	 * 期初成本
	 */
	private BigDecimal initUnitCost;

	/**
	 * 期末成本
	 */
	private BigDecimal unitCost;

	/**
	 * 商品期初数量
	 */
	private Integer initStock;

	/**
	 * 商品期末数量
	 */
	private Integer stock;

	/**
	 * 成本核算时间
	 */
	private Date calcTime;

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

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public String getProdNum() {
		return prodNum;
	}

	public void setProdNum(String prodNum) {
		this.prodNum = prodNum == null ? null : prodNum.trim();
	}

	public BigDecimal getInitUnitCost() {
		return initUnitCost;
	}

	public void setInitUnitCost(BigDecimal initUnitCost) {
		this.initUnitCost = initUnitCost;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public Integer getInitStock() {
		return initStock;
	}

	public void setInitStock(Integer initStock) {
		this.initStock = initStock;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Date getCalcTime() {
		return calcTime;
	}

	public void setCalcTime(Date calcTime) {
		this.calcTime = calcTime;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
}