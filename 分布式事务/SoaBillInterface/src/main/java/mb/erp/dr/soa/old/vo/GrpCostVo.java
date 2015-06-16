package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 成本价格vo
 * 
 * @author 郭明帅
 * @version 1.0, 2014-10-31
 * @see GrpCostVo
 * @since 全流通改造
 */
public class GrpCostVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 2905521189408697340L;

	private String unitId; // 供货方编码

	private String costGrpId; // 成本组编码

	private String prodId; // 商品编码

	private Double unitCost; // 单位成本

	private Double stock; // 当前库存

	private Date calcAt; // 核算日期

	private Double initUnitCost; // 期初成本

	private Double initStk; // 期初库存

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Date getCalcAt() {
		return calcAt;
	}

	public void setCalcAt(Date calcAt) {
		this.calcAt = calcAt;
	}

	public Double getInitUnitCost() {
		return initUnitCost;
	}

	public void setInitUnitCost(Double initUnitCost) {
		this.initUnitCost = initUnitCost;
	}

	public Double getInitStk() {
		return initStk;
	}

	public void setInitStk(Double initStk) {
		this.initStk = initStk;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCostGrpId() {
		return costGrpId;
	}

	public void setCostGrpId(String costGrpId) {
		this.costGrpId = costGrpId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
}