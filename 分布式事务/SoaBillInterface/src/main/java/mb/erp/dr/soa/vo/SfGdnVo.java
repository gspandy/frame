package mb.erp.dr.soa.vo;

import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;

/**
 * 新ERP出库单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-12-04
 * @see         SfGdnVo
 * @since       全流通改造
 */
public class SfGdnVo extends NewBaseBizVo{
	
	private static final long serialVersionUID = -6214291710533560380L;

	private Long id;

    private Long bfOrgUnitId;
    
    private String code;

    private String ogdnCode;

    private Date docDate;

    private String delivMode;

    private Long bfOrgWarehId;
    
    private Long bfOrgRcvUnitId;
    
    private Long bfOrgRcvWarehId;
    
    private Long ctrlrId;

    private Long contactId;

    private String currency;

    private Double ttlQty;

    private Double ttlVal;

    private Double taxRate;

    private Double taxVal;

    private Double psnVal;

    private Double addtCost;

    private Double cost;

    private Date reqdAt;

    private Date dispTime;

    private String delivMthd;

    private String delivAddr;

    private String delivPstd;

    private String csbNum;

    private String costChg;

    private String progress;

    private String remark;

    private Double productCount;

    private Long locId;

    private Long brandId;
    
    private String brandCode;

    private String rcvState;

    private Long bfOrgTspComId;

    private String transType;

    private Integer docState;

    private String createUser;

    private Date createDate;

    private String lastModifiedUser;

    private Date lastModifiedDate;

    private String flag;

    private NewBillType srcDocType;

    private String srcDocCode;

    private String sfTrayCode;

    private Long packerId;

    private Long checkerId;

    private Long scanerId;

    private String oerpFlag;

    private String isUpdated;

    private String efficient;

    private String dataSource;

    private String approved;

    private String oldGdnCode;

    private Date groupTime;

    private String isInvoiceVerify;

    private String innerDgnCode;

    private String struckReason;

    private String isCostAccount;

    private String docSrc;

    private String docSrcInfo;

    private String sapSendFlag;

    private Date sapSendTime;

    private String oerpSendFlag;

    private Date oerpSendTime;

    private String origDocType;

    private String origDocCode;

    private String scancodenum;

    private Long origUnitId;

    private Integer actSenWarehId;

    private String shopGainedStatus;

    private Date shopGainedTime;

    private String cusGainedStatus;

    private Date cusGainedTime;

    private String shopRefReson;

    private String cusRefReson;

    private String orgDataType;

    private Integer exFlag;

    private String oerpRemark;

    private Date takeAccountDate;

    private String osDocCode;
    
    private List<SfGdnDtlVo> sfGdnDtlVos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBfOrgUnitId() {
        return bfOrgUnitId;
    }

