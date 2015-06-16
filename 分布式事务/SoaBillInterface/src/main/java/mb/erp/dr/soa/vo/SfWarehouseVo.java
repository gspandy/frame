package mb.erp.dr.soa.vo;

import java.io.Serializable;

/**
 * 新ERP 仓库
 * 仓库指组织中的物流中心与门店，所以其ID是组织中的ID
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-17
 * @see         SfWarehouseVo
 * @since       全流通改造
 */
public class SfWarehouseVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4258789495972104567L;

	private Long id;

    private Long bfOrgId;

    private Long costGrpId;

    private String locAdopted;

    private String scanAdopted;

    private Long dispLocId;

    private Long rcptLocId;

    private Long tempLocId;

    private String fucAdopted;

    private String delivAdopted;

    private String rfAdopted;

    private String transferFlag;

    private String boxAdopted;

    private String boxCodePrefix;

    private Long transLocId;

    private String trayCodePrefix;

    private String arrLocAdopted;

    private String whType;

    private Double taskNum;

    private Long dailyMaxCount;

    private Double leastSendNum;

    private Long cleanTempLocId;

    private Long classTempLocId;

    private Long b2bDispLocId;

    private Long brandId;

    private Long bgrSeasonId;

    private Long financeLocId;

    private Long boxRcptLocId;

    private Long bgrSortSeasonId;

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

    public Long getCostGrpId() {
        return costGrpId;
    }

    public void setCostGrpId(Long costGrpId) {
        this.costGrpId = costGrpId;
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

    public Long getDispLocId() {
        return dispLocId;
    }

    public void setDispLocId(Long dispLocId) {
        this.dispLocId = dispLocId;
    }

    public Long getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(Long rcptLocId) {
        this.rcptLocId = rcptLocId;
    }

    public Long getTempLocId() {
        return tempLocId;
    }

    public void setTempLocId(Long tempLocId) {
        this.tempLocId = tempLocId;
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

    public String getBoxAdopted() {
        return boxAdopted;
    }

    public void setBoxAdopted(String boxAdopted) {
        this.boxAdopted = boxAdopted == null ? null : boxAdopted.trim();
    }

    public String getBoxCodePrefix() {
        return boxCodePrefix;
    }

    public void setBoxCodePrefix(String boxCodePrefix) {
        this.boxCodePrefix = boxCodePrefix == null ? null : boxCodePrefix.trim();
    }

    public Long getTransLocId() {
        return transLocId;
    }

    public void setTransLocId(Long transLocId) {
        this.transLocId = transLocId;
    }

    public String getTrayCodePrefix() {
        return trayCodePrefix;
    }

    public void setTrayCodePrefix(String trayCodePrefix) {
        this.trayCodePrefix = trayCodePrefix == null ? null : trayCodePrefix.trim();
    }

    public String getArrLocAdopted() {
        return arrLocAdopted;
    }

    public void setArrLocAdopted(String arrLocAdopted) {
        this.arrLocAdopted = arrLocAdopted == null ? null : arrLocAdopted.trim();
    }

    public String getWhType() {
        return whType;
    }

    public void setWhType(String whType) {
        this.whType = whType == null ? null : whType.trim();
    }

    public Double getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Double taskNum) {
        this.taskNum = taskNum;
    }

    public Long getDailyMaxCount() {
        return dailyMaxCount;
    }

    public void setDailyMaxCount(Long dailyMaxCount) {
        this.dailyMaxCount = dailyMaxCount;
    }

    public Double getLeastSendNum() {
        return leastSendNum;
    }

    public void setLeastSendNum(Double leastSendNum) {
        this.leastSendNum = leastSendNum;
    }

    public Long getCleanTempLocId() {
        return cleanTempLocId;
    }

    public void setCleanTempLocId(Long cleanTempLocId) {
        this.cleanTempLocId = cleanTempLocId;
    }

    public Long getClassTempLocId() {
        return classTempLocId;
    }

    public void setClassTempLocId(Long classTempLocId) {
        this.classTempLocId = classTempLocId;
    }

    public Long getB2bDispLocId() {
        return b2bDispLocId;
    }

    public void setB2bDispLocId(Long b2bDispLocId) {
        this.b2bDispLocId = b2bDispLocId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getBgrSeasonId() {
        return bgrSeasonId;
    }

    public void setBgrSeasonId(Long bgrSeasonId) {
        this.bgrSeasonId = bgrSeasonId;
    }

    public Long getFinanceLocId() {
        return financeLocId;
    }

    public void setFinanceLocId(Long financeLocId) {
        this.financeLocId = financeLocId;
    }

    public Long getBoxRcptLocId() {
        return boxRcptLocId;
    }

    public void setBoxRcptLocId(Long boxRcptLocId) {
        this.boxRcptLocId = boxRcptLocId;
    }

    public Long getBgrSortSeasonId() {
        return bgrSortSeasonId;
    }

    public void setBgrSortSeasonId(Long bgrSortSeasonId) {
        this.bgrSortSeasonId = bgrSortSeasonId;
    }
}