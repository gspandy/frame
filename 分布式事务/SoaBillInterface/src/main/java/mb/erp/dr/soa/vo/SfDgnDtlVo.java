package mb.erp.dr.soa.vo;

/**
 * 新ERP交货单明细实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfDgnDtlVo
 * @since       全流通改造
 */
public class SfDgnDtlVo extends NewBaseBizDtlVo{
	private static final long serialVersionUID = 9094057117535182366L;

	private Long id; //内码

    private Long sfDgnId; //外键ID

    private Long prodId; //商品ID
    
    private Double quantity; //数量

    private Double deliveredQua; //已分配数量

    private Double assignedQua; //集货数量

    private Double unitPrice; //单价

    private Double discRate; //折率

    private Double unitAddtCost; //附加成本

    private Double unitCost; //单位成本

    private Double oldDiscRate; //原始折率

    private String remark; //备注

    private Double outQty;

    private Double pickedQty;

    private Long locId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSfDgnId() {
        return sfDgnId;
    }

    public void setSfDgnId(Long sfDgnId) {
        this.sfDgnId = sfDgnId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getDeliveredQua() {
        return deliveredQua;
    }

    public void setDeliveredQua(Double deliveredQua) {
        this.deliveredQua = deliveredQua;
    }

    public Double getAssignedQua() {
        return assignedQua;
    }

    public void setAssignedQua(Double assignedQua) {
        this.assignedQua = assignedQua;
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

    public Double getOldDiscRate() {
        return oldDiscRate;
    }

    public void setOldDiscRate(Double oldDiscRate) {
        this.oldDiscRate = oldDiscRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getOutQty() {
        return outQty;
    }

    public void setOutQty(Double outQty) {
        this.outQty = outQty;
    }

    public Double getPickedQty() {
        return pickedQty;
    }

    public void setPickedQty(Double pickedQty) {
        this.pickedQty = pickedQty;
    }

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

}