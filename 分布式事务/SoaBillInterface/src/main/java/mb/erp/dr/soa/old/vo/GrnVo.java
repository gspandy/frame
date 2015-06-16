package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 入库单vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GrnVo
 * @since       全流通改造
 */
public class GrnVo extends BaseBizVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8339055524815800457L;
	
	private String unitId ; // 购货方编码
	private String grnNum ; // 订单编号
	private Date docDate ; // 单据日期
	private String rcptMode ; // 入库方式
	private String warehId ; // 入库仓库编码
	private String oprId ; // 操作人员编码
	private String ctrlrId ; // 控制人员编码
	private String dispUnitId ; // 发货组织编码
	private BillType srcDocType ; // 原始单据类型
	private String srcUnitId ; // 原始单据组织编码
	private String srcDocNum ; // 原始单据编码
	private Double ttlQty ; // 总数量
	private Double ttlVal ; // 总金额
	private Double taxRate ; // 税率
	private Double taxVal ; // 税额
	private Double psnVal ; // 折让金额
	private Double addtCost ; // 附加成本
	private Double cost ; // 总成本
	private Date rcptTime ; // 收货时间
	private Date paStrAt ; // 开始分储时间
	private Date paCompAt ; // 分储完成时间
	private String efficient ; // 是否有效
	private String struck ; // 是否冲单
	private String costChg ; // 是否成本结转
	private String pdaProgress ; // RF进度
	private Date efficientTime ; // 生效时间
	private Date costTime ; // 成本结转时间
	private String businessContractNum ; // 生产合同编码
	private String k3ReadFlag ; // 金蝶读取标志
	private String needSend ; // WMS是否读取
	private String shmtNbr ; // ASN编码
	private String rcvType ; // 收货方式
	private String rfRcv ; // 是否采用RF收货
	private String goodcode ; // 商品条码
	private String brandId ; // 品牌ID
	private String isCross ; // 是否交叉转运
	private String crossOrderno ; // 交叉转运单号
	private String innerOrderno ; // 内向交货单号
	private String isChecked ; // 是否需要质检
	private String dataType ; // 是否缺色断码订单
	private Date preArriveTime ; // 预计到货时间
	private Date arriveTime ; // 实际到货时间
	private String factRcvWarehId ; // 实际接收仓库编码
	private String sapFiFlag ; // SAP_FI是否读取
	private String sapCoFlag ; // SAP_CO_是否读取
	private String sapFlag ; // SAP是否读取
	private String sapInvoiceFlag ; // SAP发票校验标志
	private Date creDate ; // 创建日期
	private String checkedStatus ; // 质检状态
	private String checkedBatchno ; // 质检批号
	private Date payqtyDate ; // 交货日期
	private String newGrnnum ; // 新ERP入库单编号
	private String newFlag ; // 是否新ERP标记
	private String boxGen ; // 是否生成货箱
	private String isBs ; // 是否外来单据生成
	private String factDispId ; // 实际发货方编码
	private String isRanged ; // 是否品牌拆分
	private String dataSource ; // 数据来源
	private String approved ; // 决策方
	private String isuniform ; // 是否统一配货
	private String remark1 ; // 附加备注1
	private String gradId ; // WMS操作员ID
	private String lockedStatus ; // 是否锁定
	private String isObtain ; // 是否生成新ERP到货通知单
	private String ownerUnitId ; // 货主组织编码
	private String ownerGrnNum ; // 货主单据编码
	private String ownerDocType ; // 货主单据类型
	private String osDocCode ; // OS订单编码
    private String rcvState;
    private String delivWarehId;// 发货仓库
    private String btsSrcType;
    private String btsSrcNum;
    private Long drTbnId; //新ERP调配单ID
    private List<GrnDtlVo> grnDtlVos;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getGrnNum() {
        return grnNum;
    }

    public void setGrnNum(String grnNum) {
        this.grnNum = grnNum == null ? null : grnNum.trim();
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getRcptMode() {
        return rcptMode;
    }

    public void setRcptMode(String rcptMode) {
        this.rcptMode = rcptMode == null ? null : rcptMode.trim();
    }

    public String getWarehId() {
        return warehId;
    }

    public void setWarehId(String warehId) {
        this.warehId = warehId == null ? null : warehId.trim();
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId == null ? null : oprId.trim();
    }

    public String getCtrlrId() {
        return ctrlrId;
    }

    public void setCtrlrId(String ctrlrId) {
        this.ctrlrId = ctrlrId == null ? null : ctrlrId.trim();
    }

    public String getDispUnitId() {
        return dispUnitId;
    }

    public void setDispUnitId(String dispUnitId) {
        this.dispUnitId = dispUnitId == null ? null : dispUnitId.trim();
    }

    public BillType getSrcDocType() {
    	return srcDocType;
    }
    
    public void setSrcDocType(BillType srcDocType) {
    	this.srcDocType = srcDocType;
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

    public Date getRcptTime() {
        return rcptTime;
    }

    public void setRcptTime(Date rcptTime) {
        this.rcptTime = rcptTime;
    }

    public Date getPaStrAt() {
        return paStrAt;
    }

    public void setPaStrAt(Date paStrAt) {
        this.paStrAt = paStrAt;
    }

    public Date getPaCompAt() {
        return paCompAt;
    }

    public void setPaCompAt(Date paCompAt) {
        this.paCompAt = paCompAt;
    }

    public String getEfficient() {
        return efficient;
    }

    public void setEfficient(String efficient) {
        this.efficient = efficient == null ? null : efficient.trim();
    }

    public String getStruck() {
        return struck;
    }

    public void setStruck(String struck) {
        this.struck = struck == null ? null : struck.trim();
    }

    public String getCostChg() {
        return costChg;
    }

    public void setCostChg(String costChg) {
        this.costChg = costChg == null ? null : costChg.trim();
    }

    public String getPdaProgress() {
        return pdaProgress;
    }

    public void setPdaProgress(String pdaProgress) {
        this.pdaProgress = pdaProgress == null ? null : pdaProgress.trim();
    }

    public Date getEfficientTime() {
        return efficientTime;
    }

    public void setEfficientTime(Date efficientTime) {
        this.efficientTime = efficientTime;
    }

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }

    public String getBusinessContractNum() {
        return businessContractNum;
    }

    public void setBusinessContractNum(String businessContractNum) {
        this.businessContractNum = businessContractNum == null ? null : businessContractNum.trim();
    }

    public String getK3ReadFlag() {
        return k3ReadFlag;
    }

    public void setK3ReadFlag(String k3ReadFlag) {
        this.k3ReadFlag = k3ReadFlag == null ? null : k3ReadFlag.trim();
    }

    public String getNeedSend() {
        return needSend;
    }

    public void setNeedSend(String needSend) {
        this.needSend = needSend == null ? null : needSend.trim();
    }

    public String getShmtNbr() {
        return shmtNbr;
    }

    public void setShmtNbr(String shmtNbr) {
        this.shmtNbr = shmtNbr == null ? null : shmtNbr.trim();
    }

    public String getRcvType() {
        return rcvType;
    }

    public void setRcvType(String rcvType) {
        this.rcvType = rcvType == null ? null : rcvType.trim();
    }

    public String getRfRcv() {
        return rfRcv;
    }

    public void setRfRcv(String rfRcv) {
        this.rfRcv = rfRcv == null ? null : rfRcv.trim();
    }

    public String getGoodcode() {
        return goodcode;
    }

    public void setGoodcode(String goodcode) {
        this.goodcode = goodcode == null ? null : goodcode.trim();
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }

    public String getRcvState() {
        return rcvState;
    }

    public void setRcvState(String rcvState) {
        this.rcvState = rcvState == null ? null : rcvState.trim();
    }

    public String getDelivWarehId() {
        return delivWarehId;
    }

    public void setDelivWarehId(String delivWarehId) {
        this.delivWarehId = delivWarehId == null ? null : delivWarehId.trim();
    }

    public String getIsCross() {
        return isCross;
    }

    public void setIsCross(String isCross) {
        this.isCross = isCross == null ? null : isCross.trim();
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

    public String getFactRcvWarehId() {
        return factRcvWarehId;
    }

    public void setFactRcvWarehId(String factRcvWarehId) {
        this.factRcvWarehId = factRcvWarehId == null ? null : factRcvWarehId.trim();
    }

    public String getSapFiFlag() {
        return sapFiFlag;
    }

    public void setSapFiFlag(String sapFiFlag) {
        this.sapFiFlag = sapFiFlag == null ? null : sapFiFlag.trim();
    }

    public String getSapCoFlag() {
        return sapCoFlag;
    }

    public void setSapCoFlag(String sapCoFlag) {
        this.sapCoFlag = sapCoFlag == null ? null : sapCoFlag.trim();
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

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
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

    public Date getPayqtyDate() {
        return payqtyDate;
    }

    public void setPayqtyDate(Date payqtyDate) {
        this.payqtyDate = payqtyDate;
    }

    public String getNewGrnnum() {
        return newGrnnum;
    }

    public void setNewGrnnum(String newGrnnum) {
        this.newGrnnum = newGrnnum == null ? null : newGrnnum.trim();
    }

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag == null ? null : newFlag.trim();
    }

    public String getBoxGen() {
        return boxGen;
    }

    public void setBoxGen(String boxGen) {
        this.boxGen = boxGen == null ? null : boxGen.trim();
    }

    public String getIsBs() {
        return isBs;
    }

    public void setIsBs(String isBs) {
        this.isBs = isBs == null ? null : isBs.trim();
    }

    public String getBtsSrcType() {
        return btsSrcType;
    }

    public void setBtsSrcType(String btsSrcType) {
        this.btsSrcType = btsSrcType == null ? null : btsSrcType.trim();
    }

    public String getBtsSrcNum() {
        return btsSrcNum;
    }

    public void setBtsSrcNum(String btsSrcNum) {
        this.btsSrcNum = btsSrcNum == null ? null : btsSrcNum.trim();
    }

    public String getFactDispId() {
        return factDispId;
    }

    public void setFactDispId(String factDispId) {
        this.factDispId = factDispId == null ? null : factDispId.trim();
    }

    public String getIsRanged() {
        return isRanged;
    }

    public void setIsRanged(String isRanged) {
        this.isRanged = isRanged == null ? null : isRanged.trim();
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

    public String getIsuniform() {
        return isuniform;
    }

    public void setIsuniform(String isuniform) {
        this.isuniform = isuniform == null ? null : isuniform.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getGradId() {
        return gradId;
    }

    public void setGradId(String gradId) {
        this.gradId = gradId == null ? null : gradId.trim();
    }

    public String getLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(String lockedStatus) {
        this.lockedStatus = lockedStatus == null ? null : lockedStatus.trim();
    }

    public String getIsObtain() {
        return isObtain;
    }

    public void setIsObtain(String isObtain) {
        this.isObtain = isObtain == null ? null : isObtain.trim();
    }

    public String getOwnerUnitId() {
        return ownerUnitId;
    }

    public void setOwnerUnitId(String ownerUnitId) {
        this.ownerUnitId = ownerUnitId == null ? null : ownerUnitId.trim();
    }

    public String getOwnerGrnNum() {
        return ownerGrnNum;
    }

    public void setOwnerGrnNum(String ownerGrnNum) {
        this.ownerGrnNum = ownerGrnNum == null ? null : ownerGrnNum.trim();
    }

    public String getOwnerDocType() {
        return ownerDocType;
    }

    public void setOwnerDocType(String ownerDocType) {
        this.ownerDocType = ownerDocType == null ? null : ownerDocType.trim();
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }

	public List<GrnDtlVo> getGrnDtlVos() {
		return grnDtlVos;
	}

	public void setGrnDtlVos(List<GrnDtlVo> grnDtlVos) {
		this.grnDtlVos = grnDtlVos;
	}

	public Long getDrTbnId() {
		return drTbnId;
	}

	public void setDrTbnId(Long drTbnId) {
		this.drTbnId = drTbnId;
	}
    
}