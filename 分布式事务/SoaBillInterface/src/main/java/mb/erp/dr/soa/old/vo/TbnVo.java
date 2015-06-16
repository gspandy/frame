package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

/**
 * 调配单vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         TbnVo
 * @since       全流通改造
 */
public class TbnVo extends BaseBizVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8825808970498463129L;

    private String venderId;	//	供货方编码
    private String tbnNum;	//	调配单编号
    private Date docDate;	//	单据日期
    private String vendeeId;	//	购货方编码
    private String dispWarehId;	//	发货仓库编码
    private String rcvWarehId;	//	接收仓库编码
    private String adnNum;	//	计划配货单号
    private String oprId;	//	操作员编码
    private String buyerId;	//	购买人编码
    private String sellerId;	//	售卖人编码
    private String invsgId;	//	审批人编码
    private String delivMthd;	//	送货方式
    private String delivAddr;	//	送货地址
    private String delivPstd;	//	邮编
    private Date reqdAt;	//	要求送货时间
    private Date dispTime;	//	发货时间
    private Date rcvTime;	//	收货时间
    private Double taxRate;	//	税率
    private Double crQty;	//	调配数量
    private Double crVal;	//	调配金额
    private Double delivQty;	//	发货数量
    private Double delivVal;	//	发货金额
    private Double rcvQty;	//	收货数量
    private Double rcvVal;	//	收货金额
    private Date invsgTime;	//	审批时间
    private String volvFinid;	//	中转仓编码
    private String rcvid;	//	接收方编码
    private Double crOrderval;	//	调配重算金额
    private String tbnType;	//	调配类型
    private String brandId;	//	品牌ID
    private String factVendeeId;	//	实际购货方ID
    private String factWarehId;	//	实际发货仓库编码
    private String factIdtNum;	//	实际现货订单编码
    private String srcUnitId;	//	原始组织编码
    private String srcDocNum;	//	原始单据编码
    private String srcDocType;	//	原始单据类型
    private String reasonCode;	//	调配原因
    private String isuniform;	//	是否B2C统一配货
    private String dataSource;	//	数据来源
    private String approved;	//	决策方
    private String isRefreshPrice;	//	是否重算价格
    private String erpType;	//	ERP类型
    private String isUnion;	//	是否B2B统一配货
    private String rcvAddr;	//	接收地址
    private String rcvUser;	//	接收人
    private String rcvPhoneNo;	//	接收人电话
    private String isOos;	//	全流通类型
    private String expressNum;	//	快递单号
    private String expressState;	//	快递状态
    private String expressCo;	//	快递联系人
    private Double expressFee;	//	快递费用
    private String orgDocNum;	//	源头单据编码
    private String orgDocType;	//	源头单据类型
    private String consignType;	//	代销类型
    private String osDocCode; //OS订单号
    
    private List<TbnDtlVo> tbnDtlVos;

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getTbnNum() {
		return tbnNum;
	}

	public void setTbnNum(String tbnNum) {
		this.tbnNum = tbnNum;
	}

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getDispWarehId() {
        return dispWarehId;
    }

    public void setDispWarehId(String dispWarehId) {
        this.dispWarehId = dispWarehId == null ? null : dispWarehId.trim();
    }

    public String getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(String rcvWarehId) {
        this.rcvWarehId = rcvWarehId == null ? null : rcvWarehId.trim();
    }

    public String getAdnNum() {
        return adnNum;
    }

    public void setAdnNum(String adnNum) {
        this.adnNum = adnNum == null ? null : adnNum.trim();
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

    public Date getDispTime() {
        return dispTime;
    }

    public void setDispTime(Date dispTime) {
        this.dispTime = dispTime;
    }

    public Date getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(Date rcvTime) {
        this.rcvTime = rcvTime;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getCrQty() {
        return crQty;
    }

    public void setCrQty(Double crQty) {
        this.crQty = crQty;
    }

    public Double getCrVal() {
        return crVal;
    }

    public void setCrVal(Double crVal) {
        this.crVal = crVal;
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


    public Date getInvsgTime() {
        return invsgTime;
    }

    public void setInvsgTime(Date invsgTime) {
        this.invsgTime = invsgTime;
    }

    public String getVolvFinid() {
        return volvFinid;
    }

    public void setVolvFinid(String volvFinid) {
        this.volvFinid = volvFinid == null ? null : volvFinid.trim();
    }

    public String getRcvid() {
        return rcvid;
    }

    public void setRcvid(String rcvid) {
        this.rcvid = rcvid == null ? null : rcvid.trim();
    }

    public Double getCrOrderval() {
        return crOrderval;
    }

    public void setCrOrderval(Double crOrderval) {
        this.crOrderval = crOrderval;
    }

    public String getTbnType() {
        return tbnType;
    }

    public void setTbnType(String tbnType) {
        this.tbnType = tbnType == null ? null : tbnType.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getFactVendeeId() {
        return factVendeeId;
    }

    public void setFactVendeeId(String factVendeeId) {
        this.factVendeeId = factVendeeId == null ? null : factVendeeId.trim();
    }

    public String getFactWarehId() {
        return factWarehId;
    }

    public void setFactWarehId(String factWarehId) {
        this.factWarehId = factWarehId == null ? null : factWarehId.trim();
    }

    public String getFactIdtNum() {
        return factIdtNum;
    }

    public void setFactIdtNum(String factIdtNum) {
        this.factIdtNum = factIdtNum == null ? null : factIdtNum.trim();
    }

    public String getSrcUnitId() {
        return srcUnitId;
    }

    public void setSrcUnitId(String srcUnitId) {
        this.srcUnitId = srcUnitId == null ? null : srcUnitId.trim();
    }

    public String getSrcDocNum() {
        return srcDocNum;
    }

    public void setSrcDocNum(String srcDocNum) {
        this.srcDocNum = srcDocNum == null ? null : srcDocNum.trim();
    }

    public String getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(String srcDocType) {
        this.srcDocType = srcDocType == null ? null : srcDocType.trim();
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode == null ? null : reasonCode.trim();
    }

    public String getIsuniform() {
        return isuniform;
    }

    public void setIsuniform(String isuniform) {
        this.isuniform = isuniform == null ? null : isuniform.trim();
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

    public String getIsRefreshPrice() {
        return isRefreshPrice;
    }

    public void setIsRefreshPrice(String isRefreshPrice) {
        this.isRefreshPrice = isRefreshPrice == null ? null : isRefreshPrice.trim();
    }

    public String getErpType() {
        return erpType;
    }

    public void setErpType(String erpType) {
        this.erpType = erpType == null ? null : erpType.trim();
    }

    public String getIsUnion() {
        return isUnion;
    }

    public void setIsUnion(String isUnion) {
        this.isUnion = isUnion == null ? null : isUnion.trim();
    }

    public String getRcvAddr() {
        return rcvAddr;
    }

    public void setRcvAddr(String rcvAddr) {
        this.rcvAddr = rcvAddr == null ? null : rcvAddr.trim();
    }

    public String getRcvUser() {
        return rcvUser;
    }

    public void setRcvUser(String rcvUser) {
        this.rcvUser = rcvUser == null ? null : rcvUser.trim();
    }

    public String getRcvPhoneNo() {
        return rcvPhoneNo;
    }

    public void setRcvPhoneNo(String rcvPhoneNo) {
        this.rcvPhoneNo = rcvPhoneNo == null ? null : rcvPhoneNo.trim();
    }

    public String getIsOos() {
        return isOos;
    }

    public void setIsOos(String isOos) {
        this.isOos = isOos == null ? null : isOos.trim();
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum == null ? null : expressNum.trim();
    }

    public String getExpressState() {
        return expressState;
    }

    public void setExpressState(String expressState) {
        this.expressState = expressState == null ? null : expressState.trim();
    }

    public String getExpressCo() {
        return expressCo;
    }

    public void setExpressCo(String expressCo) {
        this.expressCo = expressCo == null ? null : expressCo.trim();
    }

    public Double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Double expressFee) {
        this.expressFee = expressFee;
    }

    public String getOrgDocNum() {
        return orgDocNum;
    }

    public void setOrgDocNum(String orgDocNum) {
        this.orgDocNum = orgDocNum == null ? null : orgDocNum.trim();
    }

    public String getOrgDocType() {
        return orgDocType;
    }

    public void setOrgDocType(String orgDocType) {
        this.orgDocType = orgDocType == null ? null : orgDocType.trim();
    }

    public String getConsignType() {
        return consignType;
    }

    public void setConsignType(String consignType) {
        this.consignType = consignType == null ? null : consignType.trim();
    }

	public List<TbnDtlVo> getTbnDtlVos() {
		return tbnDtlVos;
	}

	public void setTbnDtlVos(List<TbnDtlVo> tbnDtlVos) {
		this.tbnDtlVos = tbnDtlVos;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}
    
}