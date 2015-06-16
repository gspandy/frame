package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP成本价格实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-11-17
 * @see FiGrpCostVo
 * @since 全流通改造
 */
public class FiGrpCostVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 368745043076207131L;

	private Long id;

	private Long costGrpId;

	private Long prodId;

	private Double unitCost;

	private Double stock;

	private Date calcAt;

	private Double initUnitCost;

	private Double initStk;

	private String calcMargin;

	private String prodNum;

	private Double moveTtlCost;

	private String opMode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCostGrpId() {
		return costGrpId;
	}

	public void setCostGrpId(Long costGrpId) {
		this.costGrpId = costGrpId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

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

	public String getCalcMargin() {
		return calcMargin;
	}

	public void setCalcMargin(String calcMargin) {
		this.calcMargin = calcMargin == null ? null : calcMargin.trim();
	}

	public String getProdNum() {
		return prodNum;
	}

	public void setProdNum(String prodNum) {
		this.prodNum = prodNum == null ? null : prodNum.trim();
	}

	public Double getMoveTtlCost() {
		return moveTtlCost;
	}

	public void setMoveTtlCost(Double moveTtlCost) {
		this.moveTtlCost = moveTtlCost;
	}

	public String getOpMode() {
		return opMode;
	}

	public void setOpMode(String opMode) {
		this.opMode = opMode == null ? null : opMode.trim();
	}
}