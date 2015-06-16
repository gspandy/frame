package mb.erp.dr.soa.vo;

import java.util.Date;
import java.util.List;

/**
 * 新ERP交货单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfDgnVo
 * @since       全流通改造
 */
public class SfDgnVo extends NewBaseBizVo{
	private static final long serialVersionUID = 6593543635224095015L;

	private Long id; //内码
	
    private Long bfOrgUnitId; //发货组织ID

    private String code; //编码

    private Date docDate; //单据日期

    private Long warehId; //发货仓库ID

    private Long bfOrgRcvUnitId; //接收组织ID

    private Long bfOrgRcvWarehId; //接收仓库ID

    private Long ctrlrId; //操作员ID

    private Long contactId; //联系人ID

    private String srcDocType; //原始单据类型

    private Long srcUnitId; //原始组织ID

    private String srcDocCode; //原始单据编码

    private String currency; //币种

    private Double ttlQty; //总数量

    private Double ttlVal; //总金额

    private Double taxRate; //税率

    private Date reqdAt; //要求发货时间

    private String goodcode; //快递单号

    private String mfcNum; //生产合同号

    private String area; //发货区域

    private Long factoryId; //工厂ID

    private Long locId; //集货地ID

    private Date peqdAt; //货期

    private Date planToWarehDate; //计划到仓日

    private Date preCarryDate; //预计提货日

    private String isWholeSubmit; //是否整单交货

    private String isCombineAssign; //是否允许合并配送

    private String isReCalPrice; //是否需要重算价格

    private String isGenStkOnBuy; //是否需要生成在购库存

    private String isGenStkOnRoad; //是否需要生成在途库存

    private String isCanDelivery; //是否可以出库

    private String isCanAcross; //是否交叉转运

    private String isGenLogRecDoc; //是否需要生成到货通知

    private String isGenPkn; //是否需要生成分拣任务

    private String isFinnceBalance; //是否需要生成财务结算

    private String remark; //备注

    private Long brandId; //品牌组ID

    private Integer docState; //单据进度

    private String createUser; //创建人编码

    private Date createDate; //创建时间

    private String lastModifiedUser; //最后修改人编码

    private Date lastModifiedDate; //最后修改时间

    private String progress; //进度

    private String flag;

    private String isStraightDelivery; //是否直接出库

    private String delivMode; //出库方式

    private String isQn; //是否为问题单

    private Date strScanTime; //开始扫描清点时间

    private String isGenGrn; //是否直接入库

    private Integer actSenWarehId; //实际发货仓库ID

    private Date endScanTime; //结束扫描清点时间

    private String urgency; //紧急程度

    private Long scanerId; //扫描人ID

    private Long olocId; //原集货地ID

    private String pdaProgress; //RF进度

    private String pickType; //分拣方式

    private Integer printGTimes; //快递打印次数

    private String opeStatus; //操作状态

    private String boxWeight; //箱重量

    private String priExpType; //是否打印快递单

    private String priMode; //快递单打印模式

    private String combinedDgnCode;

    private Double totalBoxCount; //总箱数

    private Double totalWeight; //总重量

    private Double totalVolumn; //总体积

    private Long combinedSymbol; //合并标识

    private Long isCombined; //是否合并集货

    private String splitMode; //拆分方式

    private String clientOrderCode;

    private Long shippingId; //承运商ID

    private Long productCount; //品项数

    private String lowOrderFlag; //是否为下级门店订货

    private Long lowShopId; //下级门店ID

    private Long sfBatMnnId; //波次管理单ID

    private String isPrintGdn;

    private String locCode; //集货地编码

    private String olocCode; //原集货地编码

    private String pickMode;

    private String transState; //运输状态

    private String isTransfer; //是否中转运输

    private String transferState; //中转运输状态

    private Long ubietyWareh; //所在仓ID

    private Long transferOrgId; //中转仓

    private Long originUnitId;

    private String originDocType;

    private String originDocNum;

    private Double tempCombinedSymbol;

    private String combinFlagT;

    private Long tranWarehId;

    private Long gpicktime;

    private Long actRcvUnitId;

    private Long actRcvWarehId;

    private String dgnTypeSl;

    private String addRess;

    private String gdnState;

    private String ifEndBat;

    private String delivMthd;

    private String dataSource;

    private String delivPstd;

    private String createType;

    private String printflag;

    private Long oosOpeShopid;

    private String isInvoiceVerify;

    private String isAutoDist;

    private String isAdded;

    private Date pknConfirmTime;

    private String wifNum;

    private String oerpRemark; //断色断码订单出库后回传老ERP生成相关单据成功失败的备注

