package mb.erp.dr.soa.vo;

/**
 * 新ERP出库单详情实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-12-04
 * @see         SfGdnDtlVo
 * @since       全流通改造
 */
public class SfGdnDtlVo extends NewBaseBizDtlVo{
	
	private static final long serialVersionUID = -616766314811548684L;

	private Long id;

    private Long sfGdnId;

    private Long sfDgnId;

    private Long sfAsnId;

    private Long sfInboxId;

    private Long prodId;
    
    private Double quantity;

    private Double unitPrice;

    private Double discRate;

    private Double unitAddtCost;

    private Double unitCost;

    private Double oldDiscRate;

    private Double money;

    private String isCostChecked;

    private Long locId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSfGdnId() {
        return sfGdnId;
    }

    public void setSfGdnId(Long sfGdnId) {
        this.sfGdnId = sfGdnId;
    }

    public Long getSfDgnId() {
        return sfDgnId;
    }

    public void setSfDgnId(Long sfDgnId) {
        this.sfDgnId = sfDgnId;
    }

    public Long getSfAsnId() {
        return sfAsnId;
    }

    public void setSfAsnId(Long sfAsnId) {
        this.sfAsnId = sfAsnId;
    }

    public Long getSfInboxId() {
        return sfInboxId;
    }

    public void setSfInboxId(Long sfInboxId) {
        this.sfInboxId = sfInboxId;
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

    public Double getOldDiscRate() {
        return oldDiscRate;
    }

    public void setOldDiscRate(Double oldDiscRate) {
        this.oldDiscRate = oldDiscRate;
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

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }
}