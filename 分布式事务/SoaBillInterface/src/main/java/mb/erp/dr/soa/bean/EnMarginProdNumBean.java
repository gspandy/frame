package mb.erp.dr.soa.bean;

import java.io.Serializable;

/**
 * 成本核算参数bean
 * 
 * @author 陈志杰
 * @version 1.0, 2015-02-05
 * @see EnMarginProdNumBean
 * @since 全流通改造
 */
public class EnMarginProdNumBean implements Serializable {

	private static final long serialVersionUID = 3242147348696745038L;
	private String prodNum;
	private Integer quantity;
	private Long costGrpId;
	private String yearMonth;
	private Double cost;
	private Double billUnitCost;

	public String getProdNum() {
		return prodNum;
	}

	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getCostGrpId() {
		return costGrpId;
	}

	public void setCostGrpId(Long costGrpId) {
		this.costGrpId = costGrpId;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getBillUnitCost() {
		return billUnitCost;
	}

	public void setBillUnitCost(Double billUnitCost) {
		this.billUnitCost = billUnitCost;
	}
}
