package mb.erp.dr.soa.vo;

import java.util.Date;
import java.util.List;

/**
 * 新ERP现货订单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfIdtVo
 * @since       全流通改造
 */
public class SfIdtVo extends NewBaseBizVo{
	private static final long serialVersionUID = -4434707472441559885L;

	private Long id; //内码

    private Long bfOrgVendeeId; //购货方ID
    
    private String code; //编码

    private Date docDate; //单据日期

    private Long bfOrgVenderId; //供货方ID

    private String pidNum; //引单编码

    private Long buyerId; //购买人ID

    private Long sellerId; //售卖人ID

    private Long bfOrgRcvWarehId; //接收仓库ID

    private String delivMthd; //送货方式

    private String delivAddr; //送货地址

    private String delivPstd; //邮编

    private Date reqdAt; //货期

    private Double taxRate; //税率

    private Double orderQty; //订货数量

    private Double orderVal; //订货金额

    private Double admQty; //配货数量

    private Double admVal; //配货金额

    private Double productCount; //品项数

    private String idtType; //订单类型

    private String adnTag; //配发方式

    private String pidType; //来源单据类型

    private String citType; //引单类型

    private Date efficientTime; //生效时间

    private Long bfOrgShopId; //门店ID

    private String lowIdtFlag; //是否为下级门店订货

    private Long bfOrgLowShopId; //下级门店ID

    private Long brandId; //品牌ID

    private Integer docState; //进度

    private String createUser; //创建人

    private Date createDate; //创建时间

    private String lastModifiedUser; //最后修改人

    private Date lastModifiedDate; //最后修改日期

    private String diyIdtType; //自助订单类型

    private String orderType; //订购类型

    private Long admTimes; //分配次数

    private String urgency; //紧急程度

    private String fiLockStatus; //资金锁定状态

    private Double fillRate; //全单满足率

    private Double colorFillRate; //全色满足率

    private Double sizeFillRate; //全码满足率

    private String customerProp; //客户属性

    private Long dispWarehId; //发货仓库ID

    private Long dispUnitId; //发货方ID

    private Long rcvUnitId; //结算方ID

    private String saleType; //交易类型

    private Long frzUnitId; //账户组织ID

    private String urgencyReason; //紧急原因

    private String ocode; //老ERP订货单编号

    private Long shippingId; //承运商ID

    private Long combinedIdtId;

    private String flag;

    private Integer sfScnId;

    private String sfScnCode; //退货单编号

    private String oosOpeShopid;

    private String oosOpeAgentid;

    private Long suspendDocState;

    private String innerOrderno;

    private Double delivQty;

    private Double delivVal;

    private Double rcvQty;

    private Double rcvVal;

    private String country;

    private String province;

    private String city;

    private String county;

    private String dataSource;

    private String approved;

    private String oldIdtCode;

    private String messages;

    private String distStatus; //统一配货状态

    private Double varieties;

    private Long orderCategory;

    private String reason;

    private Long invoicesOrgId;

    private String sfIdtType;

    private Double differQty;

    private String differFlag;

    private Long differBatch;

    private Double allocState;

    private String dgnType;

    private String oldGrnCode; //老ERP入库单编号

    private String country2; //县

    private Integer orderDistNumber; //订单分配次数

    private String distAuditResults; //分配审核结果

    private String distRemark; //分配备注

    private String nsState; //IN:Initial、CF:Confirm、CA:Cancel

    private Long actDeliveWarehId; //实际发货仓库ID

    private Date lockedDate; //线下门店锁定时间

    private String isOos; //全流通类型 T:线下 F:非全流通 M:线上

    private String shopGained; //门店可以获取到的订单

    private Long getAddressShopId; //自提门店

    private Long alloctedShopId; //配发门店

    private String shopGeted; //自提门店已获取

    private String isMultiBox; //多包裹订单

    private Long yxGainedNum; //易迅运单号获取次数

    private Double ordersRatio; //利益分成比率（订货方）

