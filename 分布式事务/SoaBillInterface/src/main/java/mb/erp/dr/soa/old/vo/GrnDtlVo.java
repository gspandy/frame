package mb.erp.dr.soa.old.vo;

/**
 * 入库单明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GrnDtlVo
 * @since       全流通改造
 */
public class GrnDtlVo extends BaseBizDtlVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8237426968995337781L;
	
	private String unitId ; // 组织编码
	private String grnNum ; // 入库单编号
	private Double quantity ; // 数量
	private Double unitAddtCost ; // 单位附加成本
	private Double unitCost ; // 成本
	private String remark ; // 备注
	private Double oldDiscRate ; // 原折率
	private Double preQty ; // 预分拣数量
	private String remark1 ; // 扩展备注1
	private Double delivQty ; // 发货数量
	private String lineNo ; // 行号
	private Double amount ; // 金额
	private String rcptLocId ; // 收货货位

    public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getGrnNum() {
		return grnNum;
	}

	public void setGrnNum(String grnNum) {
		this.grnNum = grnNum;
	}

	public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    
    public Double getUnitAddtCost() {
        return unitAddtCost;
    }

    public void setUnitAddtCost(Double unitAddtCost) {
        this.unitAddtCost = unitAddtCost;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getOldDiscRate() {
        return oldDiscRate;
    }

    public void setOldDiscRate(Double oldDiscRate) {
        this.oldDiscRate = oldDiscRate;
    }

    public Double getPreQty() {
        return preQty;
    }

    public void setPreQty(Double preQty) {
        this.preQty = preQty;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public Double getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(Double delivQty) {
        this.delivQty = delivQty;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(String rcptLocId) {
        this.rcptLocId = rcptLocId == null ? null : rcptLocId.trim();
    }
}