    public void setBfOrgUnitId(Long bfOrgUnitId) {
        this.bfOrgUnitId = bfOrgUnitId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getOgdnCode() {
        return ogdnCode;
    }

    public void setOgdnCode(String ogdnCode) {
        this.ogdnCode = ogdnCode == null ? null : ogdnCode.trim();
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getDelivMode() {
        return delivMode;
    }

    public void setDelivMode(String delivMode) {
        this.delivMode = delivMode == null ? null : delivMode.trim();
    }

    public Long getBfOrgWarehId() {
        return bfOrgWarehId;
    }

    public void setBfOrgWarehId(Long bfOrgWarehId) {
        this.bfOrgWarehId = bfOrgWarehId;
    }

    public Long getBfOrgRcvUnitId() {
        return bfOrgRcvUnitId;
    }

    public void setBfOrgRcvUnitId(Long bfOrgRcvUnitId) {
        this.bfOrgRcvUnitId = bfOrgRcvUnitId;
    }

    public Long getBfOrgRcvWarehId() {
        return bfOrgRcvWarehId;
    }

    public void setBfOrgRcvWarehId(Long bfOrgRcvWarehId) {
        this.bfOrgRcvWarehId = bfOrgRcvWarehId;
    }

    public Long getCtrlrId() {
        return ctrlrId;
    }

    public void setCtrlrId(Long ctrlrId) {
        this.ctrlrId = ctrlrId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
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

    public Date getReqdAt() {
        return reqdAt;
    }

    public void setReqdAt(Date reqdAt) {
        this.reqdAt = reqdAt;
    }

    public Date getDispTime() {
        return dispTime;
    }

    public void setDispTime(Date dispTime) {
        this.dispTime = dispTime;
    }

    public String getDelivMthd() {
        return delivMthd;
    }

    public void setDelivMthd(String delivMthd) {
        this.delivMthd = delivMthd == null ? null : delivMthd.trim();
    }

    public String getDelivAddr() {
        return delivAddr;
    }

    public void setDelivAddr(String delivAddr) {
        this.delivAddr = delivAddr == null ? null : delivAddr.trim();
    }

    public String getDelivPstd() {
        return delivPstd;
    }

    public void setDelivPstd(String delivPstd) {
        this.delivPstd = delivPstd == null ? null : delivPstd.trim();
    }

    public String getCsbNum() {
        return csbNum;
    }

    public void setCsbNum(String csbNum) {
        this.csbNum = csbNum == null ? null : csbNum.trim();
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

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
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

    public Long getBfOrgTspComId() {
        return bfOrgTspComId;
    }

    public void setBfOrgTspComId(Long bfOrgTspComId) {
        this.bfOrgTspComId = bfOrgTspComId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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

    public NewBillType getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(NewBillType srcDocType) {
        this.srcDocType = srcDocType;
    }

    public String getSrcDocCode() {
        return srcDocCode;
    }

    public void setSrcDocCode(String srcDocCode) {
        this.srcDocCode = srcDocCode == null ? null : srcDocCode.trim();
    }

    public String getSfTrayCode() {
        return sfTrayCode;
    }

    public void setSfTrayCode(String sfTrayCode) {
        this.sfTrayCode = sfTrayCode == null ? null : sfTrayCode.trim();
    }

    public Long getPackerId() {
        return packerId;
    }

    public void setPackerId(Long packerId) {
        this.packerId = packerId;
    }

    public Long getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }

    public Long getScanerId() {
        return scanerId;
    }

    public void setScanerId(Long scanerId) {
        this.scanerId = scanerId;
    }

    public String getOerpFlag() {
        return oerpFlag;
    }

    public void setOerpFlag(String oerpFlag) {
        this.oerpFlag = oerpFlag == null ? null : oerpFlag.trim();
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated == null ? null : isUpdated.trim();
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

    public String getOldGdnCode() {
        return oldGdnCode;
    }

    public void setOldGdnCode(String oldGdnCode) {
        this.oldGdnCode = oldGdnCode == null ? null : oldGdnCode.trim();
    }

    public Date getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(Date groupTime) {
        this.groupTime = groupTime;
    }

    public String getIsInvoiceVerify() {
        return isInvoiceVerify;
    }

    public void setIsInvoiceVerify(String isInvoiceVerify) {
        this.isInvoiceVerify = isInvoiceVerify == null ? null : isInvoiceVerify.trim();
    }

    public String getInnerDgnCode() {
        return innerDgnCode;
    }

    public void setInnerDgnCode(String innerDgnCode) {
        this.innerDgnCode = innerDgnCode == null ? null : innerDgnCode.trim();
    }

    public String getStruckReason() {
        return struckReason;
    }

    public void setStruckReason(String struckReason) {
        this.struckReason = struckReason == null ? null : struckReason.trim();
    }

    public String getIsCostAccount() {
        return isCostAccount;
    }

    public void setIsCostAccount(String isCostAccount) {
        this.isCostAccount = isCostAccount == null ? null : isCostAccount.trim();
    }

    public String getDocSrc() {
        return docSrc;
    }

    public void setDocSrc(String docSrc) {
        this.docSrc = docSrc == null ? null : docSrc.trim();
    }

    public String getDocSrcInfo() {
        return docSrcInfo;
    }

    public void setDocSrcInfo(String docSrcInfo) {
        this.docSrcInfo = docSrcInfo == null ? null : docSrcInfo.trim();
    }

    public String getSapSendFlag() {
        return sapSendFlag;
    }

    public void setSapSendFlag(String sapSendFlag) {
        this.sapSendFlag = sapSendFlag == null ? null : sapSendFlag.trim();
    }

    public Date getSapSendTime() {
        return sapSendTime;
    }

    public void setSapSendTime(Date sapSendTime) {
        this.sapSendTime = sapSendTime;
    }

    public String getOerpSendFlag() {
        return oerpSendFlag;
    }

    public void setOerpSendFlag(String oerpSendFlag) {
        this.oerpSendFlag = oerpSendFlag == null ? null : oerpSendFlag.trim();
    }

    public Date getOerpSendTime() {
        return oerpSendTime;
    }

    public void setOerpSendTime(Date oerpSendTime) {
        this.oerpSendTime = oerpSendTime;
    }

    public String getOrigDocType() {
        return origDocType;
    }

    public void setOrigDocType(String origDocType) {
        this.origDocType = origDocType;
    }

    public String getOrigDocCode() {
        return origDocCode;
    }

    public void setOrigDocCode(String origDocCode) {
        this.origDocCode = origDocCode == null ? null : origDocCode.trim();
    }

    public String getScancodenum() {
        return scancodenum;
    }

    public void setScancodenum(String scancodenum) {
        this.scancodenum = scancodenum == null ? null : scancodenum.trim();
    }

    public Long getOrigUnitId() {
        return origUnitId;
    }

    public void setOrigUnitId(Long origUnitId) {
        this.origUnitId = origUnitId;
    }

    public Integer getActSenWarehId() {
        return actSenWarehId;
    }

    public void setActSenWarehId(Integer actSenWarehId) {
        this.actSenWarehId = actSenWarehId;
    }

    public String getShopGainedStatus() {
        return shopGainedStatus;
    }

    public void setShopGainedStatus(String shopGainedStatus) {
        this.shopGainedStatus = shopGainedStatus == null ? null : shopGainedStatus.trim();
    }

    public Date getShopGainedTime() {
        return shopGainedTime;
    }

    public void setShopGainedTime(Date shopGainedTime) {
        this.shopGainedTime = shopGainedTime;
    }

    public String getCusGainedStatus() {
        return cusGainedStatus;
    }

    public void setCusGainedStatus(String cusGainedStatus) {
        this.cusGainedStatus = cusGainedStatus == null ? null : cusGainedStatus.trim();
    }

    public Date getCusGainedTime() {
        return cusGainedTime;
    }

    public void setCusGainedTime(Date cusGainedTime) {
        this.cusGainedTime = cusGainedTime;
    }

    public String getShopRefReson() {
        return shopRefReson;
    }

    public void setShopRefReson(String shopRefReson) {
        this.shopRefReson = shopRefReson == null ? null : shopRefReson.trim();
    }

    public String getCusRefReson() {
        return cusRefReson;
    }

    public void setCusRefReson(String cusRefReson) {
        this.cusRefReson = cusRefReson == null ? null : cusRefReson.trim();
    }

    public String getOrgDataType() {
        return orgDataType;
    }

    public void setOrgDataType(String orgDataType) {
        this.orgDataType = orgDataType == null ? null : orgDataType.trim();
    }

    public Integer getExFlag() {
        return exFlag;
    }

    public void setExFlag(Integer exFlag) {
        this.exFlag = exFlag;
    }

    public String getOerpRemark() {
        return oerpRemark;
    }

    public void setOerpRemark(String oerpRemark) {
        this.oerpRemark = oerpRemark == null ? null : oerpRemark.trim();
    }

    public Date getTakeAccountDate() {
        return takeAccountDate;
    }

    public void setTakeAccountDate(Date takeAccountDate) {
        this.takeAccountDate = takeAccountDate;
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }

	public List<SfGdnDtlVo> getSfGdnDtlVos() {
		return sfGdnDtlVos;
	}

	public void setSfGdnDtlVos(List<SfGdnDtlVo> sfGdnDtlVos) {
		this.sfGdnDtlVos = sfGdnDtlVos;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
}