    private Double servicersRatio; //利益分成比率（服务方）

    private Double shipperRatio; //利益分成比率（发货方）

    private String rllNum; //销售小票号

    private String orgDocType;

    private String payBacked; //撤单断色断码订单已释放占用金额

    private String oerpRemark; //断色断码订单出库后回传老ERP生成相关单据成功失败的备注

    private String transferValided; //用结果回执说明（0或者空为需要传、1为传入成功、2为释放金额失败）

    private String isLockedMon; //下发订单时是否需要占用余额  0或者空:需要   1:不需要

    private String reallocUser; //重新分配人员

    private Date reallocTime; //重新分配时间

    private String isDispReq;

    private String isPickup; //承运商是否取件

    private Date pickupDate; //承运商取件时间

    private Double allocIng;

    private String cancelReason; //撤单原因

    private String b2cidtType; //订单类型

    private String isDftDisp;

    private Long appointWarehId;

    private String isC2b; //是否C2B订单

    private Long distState; //分配状态

    private String consignType;

    private String osDocCode;

    private String distTaskError; //订单分配异常信息

    private Long distTaskId; //单分配任务登记表Id
    
    private String docSource;
    
    private List<SfIdtDtlVo> sfIdtDtlVos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBfOrgVendeeId() {
		return bfOrgVendeeId;
	}

