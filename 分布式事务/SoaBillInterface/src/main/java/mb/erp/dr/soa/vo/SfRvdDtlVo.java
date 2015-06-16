package mb.erp.dr.soa.vo;


/**
 * 新ERP到货通知单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfRvdDtlVo
 * @since       全流通改造
 */
public class SfRvdDtlVo extends NewBaseBizDtlVo{
	private static final long serialVersionUID = -7343306699830242020L;

	private Long id; //内码

    private Long sfRvdId; //到货通知单ID

    private Long prodId; //商品ID

    private Double quantity; //实收数量

    private Double unitPrice; //单价

    private Double discRate; //折率

    private Double unitAddtCost; //单位附加成本

    private Double unitCost; //单位成本

    private String remark; //备注

    private Double oldDiscRate; //原折率

    private Double preQty; //预计数量

    private Double delivQty; //发货数量

    private String lineNo; //行号

    private Double rvdQty; //入库数量

    private Double rcvQty; //

    private Double ttlVal; //总金额

    private Double newDiscRate; //优惠折率

    private Double idtfQlgdQty; //正品数量

    private Double idtfFxrqQty; //可处理品数量

    private Double idtfDmgdQty; //污损品数量

    private Double idtfInfrQty; //次品数量

    private Double idtfPassedQty; //正品超量

    private Double idtfLackQty; //少配件品

    private Double pkQty; //PK数量

    private Double chQty; //初核数量

    private Double accQty; //接收数量

    private String isAdd; //是否在分类扫描期间多出的商品

    private Double idtfQlgdPassedQty; //正品拒收数量

    private Double idtfFxrqPassedQty; //清洗品拒收数量

    private Long rcptLocId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSfRvdId() {
        return sfRvdId;
    }

    public void setSfRvdId(Long sfRvdId) {
        this.sfRvdId = sfRvdId;
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

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public Double getRvdQty() {
        return rvdQty;
    }

    public void setRvdQty(Double rvdQty) {
        this.rvdQty = rvdQty;
    }

    public Double getRcvQty() {
        return rcvQty;
    }

    public void setRcvQty(Double rcvQty) {
        this.rcvQty = rcvQty;
    }

    public Double getTtlVal() {
        return ttlVal;
    }

    public void setTtlVal(Double ttlVal) {
        this.ttlVal = ttlVal;
    }

    public Double getNewDiscRate() {
        return newDiscRate;
    }

    public void setNewDiscRate(Double newDiscRate) {
        this.newDiscRate = newDiscRate;
    }

    public Double getIdtfQlgdQty() {
        return idtfQlgdQty;
    }

    public void setIdtfQlgdQty(Double idtfQlgdQty) {
        this.idtfQlgdQty = idtfQlgdQty;
    }

    public Double getIdtfFxrqQty() {
        return idtfFxrqQty;
    }

    public void setIdtfFxrqQty(Double idtfFxrqQty) {
        this.idtfFxrqQty = idtfFxrqQty;
    }

    public Double getIdtfDmgdQty() {
        return idtfDmgdQty;
    }

    public void setIdtfDmgdQty(Double idtfDmgdQty) {
        this.idtfDmgdQty = idtfDmgdQty;
    }

    public Double getIdtfInfrQty() {
        return idtfInfrQty;
    }

    public void setIdtfInfrQty(Double idtfInfrQty) {
        this.idtfInfrQty = idtfInfrQty;
    }

    public Double getIdtfPassedQty() {
        return idtfPassedQty;
    }

    public void setIdtfPassedQty(Double idtfPassedQty) {
        this.idtfPassedQty = idtfPassedQty;
    }

    public Double getIdtfLackQty() {
        return idtfLackQty;
    }

    public void setIdtfLackQty(Double idtfLackQty) {
        this.idtfLackQty = idtfLackQty;
    }

    public Double getPkQty() {
        return pkQty;
    }

    public void setPkQty(Double pkQty) {
        this.pkQty = pkQty;
    }

    public Double getChQty() {
        return chQty;
    }

    public void setChQty(Double chQty) {
        this.chQty = chQty;
    }

    public Double getAccQty() {
        return accQty;
    }

    public void setAccQty(Double accQty) {
        this.accQty = accQty;
    }

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd == null ? null : isAdd.trim();
    }

    public Double getIdtfQlgdPassedQty() {
        return idtfQlgdPassedQty;
    }

    public void setIdtfQlgdPassedQty(Double idtfQlgdPassedQty) {
        this.idtfQlgdPassedQty = idtfQlgdPassedQty;
    }

    public Double getIdtfFxrqPassedQty() {
        return idtfFxrqPassedQty;
    }

    public void setIdtfFxrqPassedQty(Double idtfFxrqPassedQty) {
        this.idtfFxrqPassedQty = idtfFxrqPassedQty;
    }

    public Long getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(Long rcptLocId) {
        this.rcptLocId = rcptLocId;
    }
}