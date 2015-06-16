package mb.erp.dr.soa.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新ERP调配单vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-11-21
 * @see         DrTbnVo
 * @since       全流通改造
 */
public class DrTbnVo extends NewBaseBizVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4419667977063600445L;
	
	private Long id ; // 内码
	private Long vendeeId ; // 购货方ID
	private String tbnNum ; // 调配单编号
	private Date docDate ; // 单据日期
	private Long venderId ; // 供货方ID
	private Long dispWarehId ; // 发货仓库ID
	private Long rcvWarehId ; // 接收仓库ID
	private Long oprId ; // 操作员编码
//	private String andNum ; // 计划配货单号
	private Long buyerId ; // 购买人ID
	private Long sellerId ; // 售卖人ID
//	private String invsgId ; // 审批人编码
//	private Date invsgTime ; // 审批时间
//	private String rcvWarehId ; // 接收仓库编码
	private String delivMthd ; // 送货方式
	private String delivAddr ; // 送货地址
	private String delivPstd ; // 邮编
	private Date reqdAt ; // 要求送货时间
	private Double taxRate ; // 税率
	private Double ttlQty ; // 调配数量
	private Double ttlVal ; // 调配金额
	private Double ttlDelivQty ; // 发货数量
	private Double ttlDelivVal ; // 发货金额
	private Double ttlRcvQty ; // 接收数量
	private Double ttlRcvVal ; // 接收金额
	private Integer docState ; // 单据状态
	private String tbnType ; // 调配类型
	private Long brandId ; // 品牌组ID
	private String createUser ; // 创建人员编码
	private Date createDate ; // 创建时间
	private String lastModifiedUser ; // 最后修改人编码
	private Date lastModifiedDate ; // 最后修改时间
	private String oldTbnNum ; // 老ERP调配单编码
	private String dataSource ; // 数据来源
	private String approved ; // 决策方
	private String reasonCode ; // 调配原因
	private String messages ; // 消息反馈
//	private String progress ; // 进度
	private String dgnCode ; // 关联交货单编码
	private Double allocState ; // 配货状态
	private String distStatus ; // 分配状态
	private String nsState ; // 统一配货进度
	private String rcvAddr ; // 收货地址
	private String rcvUser ; // 收货人姓名
	private String rcvPhoneNo ; // 收货人联系电话
	private String orgDocType ; // 原始单据类型
	private String isOos ; // 是否O2O
	private String consignType ; // 是否代销
	private String osDocCode ; // B2C订单号

    private Double smAdnId;
    private Date dispTime;
    private Date rcvTime;
    
    private List<DrTbnDtlVo> dtlVos = new ArrayList<DrTbnDtlVo>();
    private List<DrTbnAllocDtlVo> allocDtlVos = new ArrayList<DrTbnAllocDtlVo>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getTbnNum() {
        return tbnNum;
    }

    public void setTbnNum(String tbnNum) {
        this.tbnNum = tbnNum == null ? null : tbnNum.trim();
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Long getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(Long vendeeId) {
        this.vendeeId = vendeeId;
    }

    public Long getDispWarehId() {
        return dispWarehId;
    }

    public void setDispWarehId(Long dispWarehId) {
        this.dispWarehId = dispWarehId;
    }

    public Long getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(Long rcvWarehId) {
        this.rcvWarehId = rcvWarehId;
    }

    public Double getSmAdnId() {
        return smAdnId;
    }

    public void setSmAdnId(Double smAdnId) {
        this.smAdnId = smAdnId;
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprId(Long oprId) {
        this.oprId = oprId;
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

    public Double getTtlDelivQty() {
        return ttlDelivQty;
    }

    public void setTtlDelivQty(Double ttlDelivQty) {
        this.ttlDelivQty = ttlDelivQty;
    }

    public Double getTtlDelivVal() {
        return ttlDelivVal;
    }

    public void setTtlDelivVal(Double ttlDelivVal) {
        this.ttlDelivVal = ttlDelivVal;
    }

    public Double getTtlRcvQty() {
        return ttlRcvQty;
    }

    public void setTtlRcvQty(Double ttlRcvQty) {
        this.ttlRcvQty = ttlRcvQty;
    }

    public Double getTtlRcvVal() {
        return ttlRcvVal;
    }

    public void setTtlRcvVal(Double ttlRcvVal) {
        this.ttlRcvVal = ttlRcvVal;
    }

    public Integer getDocState() {
        return docState;
    }

    public void setDocState(Integer docState) {
        this.docState = docState;
    }

    public String getTbnType() {
        return tbnType;
    }

    public void setTbnType(String tbnType) {
        this.tbnType = tbnType == null ? null : tbnType.trim();
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public String getOldTbnNum() {
        return oldTbnNum;
    }

    public void setOldTbnNum(String oldTbnNum) {
        this.oldTbnNum = oldTbnNum == null ? null : oldTbnNum.trim();
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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode == null ? null : reasonCode.trim();
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages == null ? null : messages.trim();
    }

    public String getDgnCode() {
        return dgnCode;
    }

    public void setDgnCode(String dgnCode) {
        this.dgnCode = dgnCode == null ? null : dgnCode.trim();
    }

    public Double getAllocState() {
        return allocState;
    }

    public void setAllocState(Double allocState) {
        this.allocState = allocState;
    }

    public String getDistStatus() {
        return distStatus;
    }

    public void setDistStatus(String distStatus) {
        this.distStatus = distStatus == null ? null : distStatus.trim();
    }

    public String getNsState() {
        return nsState;
    }

    public void setNsState(String nsState) {
        this.nsState = nsState == null ? null : nsState.trim();
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

    public String getOrgDocType() {
        return orgDocType;
    }

    public void setOrgDocType(String orgDocType) {
        this.orgDocType = orgDocType;
    }

    public String getIsOos() {
        return isOos;
    }

    public void setIsOos(String isOos) {
        this.isOos = isOos == null ? null : isOos.trim();
    }

    public String getConsignType() {
        return consignType;
    }

    public void setConsignType(String consignType) {
        this.consignType = consignType == null ? null : consignType.trim();
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }
	public List<DrTbnDtlVo> getDtlVos() {
		return dtlVos;
	}
	public void setDtlVos(List<DrTbnDtlVo> dtlVos) {
		this.dtlVos = dtlVos;
	}
	public List<DrTbnAllocDtlVo> getAllocDtlVos() {
		return allocDtlVos;
	}
	public void setAllocDtlVos(List<DrTbnAllocDtlVo> allocDtlVos) {
		this.allocDtlVos = allocDtlVos;
	}
}