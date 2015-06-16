package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 计划配货单实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         AdnVo
 * @since       全流通改造
 */
public class AdnVo extends BaseBizVo {
	
	private static final long serialVersionUID = -4475413672876377787L;

	private String vendeeId ; // 购货方编码

	private String idtNum ; // 订单编号

	private Date docDate ; // 单据日期

	private String venderId ; // 供货方编码

	private String warehId ; // 发货仓库编码

	private String adnNum ; // 计划配货单编码

	private String dispUnitId ; // 配发组织编码

	private String dispUnitType ; // 配发组织类型编码

	private Date reqdAt ; // 要求发货日期

	private String ctrlrId ; // 操作人编码

	private Date dispTime ; // 发货时间

	private Date rcvTime ; // 收货时间

	private Double admQty ; // 配货数量

	private Double admVal ; // 配货金额

	private Double psnVal ; // 折让金额

	private Double delivQty ; // 发货数量

	private Double delivVal ; // 发货金额

	private Double rcvQty ; // 收货数量

	private Double rcvVal ; // 收货金额

	private Date admAt ; // 配货时间

	private Double productCount ; // 品项数

	private String isGentbn ; // 是否生成调配单

	private String brandId ; // 品牌ID

	private String allocationType ; // 配货类型

	private String factWarehId ; // 实际发货仓库

	private String factVendeeId ; // 实际购货方编码

	private String factIdtNum ; // 实际订单编码

	private String srcUnitId ; // 原始组织编码

	private String srcDocNum ; // 原始单据编码

	private BillType srcDocType ; // 原始单据类型

	private String tranRcvWarehId ; // 实际接收仓库编码
	
    private String osDocCode; //OS订单号

    private List<AdnDtlVo> adnDtlVos;

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
        this.vendeeId = vendeeId == null ? null : vendeeId.trim();
    }

    public String getIdtNum() {
        return idtNum;
    }

    public void setIdtNum(String idtNum) {
        this.idtNum = idtNum == null ? null : idtNum.trim();
    }

    public String getWarehId() {
        return warehId;
    }

    public void setWarehId(String warehId) {
        this.warehId = warehId == null ? null : warehId.trim();
    }

    public String getDispUnitId() {
        return dispUnitId;
    }

    public void setDispUnitId(String dispUnitId) {
        this.dispUnitId = dispUnitId == null ? null : dispUnitId.trim();
    }

    public String getDispUnitType() {
        return dispUnitType;
    }

    public void setDispUnitType(String dispUnitType) {
        this.dispUnitType = dispUnitType == null ? null : dispUnitType.trim();
    }

    public Date getReqdAt() {
        return reqdAt;
    }

    public void setReqdAt(Date reqdAt) {
        this.reqdAt = reqdAt;
    }

    public String getCtrlrId() {
        return ctrlrId;
    }

    public void setCtrlrId(String ctrlrId) {
        this.ctrlrId = ctrlrId == null ? null : ctrlrId.trim();
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

    public Double getPsnVal() {
        return psnVal;
    }

    public void setPsnVal(Double psnVal) {
        this.psnVal = psnVal;
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

    public Date getAdmAt() {
        return admAt;
    }

    public void setAdmAt(Date admAt) {
        this.admAt = admAt;
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public String getIsGentbn() {
        return isGentbn;
    }

    public void setIsGentbn(String isGentbn) {
        this.isGentbn = isGentbn == null ? null : isGentbn.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(String allocationType) {
        this.allocationType = allocationType == null ? null : allocationType.trim();
    }

    public String getFactWarehId() {
        return factWarehId;
    }

    public void setFactWarehId(String factWarehId) {
        this.factWarehId = factWarehId == null ? null : factWarehId.trim();
    }

    public String getFactVendeeId() {
        return factVendeeId;
    }

    public void setFactVendeeId(String factVendeeId) {
        this.factVendeeId = factVendeeId == null ? null : factVendeeId.trim();
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

    
    public BillType getSrcDocType() {
		return srcDocType;
	}

	public void setSrcDocType(BillType srcDocType) {
		this.srcDocType = srcDocType;
	}

	public String getTranRcvWarehId() {
        return tranRcvWarehId;
    }

    public void setTranRcvWarehId(String tranRcvWarehId) {
        this.tranRcvWarehId = tranRcvWarehId == null ? null : tranRcvWarehId.trim();
    }

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getAdnNum() {
		return adnNum;
	}

	public void setAdnNum(String adnNum) {
		this.adnNum = adnNum;
	}

	public List<AdnDtlVo> getAdnDtlVos() {
		return adnDtlVos;
	}

	public void setAdnDtlVos(List<AdnDtlVo> adnDtlVos) {
		this.adnDtlVos = adnDtlVos;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}
}