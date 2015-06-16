package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

/**
 * 现货订单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         IdtVo
 * @since       全流通改造
 */
public class IdtVo extends BaseBizVo{
	
	private static final long serialVersionUID = -2373370913473461452L;
	

    private Date docDate;  //单据日期

    private String vendeeId;  //购货方编码

    private String venderId;  //供货方编码

    private String idtNum;  //订单编号

    private String pidNum;  //引单编码

    private String oprId;  //操作员编码

    private String buyerId;  //购买人编码

    private String sellerId;  //售卖人编码

    private String invsgId;  //审批人编码

    private String rcvWarehId;  //接收仓库编码

    private String delivMthd;  //送货方式

    private String delivAddr;  //送货地址

    private String delivPstd;  //邮编

    private Date reqdAt;  //货期

    private Double taxRate;  //税率

    private Double orderQty;  //订货数量

    private Double orderVal;  //订货金额

    private Double admQty;  //配货数量

    private Double admVal;  //配货金额

    private Double delivQty;  //发货数量

    private Double delivVal;  //发货金额

    private Double rcvQty;  //收货数量

    private Double rcvVal;  //收货金额

    private Date ordAt;  //订货日期

    private Double productCount;  //品项数

    private String citType;  //引单类型

    private String adnTag;  //

    private String idtType;  //订单类型

    private String pidType;  //来源单据类型

    private String shopId;  //门店ID

    private String lowIdtFlag;  //是否为下级门店订货

    private String lowShopId;  //下级门店ID

    private String brandId;  //品牌ID

    private String cuUnitId;  //来源组织ID

    private Date efficientTime;  //有效时间

    private String innerOrderno;  //内向交货单号

    private String messages;  //反馈消息

    private Long topicSeqId;  //宣传品主题ID

    private Date lastModifiedDate;  //最后修改日期

    private String newIdtId;  //新ERP订单CODE

    private String dataSource;  //数据来源

    private String approved;  //决策方

    private String distStatus;  //分配状态

    private Double orderDistNumber;  //分配次数

    private String nsState;  //缺货状态

    private String autoDist;  //是否参与统一配货

    private Double distCount;  //分配总量

    private String distAuditResults;  //分配审核结果

    private Date beginDistTime;  //开始分配时间

    private String distRemark;  //配货失败备注

    private String isDispReq;  //是否跨仓要求

    private String isOos;  //全球通类型

    private String isPicked;  //是否货已提

    private String isDftDisp;  //是否默认配发仓

    private String appointWarehId;  //指定配发仓库

    private String isNeed;  //是否指定仓库

    private String isStockDisp;  //分配次数

    private String distMsg;  //是否指定库存分配

    private String consignType;  //代销类型
    
    private String osDocCode; //OS订单号

    private List<IdtDtlVo> idtDtlVos;


    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getPidNum() {
        return pidNum;
    }

