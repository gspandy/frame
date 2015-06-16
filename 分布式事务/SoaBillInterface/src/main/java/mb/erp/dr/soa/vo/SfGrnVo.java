package mb.erp.dr.soa.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;

/**
 * 新ERP入库单vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-08
 * @see         SfGrnVo
 * @since       全流通改造
 */
public class SfGrnVo extends NewBaseBizVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2523457928221205981L;

	private Long id;

    private Long unitId;

    private String grnNum;

    private Date docDate;

    private String rcptMode;

    private Long oprId;

    private Long ctrlrId;
    
    private Long warehId;

    private Long dispUnitId;

    private Long delivWarehId;

    private NewBillType srcDocType;

    private Long srcUnitId;

    private String srcDocNum;

    private String currency;

    private Double ttlQty;

    private Double ttlVal;

    private Double taxRate;

    private Double taxVal;

    private Double psnVal;

    private Double addtCost;

    private Double cost;

    private Date rcptTime;

    private Date paStrAt;

    private Date paCompAt;

    private String costChg;

    private String progress;

    private String remark;

    private String pdaProgress;

    private Date efficientTime;

    private Date costTime;

    private String businessContractNum;

    private String needSend;

    private String shmtNbr;

    private String rcvType;

    private String innerOrderno;

    private String rfRcv;

    private Long brandId;

    private String rcvState;

    private String isCrossTransfer;

    private String isQltChecked;

    private Date planedRchTime;

    private Date actualRchTime;

    private Long actualRcvWarehId;

    private Integer docState;

    private String createUser;

    private Date createDate;

    private String lastModifiedUser;

    private Date lastModifiedDate;

    private String flag;

    private String qtyType;

    private String dealDiff;

    private String sfRvdCode;

    private String oerpFlag;

    private String efficient;

    private String dataSource;

    private String approved;

    private String oldGrnCode;

    private String checkedStatus;

    private String checkedBatchno;

    private String checkedUser;

    private Date checkedTime;

    private String isReceiptCheck;

    private String isStruck;

    private String struckReason;

    private String docRoot;

    private String docRootInfo;

    private String sapFlag;

    private Date sapTime;

    private Date oerpTime;

    private String rootDocType;

    private String rootDocNum;

    private Double productCount;

    private String isChecked;

    private String lockedStatus;

    private String osDocCode;
    
    private List<SfGrnDtlVo> dtlVos = new ArrayList<SfGrnDtlVo>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getGrnNum() {
        return grnNum;
    }

    public void setGrnNum(String grnNum) {
        this.grnNum = grnNum == null ? null : grnNum.trim();
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getRcptMode() {
        return rcptMode;
    }

    public void setRcptMode(String rcptMode) {
        this.rcptMode = rcptMode == null ? null : rcptMode.trim();
    }

    public Long getWarehId() {
        return warehId;
    }

    public void setWarehId(Long warehId) {
        this.warehId = warehId;
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprId(Long oprId) {
        this.oprId = oprId;
    }

    public Long getCtrlrId() {
        return ctrlrId;
    }

    public void setCtrlrId(Long ctrlrId) {
        this.ctrlrId = ctrlrId;
    }

    public Long getDispUnitId() {
        return dispUnitId;
    }

    public void setDispUnitId(Long dispUnitId) {
        this.dispUnitId = dispUnitId;
    }

    public Long getDelivWarehId() {
        return delivWarehId;
    }

    public void setDelivWarehId(Long delivWarehId) {
        this.delivWarehId = delivWarehId;
    }

    public NewBillType getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(NewBillType srcDocType) {
        this.srcDocType = srcDocType;
    }

    public Long getSrcUnitId() {
        return srcUnitId;
    }

    public void setSrcUnitId(Long srcUnitId) {
        this.srcUnitId = srcUnitId;
    }

    public String getSrcDocNum() {
        return srcDocNum;
    }

    public void setSrcDocNum(String srcDocNum) {
        this.srcDocNum = srcDocNum == null ? null : srcDocNum.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Double getTtlQty() {
        return ttlQty;
    }

    public void setTtlQty(Double ttlQty) {
        this.ttlQty = ttlQty;
    }

    public Double getTtlVal() {
        return ttlVal;
    }

    public void setTtlVal(Double ttlVal) {
        this.ttlVal = ttlVal;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxVal() {
        return taxVal;
    }

    public void setTaxVal(Double taxVal) {
        this.taxVal = taxVal;
    }

    public Double getPsnVal() {
        return psnVal;
    }

    public void setPsnVal(Double psnVal) {
        this.psnVal = psnVal;
    }

    public Double getAddtCost() {
        return addtCost;
    }

    public void setAddtCost(Double addtCost) {
        this.addtCost = addtCost;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getRcptTime() {
        return rcptTime;
    }

    public void setRcptTime(Date rcptTime) {
        this.rcptTime = rcptTime;
    }

    public Date getPaStrAt() {
        return paStrAt;
    }

    public void setPaStrAt(Date paStrAt) {
        this.paStrAt = paStrAt;
    }

    public Date getPaCompAt() {
        return paCompAt;
    }

    public void setPaCompAt(Date paCompAt) {
        this.paCompAt = paCompAt;
    }

    public String getCostChg() {
        return costChg;
    }

    public void setCostChg(String costChg) {
        this.costChg = costChg == null ? null : costChg.trim();
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPdaProgress() {
        return pdaProgress;
    }

    public void setPdaProgress(String pdaProgress) {
        this.pdaProgress = pdaProgress == null ? null : pdaProgress.trim();
    }

    public Date getEfficientTime() {
        return efficientTime;
    }

    public void setEfficientTime(Date efficientTime) {
        this.efficientTime = efficientTime;
    }

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }

    public String getBusinessContractNum() {
        return businessContractNum;
    }

    public void setBusinessContractNum(String businessContractNum) {
        this.businessContractNum = businessContractNum == null ? null : businessContractNum.trim();
    }

    public String getNeedSend() {
        return needSend;
    }

    public void setNeedSend(String needSend) {
        this.needSend = needSend == null ? null : needSend.trim();
    }

    public String getShmtNbr() {
        return shmtNbr;
    }

    public void setShmtNbr(String shmtNbr) {
        this.shmtNbr = shmtNbr == null ? null : shmtNbr.trim();
    }

    public String getRcvType() {
        return rcvType;
    }

    public void setRcvType(String rcvType) {
        this.rcvType = rcvType == null ? null : rcvType.trim();
    }

    public String getInnerOrderno() {
        return innerOrderno;
    }

    public void setInnerOrderno(String innerOrderno) {
        this.innerOrderno = innerOrderno == null ? null : innerOrderno.trim();
    }

    public String getRfRcv() {
        return rfRcv;
    }

    public void setRfRcv(String rfRcv) {
        this.rfRcv = rfRcv == null ? null : rfRcv.trim();
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getRcvState() {
        return rcvState;
    }

    public void setRcvState(String rcvState) {
        this.rcvState = rcvState == null ? null : rcvState.trim();
    }

    public String getIsCrossTransfer() {
        return isCrossTransfer;
    }

    public void setIsCrossTransfer(String isCrossTransfer) {
        this.isCrossTransfer = isCrossTransfer == null ? null : isCrossTransfer.trim();
    }

    public String getIsQltChecked() {
        return isQltChecked;
    }

    public void setIsQltChecked(String isQltChecked) {
        this.isQltChecked = isQltChecked == null ? null : isQltChecked.trim();
    }

    public Date getPlanedRchTime() {
        return planedRchTime;
    }

    public void setPlanedRchTime(Date planedRchTime) {
        this.planedRchTime = planedRchTime;
    }

    public Date getActualRchTime() {
        return actualRchTime;
    }

    public void setActualRchTime(Date actualRchTime) {
        this.actualRchTime = actualRchTime;
    }

    public Long getActualRcvWarehId() {
        return actualRcvWarehId;
    }

    public void setActualRcvWarehId(Long actualRcvWarehId) {
        this.actualRcvWarehId = actualRcvWarehId;
    }

    public Integer getDocState() {
        return docState;
    }

    public void setDocState(Integer docState) {
        this.docState = docState;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser == null ? null : lastModifiedUser.trim();
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getQtyType() {
        return qtyType;
    }

    public void setQtyType(String qtyType) {
        this.qtyType = qtyType == null ? null : qtyType.trim();
    }

    public String getDealDiff() {
        return dealDiff;
    }

    public void setDealDiff(String dealDiff) {
        this.dealDiff = dealDiff == null ? null : dealDiff.trim();
    }

    public String getSfRvdCode() {
        return sfRvdCode;
    }

    public void setSfRvdCode(String sfRvdCode) {
        this.sfRvdCode = sfRvdCode == null ? null : sfRvdCode.trim();
    }

    public String getOerpFlag() {
        return oerpFlag;
    }

    public void setOerpFlag(String oerpFlag) {
        this.oerpFlag = oerpFlag == null ? null : oerpFlag.trim();
    }

    public String getEfficient() {
        return efficient;
    }

    public void setEfficient(String efficient) {
        this.efficient = efficient == null ? null : efficient.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved == null ? null : approved.trim();
    }

    public String getOldGrnCode() {
        return oldGrnCode;
    }

    public void setOldGrnCode(String oldGrnCode) {
        this.oldGrnCode = oldGrnCode == null ? null : oldGrnCode.trim();
    }

    public String getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(String checkedStatus) {
        this.checkedStatus = checkedStatus == null ? null : checkedStatus.trim();
    }

    public String getCheckedBatchno() {
        return checkedBatchno;
    }

    public void setCheckedBatchno(String checkedBatchno) {
        this.checkedBatchno = checkedBatchno == null ? null : checkedBatchno.trim();
    }

    public String getCheckedUser() {
        return checkedUser;
    }

    public void setCheckedUser(String checkedUser) {
        this.checkedUser = checkedUser == null ? null : checkedUser.trim();
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getIsReceiptCheck() {
        return isReceiptCheck;
    }

    public void setIsReceiptCheck(String isReceiptCheck) {
        this.isReceiptCheck = isReceiptCheck == null ? null : isReceiptCheck.trim();
    }

    public String getIsStruck() {
        return isStruck;
    }

    public void setIsStruck(String isStruck) {
        this.isStruck = isStruck == null ? null : isStruck.trim();
    }

    public String getStruckReason() {
        return struckReason;
    }

    public void setStruckReason(String struckReason) {
        this.struckReason = struckReason == null ? null : struckReason.trim();
    }

    public String getDocRoot() {
        return docRoot;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot == null ? null : docRoot.trim();
    }

    public String getDocRootInfo() {
        return docRootInfo;
    }

    public void setDocRootInfo(String docRootInfo) {
        this.docRootInfo = docRootInfo == null ? null : docRootInfo.trim();
    }

    public String getSapFlag() {
        return sapFlag;
    }

    public void setSapFlag(String sapFlag) {
        this.sapFlag = sapFlag == null ? null : sapFlag.trim();
    }

    public Date getSapTime() {
        return sapTime;
    }

    public void setSapTime(Date sapTime) {
        this.sapTime = sapTime;
    }

    public Date getOerpTime() {
        return oerpTime;
    }

    public void setOerpTime(Date oerpTime) {
        this.oerpTime = oerpTime;
    }

    public String getRootDocType() {
        return rootDocType;
    }

    public void setRootDocType(String rootDocType) {
        this.rootDocType = rootDocType == null ? null : rootDocType.trim();
    }

    public String getRootDocNum() {
        return rootDocNum;
    }

    public void setRootDocNum(String rootDocNum) {
        this.rootDocNum = rootDocNum == null ? null : rootDocNum.trim();
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked == null ? null : isChecked.trim();
    }

    public String getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(String lockedStatus) {
        this.lockedStatus = lockedStatus == null ? null : lockedStatus.trim();
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }

	public List<SfGrnDtlVo> getDtlVos() {
		return dtlVos;
	}

	public void setDtlVos(List<SfGrnDtlVo> dtlVos) {
		this.dtlVos = dtlVos;
	}

}