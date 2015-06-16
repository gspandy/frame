package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

/**
 * 退货单实体类
 * 
 * @author     陈志杰
 * @version    1.0, 2015-03-18
 * @see         ScnVo
 * @since       全流通改造
 */
public class ScnVo extends BaseBizVo {
	private static final long serialVersionUID = -8032888151746016635L;

	private String vendeeId;

    private String scnNum;
	
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

    private String scnCheckerId;

    private String scnOprId;

    private Double recvQty;

    private Double productCount;

    private String dispOprId;

    private Date acptStartTime;

    private Date acptEndTime;

    private String classRemark;

    private Date planEndTime;

    private String locId;

    private String brandId;

    private String bgrNum;
    
    private List<ScnDtlVo> scnDtlVos;

    public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getScnNum() {
		return scnNum;
	}

	public void setScnNum(String scnNum) {
		this.scnNum = scnNum;
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

    public String getScnCheckerId() {
        return scnCheckerId;
    }

    public void setScnCheckerId(String scnCheckerId) {
        this.scnCheckerId = scnCheckerId == null ? null : scnCheckerId.trim();
    }

    public String getScnOprId() {
        return scnOprId;
    }

    public void setScnOprId(String scnOprId) {
        this.scnOprId = scnOprId == null ? null : scnOprId.trim();
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

    public String getBgrNum() {
        return bgrNum;
    }

    public void setBgrNum(String bgrNum) {
        this.bgrNum = bgrNum == null ? null : bgrNum.trim();
    }

	public List<ScnDtlVo> getScnDtlVos() {
		return scnDtlVos;
	}

	public void setScnDtlVos(List<ScnDtlVo> scnDtlVos) {
		this.scnDtlVos = scnDtlVos;
	}
}