    public void setPidNum(String pidNum) {
        this.pidNum = pidNum == null ? null : pidNum.trim();
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId == null ? null : oprId.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getInvsgId() {
        return invsgId;
    }

    public void setInvsgId(String invsgId) {
        this.invsgId = invsgId == null ? null : invsgId.trim();
    }

    public String getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(String rcvWarehId) {
        this.rcvWarehId = rcvWarehId == null ? null : rcvWarehId.trim();
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

    public Date getOrdAt() {
        return ordAt;
    }

    public void setOrdAt(Date ordAt) {
        this.ordAt = ordAt;
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public String getCitType() {
        return citType;
    }

    public void setCitType(String citType) {
        this.citType = citType == null ? null : citType.trim();
    }

    public String getAdnTag() {
        return adnTag;
    }

    public void setAdnTag(String adnTag) {
        this.adnTag = adnTag == null ? null : adnTag.trim();
    }

    public String getIdtType() {
        return idtType;
    }

    public void setIdtType(String idtType) {
        this.idtType = idtType == null ? null : idtType.trim();
    }

    public String getPidType() {
        return pidType;
    }

    public void setPidType(String pidType) {
        this.pidType = pidType == null ? null : pidType.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getLowIdtFlag() {
        return lowIdtFlag;
    }

    public void setLowIdtFlag(String lowIdtFlag) {
        this.lowIdtFlag = lowIdtFlag == null ? null : lowIdtFlag.trim();
    }

    public String getLowShopId() {
        return lowShopId;
    }

    public void setLowShopId(String lowShopId) {
        this.lowShopId = lowShopId == null ? null : lowShopId.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getCuUnitId() {
        return cuUnitId;
    }

    public void setCuUnitId(String cuUnitId) {
        this.cuUnitId = cuUnitId == null ? null : cuUnitId.trim();
    }

    public Date getEfficientTime() {
        return efficientTime;
    }

    public void setEfficientTime(Date efficientTime) {
        this.efficientTime = efficientTime;
    }

    public String getInnerOrderno() {
        return innerOrderno;
    }

    public void setInnerOrderno(String innerOrderno) {
        this.innerOrderno = innerOrderno == null ? null : innerOrderno.trim();
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages == null ? null : messages.trim();
    }

    public Long getTopicSeqId() {
        return topicSeqId;
    }

    public void setTopicSeqId(Long topicSeqId) {
        this.topicSeqId = topicSeqId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getNewIdtId() {
        return newIdtId;
    }

    public void setNewIdtId(String newIdtId) {
        this.newIdtId = newIdtId == null ? null : newIdtId.trim();
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

    public String getDistStatus() {
        return distStatus;
    }

    public void setDistStatus(String distStatus) {
        this.distStatus = distStatus == null ? null : distStatus.trim();
    }

    public Double getOrderDistNumber() {
        return orderDistNumber;
    }

    public void setOrderDistNumber(Double orderDistNumber) {
        this.orderDistNumber = orderDistNumber;
    }

    public String getNsState() {
        return nsState;
    }

    public void setNsState(String nsState) {
        this.nsState = nsState == null ? null : nsState.trim();
    }

    public String getAutoDist() {
        return autoDist;
    }

    public void setAutoDist(String autoDist) {
        this.autoDist = autoDist == null ? null : autoDist.trim();
    }

    public Double getDistCount() {
        return distCount;
    }

    public void setDistCount(Double distCount) {
        this.distCount = distCount;
    }

    public String getDistAuditResults() {
        return distAuditResults;
    }

    public void setDistAuditResults(String distAuditResults) {
        this.distAuditResults = distAuditResults == null ? null : distAuditResults.trim();
    }

    public Date getBeginDistTime() {
        return beginDistTime;
    }

    public void setBeginDistTime(Date beginDistTime) {
        this.beginDistTime = beginDistTime;
    }

    public String getDistRemark() {
        return distRemark;
    }

    public void setDistRemark(String distRemark) {
        this.distRemark = distRemark == null ? null : distRemark.trim();
    }

    public String getIsDispReq() {
        return isDispReq;
    }

    public void setIsDispReq(String isDispReq) {
        this.isDispReq = isDispReq == null ? null : isDispReq.trim();
    }

    public String getIsOos() {
        return isOos;
    }

    public void setIsOos(String isOos) {
        this.isOos = isOos == null ? null : isOos.trim();
    }

    public String getIsPicked() {
        return isPicked;
    }

    public void setIsPicked(String isPicked) {
        this.isPicked = isPicked == null ? null : isPicked.trim();
    }

    public String getIsDftDisp() {
        return isDftDisp;
    }

    public void setIsDftDisp(String isDftDisp) {
        this.isDftDisp = isDftDisp == null ? null : isDftDisp.trim();
    }

    public String getAppointWarehId() {
        return appointWarehId;
    }

    public void setAppointWarehId(String appointWarehId) {
        this.appointWarehId = appointWarehId == null ? null : appointWarehId.trim();
    }

    public String getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(String isNeed) {
        this.isNeed = isNeed == null ? null : isNeed.trim();
    }

    public String getIsStockDisp() {
        return isStockDisp;
    }

    public void setIsStockDisp(String isStockDisp) {
        this.isStockDisp = isStockDisp == null ? null : isStockDisp.trim();
    }

    public String getDistMsg() {
        return distMsg;
    }

    public void setDistMsg(String distMsg) {
        this.distMsg = distMsg == null ? null : distMsg.trim();
    }

    public String getConsignType() {
        return consignType;
    }

    public void setConsignType(String consignType) {
        this.consignType = consignType == null ? null : consignType.trim();
    }

	public String getIdtNum() {
		return idtNum;
	}

	public void setIdtNum(String idtNum) {
		this.idtNum = idtNum;
	}

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public List<IdtDtlVo> getIdtDtlVos() {
		return idtDtlVos;
	}

	public void setIdtDtlVos(List<IdtDtlVo> idtDtlVos) {
		this.idtDtlVos = idtDtlVos;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}
}