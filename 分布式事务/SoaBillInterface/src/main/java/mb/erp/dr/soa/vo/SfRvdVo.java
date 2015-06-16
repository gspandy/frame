package mb.erp.dr.soa.vo;

import java.util.Date;
import java.util.List;

/**
 * 新ERP到货通知单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfRvdVo
 * @since       全流通改造
 */
public class SfRvdVo extends NewBaseBizVo{
	private static final long serialVersionUID = 1799585124143126919L;
	
	private Long id; //内码

    private String code; //到货通知单编号

    private Long unitId; //收货组织ID
    
    private Long bfOrgUnitId; //发货组织ID

    private Long warehId; //发货仓库ID

    private Long bfOrgRcvWarehId; //接收仓库ID
    
    private String srcDocType; //原始单据类别

    private Long srcUnitId; //原始单据组织ID

    private String srcDocNum; //原始单据编号

    private Long factRcvWarehId; //实际接收仓库ID

    private String ogrnCode; //原入库单编号

    private Date revDate; //到货日期

    private Date docDate; //单据日期

    private String currency; //币种

    private Double ttlQty; //总数量

    private Double ttlVal; //总金额

    private Double taxRate; //税率

    private Long brandId; //品牌ID

    private String rcvState; //入库原因

    private String isCrossTransfer; //是否交叉转运

    private String crossOrderno; //交叉转运单号

    private String innerOrderno; //内向交货单编号

    private String isChecked; //是否质检

    private String checkedStatus; //质检状态

    private String checkedBatchno; //质检批号

    private String dataType;

    private Date preArriveTime; //预计到货时间

    private Date arriveTime; //实际到货时间

    private Date payqtyDate; //工厂交货日期

    private String createUser; //创建人

    private Date createDate; //创建时间

    private String lastModifiedUser; //最后修改人

    private Date lastModifiedDate; //最后修改时间

    private Integer docState; //进度

    private String progress; //备用进度

    private String sapFlag; //传入SAP标志

    private String sapInvoiceFlag;

    private String rcptMode; //入库方式

    private String receMode; //收货方式

    private String originDocNum; //源头单据编码

    private Long originUnitId; //源头单据组织

    private String originDocType; //源头单据类别

    private String dataSource; //数据来源

    private String createType; //生成方式

    private String approved; //决策方

    private Double psnVal; //折让金额总计

    private Double cost; //成本总计

    private Double addtCost; //附加成本总计

    private Double productCount;

    private String isStart;

    private String isCleanScan;

    private Long bgrSeasonId;

    private Double delivQty;

    private Double delivVal;

    private Double taxVal;

    private Long delivBoxCount;

    private Long rcvBoxCount;

    private Long bgrState;

    private String lockedStatus; //入库锁定标志

    private String isFankBox;

    private String isRanged; //是否跨品牌入库

    private String rvdType;

    private String osDocCode;
    
    private Long sfGrnId;
    
    private List<SfRvdDtlVo> sfRvdDtlVos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getBfOrgUnitId() {
        return bfOrgUnitId;
    }

    public void setBfOrgUnitId(Long bfOrgUnitId) {
        this.bfOrgUnitId = bfOrgUnitId;
    }

    public Long getWarehId() {
        return warehId;
    }

    public void setWarehId(Long warehId) {
        this.warehId = warehId;
    }

    public Long getBfOrgRcvWarehId() {
        return bfOrgRcvWarehId;
    }

    public void setBfOrgRcvWarehId(Long bfOrgRcvWarehId) {
        this.bfOrgRcvWarehId = bfOrgRcvWarehId;
    }

