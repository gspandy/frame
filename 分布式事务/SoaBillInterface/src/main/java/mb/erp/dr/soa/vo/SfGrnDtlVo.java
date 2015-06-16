package mb.erp.dr.soa.vo;

/**
 * 新ERP入库单明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-08
 * @see         SfGrnDtlVo
 * @since       全流通改造
 */
public class SfGrnDtlVo extends NewBaseBizDtlVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3506369028875920199L;

	private Long id;

    private Long sfGrnId;

    private Double quantity;

    private Double unitAddtCost;

    private Double unitCost;

    private String remark;

    private Double oldDiscRate;

    private Double preQty;

    private Double delivQty;

    private Double money;

    private String isCostChecked;

    private Long rcptLocId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSfGrnId() {
        return sfGrnId;
    }

    public void setSfGrnId(Long sfGrnId) {
        this.sfGrnId = sfGrnId;
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

    public Double getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(Double delivQty) {
        this.delivQty = delivQty;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getIsCostChecked() {
        return isCostChecked;
    }

    public void setIsCostChecked(String isCostChecked) {
        this.isCostChecked = isCostChecked == null ? null : isCostChecked.trim();
    }

    public Long getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(Long rcptLocId) {
        this.rcptLocId = rcptLocId;
    }
}