	public void setBfOrgVendeeId(Long bfOrgVendeeId) {
		this.bfOrgVendeeId = bfOrgVendeeId;
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

	public Long getBfOrgVenderId() {
		return bfOrgVenderId;
	}

	public void setBfOrgVenderId(Long bfOrgVenderId) {
		this.bfOrgVenderId = bfOrgVenderId;
	}

	public String getPidNum() {
		return pidNum;
	}

	public void setPidNum(String pidNum) {
		this.pidNum = pidNum;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBfOrgRcvWarehId() {
		return bfOrgRcvWarehId;
	}

	public void setBfOrgRcvWarehId(Long bfOrgRcvWarehId) {
		this.bfOrgRcvWarehId = bfOrgRcvWarehId;
	}

	public String getDelivMthd() {
		return delivMthd;
	}

	public void setDelivMthd(String delivMthd) {
		this.delivMthd = delivMthd;
	}

	public String getDelivAddr() {
		return delivAddr;
	}

	public void setDelivAddr(String delivAddr) {
		this.delivAddr = delivAddr;
	}

	public String getDelivPstd() {
		return delivPstd;
	}

	public void setDelivPstd(String delivPstd) {
		this.delivPstd = delivPstd;
	}

	public Date getReqdAt() {
		return reqdAt;
	}

	public void setReqdAt(Date reqdAt) {
		this.reqdAt = reqdAt;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	public Double getOrderVal() {
		return orderVal;
	}

	public void setOrderVal(Double orderVal) {
		this.orderVal = orderVal;
	}

	public Double getAdmQty() {
		return admQty;
	}

	public void setAdmQty(Double admQty) {
		this.admQty = admQty;
	}

	public Double getAdmVal() {
		return admVal;
	}

	public void setAdmVal(Double admVal) {
		this.admVal = admVal;
	}

	public Double getProductCount() {
		return productCount;
	}

	public void setProductCount(Double productCount) {
		this.productCount = productCount;
	}

	public String getIdtType() {
		return idtType;
	}

	public void setIdtType(String idtType) {
		this.idtType = idtType;
	}

	public String getAdnTag() {
		return adnTag;
	}

	public void setAdnTag(String adnTag) {
		this.adnTag = adnTag;
	}

	public String getPidType() {
		return pidType;
	}

	public void setPidType(String pidType) {
		this.pidType = pidType;
	}

	public String getCitType() {
		return citType;
	}

	public void setCitType(String citType) {
		this.citType = citType;
	}

	public Date getEfficientTime() {
		return efficientTime;
	}

	public void setEfficientTime(Date efficientTime) {
		this.efficientTime = efficientTime;
	}

	public Long getBfOrgShopId() {
		return bfOrgShopId;
	}

	public void setBfOrgShopId(Long bfOrgShopId) {
		this.bfOrgShopId = bfOrgShopId;
	}

	public String getLowIdtFlag() {
		return lowIdtFlag;
	}

	public void setLowIdtFlag(String lowIdtFlag) {
		this.lowIdtFlag = lowIdtFlag;
	}

	public Long getBfOrgLowShopId() {
		return bfOrgLowShopId;
	}

	public void setBfOrgLowShopId(Long bfOrgLowShopId) {
		this.bfOrgLowShopId = bfOrgLowShopId;
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

	public String getDiyIdtType() {
		return diyIdtType;
	}

	public void setDiyIdtType(String diyIdtType) {
		this.diyIdtType = diyIdtType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getAdmTimes() {
		return admTimes;
	}

	public void setAdmTimes(Long admTimes) {
		this.admTimes = admTimes;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getFiLockStatus() {
		return fiLockStatus;
	}

	public void setFiLockStatus(String fiLockStatus) {
		this.fiLockStatus = fiLockStatus;
	}

	public Double getFillRate() {
		return fillRate;
	}

	public void setFillRate(Double fillRate) {
		this.fillRate = fillRate;
	}

	public Double getColorFillRate() {
		return colorFillRate;
	}

	public void setColorFillRate(Double colorFillRate) {
		this.colorFillRate = colorFillRate;
	}

	public Double getSizeFillRate() {
		return sizeFillRate;
	}

	public void setSizeFillRate(Double sizeFillRate) {
		this.sizeFillRate = sizeFillRate;
	}

	public String getCustomerProp() {
		return customerProp;
	}

	public void setCustomerProp(String customerProp) {
		this.customerProp = customerProp;
	}

	public Long getDispWarehId() {
		return dispWarehId;
	}

	public void setDispWarehId(Long dispWarehId) {
		this.dispWarehId = dispWarehId;
	}

	public Long getDispUnitId() {
		return dispUnitId;
	}

	public void setDispUnitId(Long dispUnitId) {
		this.dispUnitId = dispUnitId;
	}

	public Long getRcvUnitId() {
		return rcvUnitId;
	}

	public void setRcvUnitId(Long rcvUnitId) {
		this.rcvUnitId = rcvUnitId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Long getFrzUnitId() {
		return frzUnitId;
	}

	public void setFrzUnitId(Long frzUnitId) {
		this.frzUnitId = frzUnitId;
	}

	public String getUrgencyReason() {
		return urgencyReason;
	}

	public void setUrgencyReason(String urgencyReason) {
		this.urgencyReason = urgencyReason;
	}

	public String getOcode() {
		return ocode;
	}

	public void setOcode(String ocode) {
		this.ocode = ocode;
	}

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public Long getCombinedIdtId() {
		return combinedIdtId;
	}

	public void setCombinedIdtId(Long combinedIdtId) {
		this.combinedIdtId = combinedIdtId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getSfScnId() {
		return sfScnId;
	}

	public void setSfScnId(Integer sfScnId) {
		this.sfScnId = sfScnId;
	}

	public String getSfScnCode() {
		return sfScnCode;
	}

	public void setSfScnCode(String sfScnCode) {
		this.sfScnCode = sfScnCode;
	}

	public String getOosOpeShopid() {
		return oosOpeShopid;
	}

	public void setOosOpeShopid(String oosOpeShopid) {
		this.oosOpeShopid = oosOpeShopid;
	}

	public String getOosOpeAgentid() {
		return oosOpeAgentid;
	}

	public void setOosOpeAgentid(String oosOpeAgentid) {
		this.oosOpeAgentid = oosOpeAgentid;
	}

	public Long getSuspendDocState() {
		return suspendDocState;
	}

	public void setSuspendDocState(Long suspendDocState) {
		this.suspendDocState = suspendDocState;
	}

	public String getInnerOrderno() {
		return innerOrderno;
	}

	public void setInnerOrderno(String innerOrderno) {
		this.innerOrderno = innerOrderno;
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

	public Double getRcvQty() {
		return rcvQty;
	}

	public void setRcvQty(Double rcvQty) {
		this.rcvQty = rcvQty;
	}

	public Double getRcvVal() {
		return rcvVal;
	}

	public void setRcvVal(Double rcvVal) {
		this.rcvVal = rcvVal;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getOldIdtCode() {
		return oldIdtCode;
	}

	public void setOldIdtCode(String oldIdtCode) {
		this.oldIdtCode = oldIdtCode;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getDistStatus() {
		return distStatus;
	}

	public void setDistStatus(String distStatus) {
		this.distStatus = distStatus;
	}

	public Double getVarieties() {
		return varieties;
	}

	public void setVarieties(Double varieties) {
		this.varieties = varieties;
	}

	public Long getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(Long orderCategory) {
		this.orderCategory = orderCategory;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getInvoicesOrgId() {
		return invoicesOrgId;
	}

	public void setInvoicesOrgId(Long invoicesOrgId) {
		this.invoicesOrgId = invoicesOrgId;
	}

	public String getSfIdtType() {
		return sfIdtType;
	}

	public void setSfIdtType(String sfIdtType) {
		this.sfIdtType = sfIdtType;
	}

	public Double getDifferQty() {
		return differQty;
	}

	public void setDifferQty(Double differQty) {
		this.differQty = differQty;
	}

	public String getDifferFlag() {
		return differFlag;
	}

	public void setDifferFlag(String differFlag) {
		this.differFlag = differFlag;
	}

	public Long getDifferBatch() {
		return differBatch;
	}

	public void setDifferBatch(Long differBatch) {
		this.differBatch = differBatch;
	}

	public Double getAllocState() {
		return allocState;
	}

	public void setAllocState(Double allocState) {
		this.allocState = allocState;
	}

	public String getDgnType() {
		return dgnType;
	}

	public void setDgnType(String dgnType) {
		this.dgnType = dgnType;
	}

	public String getOldGrnCode() {
		return oldGrnCode;
	}

	public void setOldGrnCode(String oldGrnCode) {
		this.oldGrnCode = oldGrnCode;
	}

	public String getCountry2() {
		return country2;
	}

	public void setCountry2(String country2) {
		this.country2 = country2;
	}

	public Integer getOrderDistNumber() {
		return orderDistNumber;
	}

	public void setOrderDistNumber(Integer orderDistNumber) {
		this.orderDistNumber = orderDistNumber;
	}

	public String getDistAuditResults() {
		return distAuditResults;
	}

	public void setDistAuditResults(String distAuditResults) {
		this.distAuditResults = distAuditResults;
	}

	public String getDistRemark() {
		return distRemark;
	}

	public void setDistRemark(String distRemark) {
		this.distRemark = distRemark;
	}

	public String getNsState() {
		return nsState;
	}

	public void setNsState(String nsState) {
		this.nsState = nsState;
	}

	public Long getActDeliveWarehId() {
		return actDeliveWarehId;
	}

	public void setActDeliveWarehId(Long actDeliveWarehId) {
		this.actDeliveWarehId = actDeliveWarehId;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public String getIsOos() {
		return isOos;
	}

	public void setIsOos(String isOos) {
		this.isOos = isOos;
	}

	public String getShopGained() {
		return shopGained;
	}

	public void setShopGained(String shopGained) {
		this.shopGained = shopGained;
	}

	public Long getGetAddressShopId() {
		return getAddressShopId;
	}

	public void setGetAddressShopId(Long getAddressShopId) {
		this.getAddressShopId = getAddressShopId;
	}

	public Long getAlloctedShopId() {
		return alloctedShopId;
	}

	public void setAlloctedShopId(Long alloctedShopId) {
		this.alloctedShopId = alloctedShopId;
	}

	public String getShopGeted() {
		return shopGeted;
	}

	public void setShopGeted(String shopGeted) {
		this.shopGeted = shopGeted;
	}

	public String getIsMultiBox() {
		return isMultiBox;
	}

	public void setIsMultiBox(String isMultiBox) {
		this.isMultiBox = isMultiBox;
	}

	public Long getYxGainedNum() {
		return yxGainedNum;
	}

	public void setYxGainedNum(Long yxGainedNum) {
		this.yxGainedNum = yxGainedNum;
	}

	public Double getOrdersRatio() {
		return ordersRatio;
	}

	public void setOrdersRatio(Double ordersRatio) {
		this.ordersRatio = ordersRatio;
	}

	public Double getServicersRatio() {
		return servicersRatio;
	}

	public void setServicersRatio(Double servicersRatio) {
		this.servicersRatio = servicersRatio;
	}

	public Double getShipperRatio() {
		return shipperRatio;
	}

	public void setShipperRatio(Double shipperRatio) {
		this.shipperRatio = shipperRatio;
	}

	public String getRllNum() {
		return rllNum;
	}

	public void setRllNum(String rllNum) {
		this.rllNum = rllNum;
	}

	public String getOrgDocType() {
		return orgDocType;
	}

	public void setOrgDocType(String orgDocType) {
		this.orgDocType = orgDocType;
	}

	public String getPayBacked() {
		return payBacked;
	}

	public void setPayBacked(String payBacked) {
		this.payBacked = payBacked;
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

	public String getIsLockedMon() {
		return isLockedMon;
	}

	public void setIsLockedMon(String isLockedMon) {
		this.isLockedMon = isLockedMon;
	}

	public String getReallocUser() {
		return reallocUser;
	}

	public void setReallocUser(String reallocUser) {
		this.reallocUser = reallocUser;
	}

	public Date getReallocTime() {
		return reallocTime;
	}

	public void setReallocTime(Date reallocTime) {
		this.reallocTime = reallocTime;
	}

	public String getIsDispReq() {
		return isDispReq;
	}

	public void setIsDispReq(String isDispReq) {
		this.isDispReq = isDispReq;
	}

	public String getIsPickup() {
		return isPickup;
	}

	public void setIsPickup(String isPickup) {
		this.isPickup = isPickup;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Double getAllocIng() {
		return allocIng;
	}

	public void setAllocIng(Double allocIng) {
		this.allocIng = allocIng;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getB2cidtType() {
		return b2cidtType;
	}

	public void setB2cidtType(String b2cidtType) {
		this.b2cidtType = b2cidtType;
	}

	public String getIsDftDisp() {
		return isDftDisp;
	}

	public void setIsDftDisp(String isDftDisp) {
		this.isDftDisp = isDftDisp;
	}

	public Long getAppointWarehId() {
		return appointWarehId;
	}

	public void setAppointWarehId(Long appointWarehId) {
		this.appointWarehId = appointWarehId;
	}

	public String getIsC2b() {
		return isC2b;
	}

	public void setIsC2b(String isC2b) {
		this.isC2b = isC2b;
	}

	public Long getDistState() {
		return distState;
	}

	public void setDistState(Long distState) {
		this.distState = distState;
	}

	public String getConsignType() {
		return consignType;
	}

	public void setConsignType(String consignType) {
		this.consignType = consignType;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}

	public String getDistTaskError() {
		return distTaskError;
	}

	public void setDistTaskError(String distTaskError) {
		this.distTaskError = distTaskError;
	}

	public Long getDistTaskId() {
		return distTaskId;
	}

	public void setDistTaskId(Long distTaskId) {
		this.distTaskId = distTaskId;
	}

	public List<SfIdtDtlVo> getSfIdtDtlVos() {
		return sfIdtDtlVos;
	}

	public void setSfIdtDtlVos(List<SfIdtDtlVo> sfIdtDtlVos) {
		this.sfIdtDtlVos = sfIdtDtlVos;
	}

	public String getDocSource() {
		return docSource;
	}

	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}
    
}