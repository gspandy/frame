package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库库库存表
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehProdVo
 * @since       全流通改造
 */
public class WarehProdVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3262246190380351734L;
	
	private String warehId ; // 仓库编码
	private String prodId ; // 商品编码 
	private Double stkOnHand ; // 实物库存
	private Double qtyOnOrder ; // 在购库存
	private Double qtyInTransit ; // 在途库存
	private Double qtyCommitted ; // 已分配库存 
	private Double qtyInDoubt ; // 在售库存
	private Double stkPublished ; // 发布库存
	private Double minStk ; // 最小库存
	private Double maxStk ; // 最大库存
	private Double alertMinStk ; // 最小库存预警值
	private Double alertMaxStk ; // 最大库存预警值
	private Double minAdStk ; // 最小发布库存
	private Double maxAdStk ; // 最大发布库存
	private String dfltZoneId ; // 默认所属区域
	private Integer stdLocCap ; // 默认货位存放量
	private String dfltLocId ; // 默认货位
	private Double qtyFucComm ; // 期货预留库存
	private Double stkJustTime ; // 库存调整量
	private Double qtyCurComm ; // 现货预留库存
	private Date stkChangeDate ; // 库存变动时间 
	private Double bgrStk ; // 退货量
	private Double inRcvStk ; // 接收库存
	private Double lockedQty ; // 锁定库存
	private Double lockStockin ; // 锁定仓库库存
	private Double stockinFree ; // 锁定库存自由量
	private Double reservedQty ; // 预留库存

    public String getWarehId() {
		return warehId;
	}

	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Double getStkOnHand() {
        return stkOnHand;
    }

    public void setStkOnHand(Double stkOnHand) {
        this.stkOnHand = stkOnHand;
    }

    public Double getQtyOnOrder() {
        return qtyOnOrder;
    }

    public void setQtyOnOrder(Double qtyOnOrder) {
        this.qtyOnOrder = qtyOnOrder;
    }

    public Double getQtyInTransit() {
        return qtyInTransit;
    }

    public void setQtyInTransit(Double qtyInTransit) {
        this.qtyInTransit = qtyInTransit;
    }

    public Double getQtyCommitted() {
        return qtyCommitted;
    }

    public void setQtyCommitted(Double qtyCommitted) {
        this.qtyCommitted = qtyCommitted;
    }

    public Double getQtyInDoubt() {
        return qtyInDoubt;
    }

    public void setQtyInDoubt(Double qtyInDoubt) {
        this.qtyInDoubt = qtyInDoubt;
    }

    public Double getStkPublished() {
        return stkPublished;
    }

    public void setStkPublished(Double stkPublished) {
        this.stkPublished = stkPublished;
    }

    public Double getMinStk() {
        return minStk;
    }

    public void setMinStk(Double minStk) {
        this.minStk = minStk;
    }

    public Double getMaxStk() {
        return maxStk;
    }

    public void setMaxStk(Double maxStk) {
        this.maxStk = maxStk;
    }

    public Double getAlertMinStk() {
        return alertMinStk;
    }

    public void setAlertMinStk(Double alertMinStk) {
        this.alertMinStk = alertMinStk;
    }

    public Double getAlertMaxStk() {
        return alertMaxStk;
    }

    public void setAlertMaxStk(Double alertMaxStk) {
        this.alertMaxStk = alertMaxStk;
    }

    public Double getMinAdStk() {
        return minAdStk;
    }

    public void setMinAdStk(Double minAdStk) {
        this.minAdStk = minAdStk;
    }

    public Double getMaxAdStk() {
        return maxAdStk;
    }

    public void setMaxAdStk(Double maxAdStk) {
        this.maxAdStk = maxAdStk;
    }

    public String getDfltZoneId() {
        return dfltZoneId;
    }

    public void setDfltZoneId(String dfltZoneId) {
        this.dfltZoneId = dfltZoneId == null ? null : dfltZoneId.trim();
    }

    public Integer getStdLocCap() {
        return stdLocCap;
    }

    public void setStdLocCap(Integer stdLocCap) {
        this.stdLocCap = stdLocCap;
    }

    public String getDfltLocId() {
        return dfltLocId;
    }

    public void setDfltLocId(String dfltLocId) {
        this.dfltLocId = dfltLocId == null ? null : dfltLocId.trim();
    }

    public Double getQtyFucComm() {
        return qtyFucComm;
    }

    public void setQtyFucComm(Double qtyFucComm) {
        this.qtyFucComm = qtyFucComm;
    }

    public Double getStkJustTime() {
        return stkJustTime;
    }

    public void setStkJustTime(Double stkJustTime) {
        this.stkJustTime = stkJustTime;
    }

    public Double getQtyCurComm() {
        return qtyCurComm;
    }

    public void setQtyCurComm(Double qtyCurComm) {
        this.qtyCurComm = qtyCurComm;
    }

    public Date getStkChangeDate() {
        return stkChangeDate;
    }

    public void setStkChangeDate(Date stkChangeDate) {
        this.stkChangeDate = stkChangeDate;
    }

    public Double getBgrStk() {
        return bgrStk;
    }

    public void setBgrStk(Double bgrStk) {
        this.bgrStk = bgrStk;
    }

    public Double getInRcvStk() {
        return inRcvStk;
    }

    public void setInRcvStk(Double inRcvStk) {
        this.inRcvStk = inRcvStk;
    }

    public Double getLockedQty() {
        return lockedQty;
    }

    public void setLockedQty(Double lockedQty) {
        this.lockedQty = lockedQty;
    }

    public Double getLockStockin() {
        return lockStockin;
    }

    public void setLockStockin(Double lockStockin) {
        this.lockStockin = lockStockin;
    }

    public Double getStockinFree() {
        return stockinFree;
    }

    public void setStockinFree(Double stockinFree) {
        this.stockinFree = stockinFree;
    }

    public Double getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(Double reservedQty) {
        this.reservedQty = reservedQty;
    }
}