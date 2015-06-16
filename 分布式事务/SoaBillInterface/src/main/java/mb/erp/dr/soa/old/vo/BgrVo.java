package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

/**
 * 退货申请单实体类
 * 
 * @author 陈志杰
 * @version 1.0, 2015-03-18
 * @see BgrVo
 * @since 全流通改造
 */
public class BgrVo extends BaseBizVo {

	private static final long serialVersionUID = 4640633059421129680L;

	private String vendeeId;

	private String bgrNum;

	private Date docDate;

	private String venderId;

	private String dispWarehId;

	private String rcvWarehId;

	private String crType;

	private String oprId;

	private String buyerId;

	private String sellerId;

	private String invsgId;

	private String delivMthd;

	private String delivAddr;

	private String delivPstd;

	private Date reqdAt;

	private Date dispTime;

	private Date rcvTime;

	private Double taxRate;

	private Double crQty;

	private Double crVal;

	private Double delivQty;

	private Double delivVal;

	private Double grossQty;

	private String transPlan;

	private Double rcvQty;

	private Double rcvVal;

	private Double accQty;

	private Double accVal;

	private Date invsgTime;

	private Long boxCount;

	private Date checkTime;

	private Date checkTiemEnd;

	private String bgrCheckerId;

	private String bgrOprId;

	private Double recvQty;

	private Double productCount;

	private String dispOprId;

	private Date acptStartTime;

	private Date acptEndTime;

	private String classRemark;

	private Date planEndTime;

	private String locId;

	private String brandId;

	private String bgrKind;

	private String backGoodsType;

	private String havebox;

	private String isIncludepassed;

	private String newBgrNum;

	private String isSorted;

	private String versionFlag;

	private List<BgrDtlVo> bgrDtlVos;

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getBgrNum() {
		return bgrNum;
	}

	public void setBgrNum(String bgrNum) {
		this.bgrNum = bgrNum;
	}

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
		this.venderId = venderId == null ? null : venderId.trim();
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

	public String getCrType() {
		return crType;
	}

	public void setCrType(String crType) {
		this.crType = crType == null ? null : crType.trim();
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

	public Double getGrossQty() {
		return grossQty;
	}

	public void setGrossQty(Double grossQty) {
		this.grossQty = grossQty;
	}

	public String getTransPlan() {
		return transPlan;
	}

	public void setTransPlan(String transPlan) {
		this.transPlan = transPlan == null ? null : transPlan.trim();
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

	public Double getAccQty() {
		return accQty;
	}

	public void setAccQty(Double accQty) {
		this.accQty = accQty;
	}

	public Double getAccVal() {
		return accVal;
	}

	public void setAccVal(Double accVal) {
		this.accVal = accVal;
	}

	public Date getInvsgTime() {
		return invsgTime;
	}

	public void setInvsgTime(Date invsgTime) {
		this.invsgTime = invsgTime;
	}

	public Long getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(Long boxCount) {
		this.boxCount = boxCount;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getCheckTiemEnd() {
		return checkTiemEnd;
	}

	public void setCheckTiemEnd(Date checkTiemEnd) {
		this.checkTiemEnd = checkTiemEnd;
	}

	public String getBgrCheckerId() {
		return bgrCheckerId;
	}

	public void setBgrCheckerId(String bgrCheckerId) {
		this.bgrCheckerId = bgrCheckerId == null ? null : bgrCheckerId.trim();
	}

	public String getBgrOprId() {
		return bgrOprId;
	}

	public void setBgrOprId(String bgrOprId) {
		this.bgrOprId = bgrOprId == null ? null : bgrOprId.trim();
	}

	public Double getRecvQty() {
		return recvQty;
	}

	public void setRecvQty(Double recvQty) {
		this.recvQty = recvQty;
	}

	public Double getProductCount() {
		return productCount;
	}

	public void setProductCount(Double productCount) {
		this.productCount = productCount;
	}

	public String getDispOprId() {
		return dispOprId;
	}

	public void setDispOprId(String dispOprId) {
		this.dispOprId = dispOprId == null ? null : dispOprId.trim();
	}

	public Date getAcptStartTime() {
		return acptStartTime;
	}

	public void setAcptStartTime(Date acptStartTime) {
		this.acptStartTime = acptStartTime;
	}

	public Date getAcptEndTime() {
		return acptEndTime;
	}

	public void setAcptEndTime(Date acptEndTime) {
		this.acptEndTime = acptEndTime;
	}

	public String getClassRemark() {
		return classRemark;
	}

	public void setClassRemark(String classRemark) {
		this.classRemark = classRemark == null ? null : classRemark.trim();
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId == null ? null : locId.trim();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId == null ? null : brandId.trim();
	}

	public String getBgrKind() {
		return bgrKind;
	}

	public void setBgrKind(String bgrKind) {
		this.bgrKind = bgrKind == null ? null : bgrKind.trim();
	}

	public String getBackGoodsType() {
		return backGoodsType;
	}

	public void setBackGoodsType(String backGoodsType) {
		this.backGoodsType = backGoodsType == null ? null : backGoodsType
				.trim();
	}

	public String getHavebox() {
		return havebox;
	}

	public void setHavebox(String havebox) {
		this.havebox = havebox == null ? null : havebox.trim();
	}

	public String getIsIncludepassed() {
		return isIncludepassed;
	}

	public void setIsIncludepassed(String isIncludepassed) {
		this.isIncludepassed = isIncludepassed == null ? null : isIncludepassed
				.trim();
	}

	public String getNewBgrNum() {
		return newBgrNum;
	}

	public void setNewBgrNum(String newBgrNum) {
		this.newBgrNum = newBgrNum == null ? null : newBgrNum.trim();
	}

	public String getIsSorted() {
		return isSorted;
	}

	public void setIsSorted(String isSorted) {
		this.isSorted = isSorted == null ? null : isSorted.trim();
	}

	public String getVersionFlag() {
		return versionFlag;
	}

	public void setVersionFlag(String versionFlag) {
		this.versionFlag = versionFlag == null ? null : versionFlag.trim();
	}

	public List<BgrDtlVo> getBgrDtlVos() {
		return bgrDtlVos;
	}

	public void setBgrDtlVos(List<BgrDtlVo> bgrDtlVos) {
		this.bgrDtlVos = bgrDtlVos;
	}
}