    public String getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(String srcDocType) {
        this.srcDocType = srcDocType == null ? null : srcDocType.trim();
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

    public Long getFactRcvWarehId() {
        return factRcvWarehId;
    }

    public void setFactRcvWarehId(Long factRcvWarehId) {
        this.factRcvWarehId = factRcvWarehId;
    }

    public String getOgrnCode() {
        return ogrnCode;
    }

    public void setOgrnCode(String ogrnCode) {
        this.ogrnCode = ogrnCode == null ? null : ogrnCode.trim();
    }

    public Date getRevDate() {
        return revDate;
    }

    public void setRevDate(Date revDate) {
        this.revDate = revDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
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

    public String getCrossOrderno() {
        return crossOrderno;
    }

    public void setCrossOrderno(String crossOrderno) {
        this.crossOrderno = crossOrderno == null ? null : crossOrderno.trim();
    }

    public String getInnerOrderno() {
        return innerOrderno;
    }

    public void setInnerOrderno(String innerOrderno) {
        this.innerOrderno = innerOrderno == null ? null : innerOrderno.trim();
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked == null ? null : isChecked.trim();
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public Date getPreArriveTime() {
        return preArriveTime;
    }

    public void setPreArriveTime(Date preArriveTime) {
        this.preArriveTime = preArriveTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getPayqtyDate() {
        return payqtyDate;
    }

    public void setPayqtyDate(Date payqtyDate) {
        this.payqtyDate = payqtyDate;
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

    public Integer getDocState() {
        return docState;
    }

    public void setDocState(Integer docState) {
        this.docState = docState;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getSapFlag() {
        return sapFlag;
    }

    public void setSapFlag(String sapFlag) {
        this.sapFlag = sapFlag == null ? null : sapFlag.trim();
    }

    public String getSapInvoiceFlag() {
        return sapInvoiceFlag;
    }

    public void setSapInvoiceFlag(String sapInvoiceFlag) {
        this.sapInvoiceFlag = sapInvoiceFlag == null ? null : sapInvoiceFlag.trim();
    }

    public String getRcptMode() {
        return rcptMode;
    }

    public void setRcptMode(String rcptMode) {
        this.rcptMode = rcptMode == null ? null : rcptMode.trim();
    }

    public String getReceMode() {
        return receMode;
    }

    public void setReceMode(String receMode) {
        this.receMode = receMode == null ? null : receMode.trim();
    }

    public String getOriginDocNum() {
        return originDocNum;
    }

    public void setOriginDocNum(String originDocNum) {
        this.originDocNum = originDocNum == null ? null : originDocNum.trim();
    }

    public Long getOriginUnitId() {
        return originUnitId;
    }

    public void setOriginUnitId(Long originUnitId) {
        this.originUnitId = originUnitId;
    }

    public String getOriginDocType() {
        return originDocType;
    }

    public void setOriginDocType(String originDocType) {
        this.originDocType = originDocType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType == null ? null : createType.trim();
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved == null ? null : approved.trim();
    }

    public Double getPsnVal() {
        return psnVal;
    }

    public void setPsnVal(Double psnVal) {
        this.psnVal = psnVal;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getAddtCost() {
        return addtCost;
    }

    public void setAddtCost(Double addtCost) {
        this.addtCost = addtCost;
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart == null ? null : isStart.trim();
    }

    public String getIsCleanScan() {
        return isCleanScan;
    }

    public void setIsCleanScan(String isCleanScan) {
        this.isCleanScan = isCleanScan == null ? null : isCleanScan.trim();
    }

    public Long getBgrSeasonId() {
        return bgrSeasonId;
    }

    public void setBgrSeasonId(Long bgrSeasonId) {
        this.bgrSeasonId = bgrSeasonId;
    }

    public Double getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(Double delivQty) {
        this.delivQty = delivQty;
    }

    public Double getDelivVal() {
        return delivVal;
    }

    public void setDelivVal(Double delivVal) {
        this.delivVal = delivVal;
    }

    public Double getTaxVal() {
        return taxVal;
    }

    public void setTaxVal(Double taxVal) {
        this.taxVal = taxVal;
    }

    public Long getDelivBoxCount() {
        return delivBoxCount;
    }

    public void setDelivBoxCount(Long delivBoxCount) {
        this.delivBoxCount = delivBoxCount;
    }

    public Long getRcvBoxCount() {
        return rcvBoxCount;
    }

    public void setRcvBoxCount(Long rcvBoxCount) {
        this.rcvBoxCount = rcvBoxCount;
    }

    public Long getBgrState() {
        return bgrState;
    }

    public void setBgrState(Long bgrState) {
        this.bgrState = bgrState;
    }

    public String getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(String lockedStatus) {
        this.lockedStatus = lockedStatus == null ? null : lockedStatus.trim();
    }

    public String getIsFankBox() {
        return isFankBox;
    }

    public void setIsFankBox(String isFankBox) {
        this.isFankBox = isFankBox == null ? null : isFankBox.trim();
    }

    public String getIsRanged() {
        return isRanged;
    }

    public void setIsRanged(String isRanged) {
        this.isRanged = isRanged == null ? null : isRanged.trim();
    }

    public String getRvdType() {
        return rvdType;
    }

    public void setRvdType(String rvdType) {
        this.rvdType = rvdType == null ? null : rvdType.trim();
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }

	public List<SfRvdDtlVo> getSfRvdDtlVos() {
		return sfRvdDtlVos;
	}

	public void setSfRvdDtlVos(List<SfRvdDtlVo> sfRvdDtlVos) {
		this.sfRvdDtlVos = sfRvdDtlVos;
	}

	public Long getSfGrnId() {
		return sfGrnId;
	}

	public void setSfGrnId(Long sfGrnId) {
		this.sfGrnId = sfGrnId;
	}
}