    private String transferValided; //调用结果回执说明（0或者空为需要传、1为传入成功、2为释放金额失败、3为生成老ERP单据失败

    private String isMerger; //是否合并

    private String osDocCode; //B2C订单号
    
    private Long sfGdnId;
    
    private List<SfDgnDtlVo> sfDgnDtlVos;

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
		this.code = code;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Long getWarehId() {
		return warehId;
	}

	public void setWarehId(Long warehId) {
		this.warehId = warehId;
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

	public String getSrcDocType() {
		return srcDocType;
	}

	public void setSrcDocType(String srcDocType) {
		this.srcDocType = srcDocType;
	}

	public Long getSrcUnitId() {
		return srcUnitId;
	}

	public void setSrcUnitId(Long srcUnitId) {
		this.srcUnitId = srcUnitId;
	}

	public String getSrcDocCode() {
		return srcDocCode;
	}

	public void setSrcDocCode(String srcDocCode) {
		this.srcDocCode = srcDocCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Date getReqdAt() {
		return reqdAt;
	}

	public void setReqdAt(Date reqdAt) {
		this.reqdAt = reqdAt;
	}

	public String getGoodcode() {
		return goodcode;
	}

	public void setGoodcode(String goodcode) {
		this.goodcode = goodcode;
	}

	public String getMfcNum() {
		return mfcNum;
	}

	public void setMfcNum(String mfcNum) {
		this.mfcNum = mfcNum;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Long factoryId) {
		this.factoryId = factoryId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Date getPeqdAt() {
		return peqdAt;
	}

	public void setPeqdAt(Date peqdAt) {
		this.peqdAt = peqdAt;
	}

	public Date getPlanToWarehDate() {
		return planToWarehDate;
	}

	public void setPlanToWarehDate(Date planToWarehDate) {
		this.planToWarehDate = planToWarehDate;
	}

	public Date getPreCarryDate() {
		return preCarryDate;
	}

	public void setPreCarryDate(Date preCarryDate) {
		this.preCarryDate = preCarryDate;
	}

	public String getIsWholeSubmit() {
		return isWholeSubmit;
	}

	public void setIsWholeSubmit(String isWholeSubmit) {
		this.isWholeSubmit = isWholeSubmit;
	}

	public String getIsCombineAssign() {
		return isCombineAssign;
	}

	public void setIsCombineAssign(String isCombineAssign) {
		this.isCombineAssign = isCombineAssign;
	}

	public String getIsReCalPrice() {
		return isReCalPrice;
	}

	public void setIsReCalPrice(String isReCalPrice) {
		this.isReCalPrice = isReCalPrice;
	}

	public String getIsGenStkOnBuy() {
		return isGenStkOnBuy;
	}

	public void setIsGenStkOnBuy(String isGenStkOnBuy) {
		this.isGenStkOnBuy = isGenStkOnBuy;
	}

	public String getIsGenStkOnRoad() {
		return isGenStkOnRoad;
	}

	public void setIsGenStkOnRoad(String isGenStkOnRoad) {
		this.isGenStkOnRoad = isGenStkOnRoad;
	}

	public String getIsCanDelivery() {
		return isCanDelivery;
	}

	public void setIsCanDelivery(String isCanDelivery) {
		this.isCanDelivery = isCanDelivery;
	}

	public String getIsCanAcross() {
		return isCanAcross;
	}

	public void setIsCanAcross(String isCanAcross) {
		this.isCanAcross = isCanAcross;
	}

	public String getIsGenLogRecDoc() {
		return isGenLogRecDoc;
	}

	public void setIsGenLogRecDoc(String isGenLogRecDoc) {
		this.isGenLogRecDoc = isGenLogRecDoc;
	}

	public String getIsGenPkn() {
		return isGenPkn;
	}

	public void setIsGenPkn(String isGenPkn) {
		this.isGenPkn = isGenPkn;
	}

	public String getIsFinnceBalance() {
		return isFinnceBalance;
	}

	public void setIsFinnceBalance(String isFinnceBalance) {
		this.isFinnceBalance = isFinnceBalance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
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
		this.createUser = createUser;
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
		this.lastModifiedUser = lastModifiedUser;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsStraightDelivery() {
		return isStraightDelivery;
	}

	public void setIsStraightDelivery(String isStraightDelivery) {
		this.isStraightDelivery = isStraightDelivery;
	}

	public String getDelivMode() {
		return delivMode;
	}

	public void setDelivMode(String delivMode) {
		this.delivMode = delivMode;
	}

	public String getIsQn() {
		return isQn;
	}

	public void setIsQn(String isQn) {
		this.isQn = isQn;
	}

	public Date getStrScanTime() {
		return strScanTime;
	}

	public void setStrScanTime(Date strScanTime) {
		this.strScanTime = strScanTime;
	}

	public String getIsGenGrn() {
		return isGenGrn;
	}

	public void setIsGenGrn(String isGenGrn) {
		this.isGenGrn = isGenGrn;
	}

	public Integer getActSenWarehId() {
		return actSenWarehId;
	}

	public void setActSenWarehId(Integer actSenWarehId) {
		this.actSenWarehId = actSenWarehId;
	}

	public Date getEndScanTime() {
		return endScanTime;
	}

	public void setEndScanTime(Date endScanTime) {
		this.endScanTime = endScanTime;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public Long getScanerId() {
		return scanerId;
	}

	public void setScanerId(Long scanerId) {
		this.scanerId = scanerId;
	}

	public Long getOlocId() {
		return olocId;
	}

	public void setOlocId(Long olocId) {
		this.olocId = olocId;
	}

	public String getPdaProgress() {
		return pdaProgress;
	}

	public void setPdaProgress(String pdaProgress) {
		this.pdaProgress = pdaProgress;
	}

	public String getPickType() {
		return pickType;
	}

	public void setPickType(String pickType) {
		this.pickType = pickType;
	}

	public Integer getPrintGTimes() {
		return printGTimes;
	}

	public void setPrintGTimes(Integer printGTimes) {
		this.printGTimes = printGTimes;
	}

	public String getOpeStatus() {
		return opeStatus;
	}

	public void setOpeStatus(String opeStatus) {
		this.opeStatus = opeStatus;
	}

	public String getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(String boxWeight) {
		this.boxWeight = boxWeight;
	}

	public String getPriExpType() {
		return priExpType;
	}

	public void setPriExpType(String priExpType) {
		this.priExpType = priExpType;
	}

	public String getPriMode() {
		return priMode;
	}

	public void setPriMode(String priMode) {
		this.priMode = priMode;
	}

	public String getCombinedDgnCode() {
		return combinedDgnCode;
	}

	public void setCombinedDgnCode(String combinedDgnCode) {
		this.combinedDgnCode = combinedDgnCode;
	}

	public Double getTotalBoxCount() {
		return totalBoxCount;
	}

	public void setTotalBoxCount(Double totalBoxCount) {
		this.totalBoxCount = totalBoxCount;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalVolumn() {
		return totalVolumn;
	}

	public void setTotalVolumn(Double totalVolumn) {
		this.totalVolumn = totalVolumn;
	}

	public Long getCombinedSymbol() {
		return combinedSymbol;
	}

	public void setCombinedSymbol(Long combinedSymbol) {
		this.combinedSymbol = combinedSymbol;
	}

	public Long getIsCombined() {
		return isCombined;
	}

	public void setIsCombined(Long isCombined) {
		this.isCombined = isCombined;
	}

	public String getSplitMode() {
		return splitMode;
	}

	public void setSplitMode(String splitMode) {
		this.splitMode = splitMode;
	}

	public String getClientOrderCode() {
		return clientOrderCode;
	}

	public void setClientOrderCode(String clientOrderCode) {
		this.clientOrderCode = clientOrderCode;
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public String getLowOrderFlag() {
		return lowOrderFlag;
	}

	public void setLowOrderFlag(String lowOrderFlag) {
		this.lowOrderFlag = lowOrderFlag;
	}

	public Long getLowShopId() {
		return lowShopId;
	}

	public void setLowShopId(Long lowShopId) {
		this.lowShopId = lowShopId;
	}

	public Long getSfBatMnnId() {
		return sfBatMnnId;
	}

	public void setSfBatMnnId(Long sfBatMnnId) {
		this.sfBatMnnId = sfBatMnnId;
	}

	public String getIsPrintGdn() {
		return isPrintGdn;
	}

	public void setIsPrintGdn(String isPrintGdn) {
		this.isPrintGdn = isPrintGdn;
	}

	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public String getOlocCode() {
		return olocCode;
	}

	public void setOlocCode(String olocCode) {
		this.olocCode = olocCode;
	}

	public String getPickMode() {
		return pickMode;
	}

	public void setPickMode(String pickMode) {
		this.pickMode = pickMode;
	}

	public String getTransState() {
		return transState;
	}

	public void setTransState(String transState) {
		this.transState = transState;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getTransferState() {
		return transferState;
	}

	public void setTransferState(String transferState) {
		this.transferState = transferState;
	}

	public Long getUbietyWareh() {
		return ubietyWareh;
	}

	public void setUbietyWareh(Long ubietyWareh) {
		this.ubietyWareh = ubietyWareh;
	}

	public Long getTransferOrgId() {
		return transferOrgId;
	}

	public void setTransferOrgId(Long transferOrgId) {
		this.transferOrgId = transferOrgId;
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

	public String getOriginDocNum() {
		return originDocNum;
	}

	public void setOriginDocNum(String originDocNum) {
		this.originDocNum = originDocNum;
	}

	public Double getTempCombinedSymbol() {
		return tempCombinedSymbol;
	}

	public void setTempCombinedSymbol(Double tempCombinedSymbol) {
		this.tempCombinedSymbol = tempCombinedSymbol;
	}

	public String getCombinFlagT() {
		return combinFlagT;
	}

	public void setCombinFlagT(String combinFlagT) {
		this.combinFlagT = combinFlagT;
	}

	public Long getTranWarehId() {
		return tranWarehId;
	}

	public void setTranWarehId(Long tranWarehId) {
		this.tranWarehId = tranWarehId;
	}

	public Long getGpicktime() {
		return gpicktime;
	}

	public void setGpicktime(Long gpicktime) {
		this.gpicktime = gpicktime;
	}

	public Long getActRcvUnitId() {
		return actRcvUnitId;
	}

	public void setActRcvUnitId(Long actRcvUnitId) {
		this.actRcvUnitId = actRcvUnitId;
	}

	public Long getActRcvWarehId() {
		return actRcvWarehId;
	}

	public void setActRcvWarehId(Long actRcvWarehId) {
		this.actRcvWarehId = actRcvWarehId;
	}

	public String getDgnTypeSl() {
		return dgnTypeSl;
	}

	public void setDgnTypeSl(String dgnTypeSl) {
		this.dgnTypeSl = dgnTypeSl;
	}

	public String getAddRess() {
		return addRess;
	}

	public void setAddRess(String addRess) {
		this.addRess = addRess;
	}

	public String getGdnState() {
		return gdnState;
	}

	public void setGdnState(String gdnState) {
		this.gdnState = gdnState;
	}

	public String getIfEndBat() {
		return ifEndBat;
	}

	public void setIfEndBat(String ifEndBat) {
		this.ifEndBat = ifEndBat;
	}

	public String getDelivMthd() {
		return delivMthd;
	}

	public void setDelivMthd(String delivMthd) {
		this.delivMthd = delivMthd;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDelivPstd() {
		return delivPstd;
	}

	public void setDelivPstd(String delivPstd) {
		this.delivPstd = delivPstd;
	}

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getPrintflag() {
		return printflag;
	}

	public void setPrintflag(String printflag) {
		this.printflag = printflag;
	}

	public Long getOosOpeShopid() {
		return oosOpeShopid;
	}

	public void setOosOpeShopid(Long oosOpeShopid) {
		this.oosOpeShopid = oosOpeShopid;
	}

	public String getIsInvoiceVerify() {
		return isInvoiceVerify;
	}

	public void setIsInvoiceVerify(String isInvoiceVerify) {
		this.isInvoiceVerify = isInvoiceVerify;
	}

	public String getIsAutoDist() {
		return isAutoDist;
	}

	public void setIsAutoDist(String isAutoDist) {
		this.isAutoDist = isAutoDist;
	}

	public String getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}

	public Date getPknConfirmTime() {
		return pknConfirmTime;
	}

	public void setPknConfirmTime(Date pknConfirmTime) {
		this.pknConfirmTime = pknConfirmTime;
	}

	public String getWifNum() {
		return wifNum;
	}

	public void setWifNum(String wifNum) {
		this.wifNum = wifNum;
	}

	public String getOerpRemark() {
		return oerpRemark;
	}

	public void setOerpRemark(String oerpRemark) {
		this.oerpRemark = oerpRemark;
	}

	public String getTransferValided() {
		return transferValided;
	}

	public void setTransferValided(String transferValided) {
		this.transferValided = transferValided;
	}

	public String getIsMerger() {
		return isMerger;
	}

	public void setIsMerger(String isMerger) {
		this.isMerger = isMerger;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}

	public List<SfDgnDtlVo> getSfDgnDtlVos() {
		return sfDgnDtlVos;
	}

	public void setSfDgnDtlVos(List<SfDgnDtlVo> sfDgnDtlVos) {
		this.sfDgnDtlVos = sfDgnDtlVos;
	}

	public Long getSfGdnId() {
		return sfGdnId;
	}

	public void setSfGdnId(Long sfGdnId) {
		this.sfGdnId = sfGdnId;
	}
	
}