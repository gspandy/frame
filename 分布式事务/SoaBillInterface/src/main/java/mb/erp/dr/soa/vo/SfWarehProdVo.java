package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP 仓库商品
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-17
 * @see         SfWarehProdVo
 * @since       全流通改造
 */
public class SfWarehProdVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5718176408200670817L;
	
	private Long id;	//	内码
    private Long bfOrgId;	//	仓库ID
    private Long bfProductId;	//	商品ID
    private Double stkOnHand;	//	实际库存
    private Double qtyOnOrder;	//	在购库存
    private Double qtyInTransit;	//	在途库存（直营）
    private Double qtyCommitted;	//	已分配库存
    private Double qtyInDoubt;	//	待处理库存
    private Double stkPublished;	//	公布库存
    private Double minStk;	//	库存下限
    private Double maxStk;	//	库存上限
    private Double alertMinStk;	//	库存报警下限
    private Double alertMaxStk;	//	库存报警上限
    private Double minAdStk;	//	配货库存下限
    private Double maxAdStk;	//	配货库存上限
    private Double sfWarehouseLocId;	//	缺省存货货位ID
    private Integer stdLocCap;	//	标准货位容量
    private Double stkJustTime;	//	STK_JUST_TIME
    private Double qtyCurComm;	//	现货预定量
    private Double qtyFucComm;	//	期货预定量
    private String qtyType;	//	库存类型
    private Date lastModifiedDate;	//	最后修改时间
    private Double qtyOnLock;	//	锁定库存
    private Double inRcvStk;	//	退货待入库库存
    private Double reservedCommittedQty;	//	预留量已分配量
    private Double qtyInTransitAg;	//	在途库存（加盟）
    private Double curCost;	//	
    private Double lockStockin;	//	入库锁定量
    private Double reservedQty;	//	已预留量
    private Double stockinFree;	//	入库锁定转自由量
    private Double lockedQty;	//	盘点锁定库存


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBfOrgId() {
        return bfOrgId;
    }

    public void setBfOrgId(Long bfOrgId) {
        this.bfOrgId = bfOrgId;
    }

    public Long getBfProductId() {
        return bfProductId;
    }

    public void setBfProductId(Long bfProductId) {
        this.bfProductId = bfProductId;
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

    public Double getSfWarehouseLocId() {
        return sfWarehouseLocId;
    }

    public void setSfWarehouseLocId(Double sfWarehouseLocId) {
        this.sfWarehouseLocId = sfWarehouseLocId;
    }

    public Integer getStdLocCap() {
        return stdLocCap;
    }

    public void setStdLocCap(Integer stdLocCap) {
        this.stdLocCap = stdLocCap;
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

    public Double getQtyFucComm() {
        return qtyFucComm;
    }

    public void setQtyFucComm(Double qtyFucComm) {
        this.qtyFucComm = qtyFucComm;
    }

    public String getQtyType() {
        return qtyType;
    }

    public void setQtyType(String qtyType) {
        this.qtyType = qtyType == null ? null : qtyType.trim();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Double getQtyOnLock() {
        return qtyOnLock;
    }

    public void setQtyOnLock(Double qtyOnLock) {
        this.qtyOnLock = qtyOnLock;
    }

    public Double getInRcvStk() {
        return inRcvStk;
    }

    public void setInRcvStk(Double inRcvStk) {
        this.inRcvStk = inRcvStk;
    }

    public Double getReservedCommittedQty() {
        return reservedCommittedQty;
    }

    public void setReservedCommittedQty(Double reservedCommittedQty) {
        this.reservedCommittedQty = reservedCommittedQty;
    }

    public Double getQtyInTransitAg() {
        return qtyInTransitAg;
    }

    public void setQtyInTransitAg(Double qtyInTransitAg) {
        this.qtyInTransitAg = qtyInTransitAg;
    }

    public Double getCurCost() {
        return curCost;
    }

    public void setCurCost(Double curCost) {
        this.curCost = curCost;
    }

    public Double getLockStockin() {
        return lockStockin;
    }

    public void setLockStockin(Double lockStockin) {
        this.lockStockin = lockStockin;
    }

    public Double getReservedQty() {
        return reservedQty;
    }

    public void setReservedQty(Double reservedQty) {
        this.reservedQty = reservedQty;
    }

    public Double getStockinFree() {
        return stockinFree;
    }

    public void setStockinFree(Double stockinFree) {
        this.stockinFree = stockinFree;
    }

    public Double getLockedQty() {
        return lockedQty;
    }

    public void setLockedQty(Double lockedQty) {
        this.lockedQty = lockedQty;
    }
}