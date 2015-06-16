package mb.erp.dr.soa.old.vo;

import java.io.Serializable;

/**
 * 仓库货位表
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         Warehouse
 * @since       全流通改造
 */
public class Warehouse implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4064293302960363390L;
	
	private String warehId;

    private String costGrpId;

    private String locAdopted;

    private String scanAdopted;

    private String dispLocId;

    private String rcptLocId;

    private String fucAdopted;

    private String delivAdopted;

    private String rfAdopted;

    private String transferFlag;

    private Double dailyMaxCount;

    private String curStockType;

    public String getWarehId() {
        return warehId;
    }

    public void setWarehId(String warehId) {
        this.warehId = warehId == null ? null : warehId.trim();
    }

    public String getCostGrpId() {
        return costGrpId;
    }

    public void setCostGrpId(String costGrpId) {
        this.costGrpId = costGrpId == null ? null : costGrpId.trim();
    }

    public String getLocAdopted() {
        return locAdopted;
    }

    public void setLocAdopted(String locAdopted) {
        this.locAdopted = locAdopted == null ? null : locAdopted.trim();
    }

    public String getScanAdopted() {
        return scanAdopted;
    }

    public void setScanAdopted(String scanAdopted) {
        this.scanAdopted = scanAdopted == null ? null : scanAdopted.trim();
    }

    public String getDispLocId() {
        return dispLocId;
    }

    public void setDispLocId(String dispLocId) {
        this.dispLocId = dispLocId == null ? null : dispLocId.trim();
    }

    public String getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(String rcptLocId) {
        this.rcptLocId = rcptLocId == null ? null : rcptLocId.trim();
    }

    public String getFucAdopted() {
        return fucAdopted;
    }

    public void setFucAdopted(String fucAdopted) {
        this.fucAdopted = fucAdopted == null ? null : fucAdopted.trim();
    }

    public String getDelivAdopted() {
        return delivAdopted;
    }

    public void setDelivAdopted(String delivAdopted) {
        this.delivAdopted = delivAdopted == null ? null : delivAdopted.trim();
    }

    public String getRfAdopted() {
        return rfAdopted;
    }

    public void setRfAdopted(String rfAdopted) {
        this.rfAdopted = rfAdopted == null ? null : rfAdopted.trim();
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag == null ? null : transferFlag.trim();
    }

    public Double getDailyMaxCount() {
        return dailyMaxCount;
    }

    public void setDailyMaxCount(Double dailyMaxCount) {
        this.dailyMaxCount = dailyMaxCount;
    }

    public String getCurStockType() {
        return curStockType;
    }

    public void setCurStockType(String curStockType) {
        this.curStockType = curStockType == null ? null : curStockType.trim();
    }
}