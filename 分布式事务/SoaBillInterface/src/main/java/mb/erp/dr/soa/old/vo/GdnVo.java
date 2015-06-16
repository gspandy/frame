package mb.erp.dr.soa.old.vo;

import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 出库单vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GdnVo
 * @since       全流通改造
 */
public class GdnVo extends BaseBizVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4478346050296553791L;
	
	private String unitId ; // 组织编码 发货方
	private String gdnNum ; // 出库单编号
	private Date docDate ; // 单据日期
	private String delivMode ; // 出库方式
	private String warehId ; // 发货仓库编码
	private String oprId ; // 操作员编码
	private String rcvUnitId ; // 接收组织编码
	private String ctrlrId ; // 确认人编码
	private String contactId ; // 联系人编码
	private BillType srcDocType ; // 原始单据类型
	private String srcUnitId ; // 原始单据组织编码
	private String srcDocNum ; // 原始单据编码
	private Double ttlQty ; // 总数量
	private Double ttlVal ; // 总金额
	private Double taxRate ; // 税率
	private Double taxVal ; // 税额
	private Double psnVal ; // 折让金额
	private Double addtCost ; // 附加成本
	private Double cost ; // 成本
	private Date reqdAt ; // 要求发货时间
	private Date pickStrAt ; // 分拣开始时间
	private Date pickCompAt ; // 分拣结束时间
	private Date dispTime ; // 发货时间
	private String delivMthd ; // 发货方式
	private String delivAddr ; // 发货地址
	private String delivPstd ; // 邮编
	private String csbNum ; // 托运单号
	private String efficient ; // 是否有效
	private String struck ; // 是否已冲单
	private String costChg ; // 是否参与成本核算
	private String scanTblNum ; // 扫描台号
	private String gradId ; // 扫描员编码
	private Double productCount ; // 品项数
	private String sregUnitId ; // 注册地编码
	private String pdaProgress ; // RF进度
	private Date efficientTime ; // 生效时间
	private Date costTime ; // 成本生效时间
	private String businessContractNum ; // 生产合同编码
	private String preProgress ; // 预分拣进度
	private String k3ReadFlag ; // 金蝶读取标志
	private String locId ; // 货位编码
	private String needSend ; // 是否传输WMS
	private String shmtNbr ; // PIX号
	private String wifNum ; // 传输WMS唯一编码
	private String tspComId ; // 承运商编码
	private String goodcode ; // 商品款式编码
	private String brandId ; // 品牌编码
	private String rcvState ; // 出库原因
	private String importWmosDmode ; // WMS发货方式
	private String rcvWarehId ; // 接收仓库
	private String dataType ; // 是否缺色断码订单
	private String outerOrderno ; // 外向交货单号
	private String sapFlag ; // 是否传输SAP标志
	private String sapInvoiceFlag ; // SAP发票标志
	private Date creDate ; // 创建日期
	private String isCross ; // 是否交叉转运
	private String innerOrderno ; // 内向交货单
	private Double boxCount ; // 箱数
	private Double productVol ; // 商品体积
	private String newGdnnum ; // 新ERP编码
	private String newFlag ; // 是否传输新ERP
	private String boxGen ; // 是否存在箱
	private String isBs ; // 是否新ERP产生
	private String isuniform ; // 是否统一配货产生
	private String transState ; // 传输状态
	private String dataSource; //数据来源
	private String approved; //决策方
	private String osDocCode;
    private Long drTbnId; //新ERP调配单ID
    private List<GdnDtlVo> gdnDtlVos;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getGdnNum() {
        return gdnNum;
    }

    public void setGdnNum(String gdnNum) {
        this.gdnNum = gdnNum == null ? null : gdnNum.trim();
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

    public String getWarehId() {
        return warehId;
    }

    public void setWarehId(String warehId) {
        this.warehId = warehId == null ? null : warehId.trim();
    }

    public String getRcvUnitId() {
        return rcvUnitId;
    }

    public void setRcvUnitId(String rcvUnitId) {
        this.rcvUnitId = rcvUnitId == null ? null : rcvUnitId.trim();
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

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId == null ? null : contactId.trim();
    }

//    public String getSrcDocType() {
//        return srcDocType;
//    }
//
//    public void setSrcDocType(String srcDocType) {
//        this.srcDocType = srcDocType == null ? null : srcDocType.trim();
//    }

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

    public Date getReqdAt() {
        return reqdAt;
    }

    public void setReqdAt(Date reqdAt) {
        this.reqdAt = reqdAt;
    }

    public Date getPickStrAt() {
        return pickStrAt;
    }

    public void setPickStrAt(Date pickStrAt) {
        this.pickStrAt = pickStrAt;
    }

    public Date getPickCompAt() {
        return pickCompAt;
    }

    public void setPickCompAt(Date pickCompAt) {
        this.pickCompAt = pickCompAt;
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

    public String getScanTblNum() {
        return scanTblNum;
    }

    public void setScanTblNum(String scanTblNum) {
        this.scanTblNum = scanTblNum == null ? null : scanTblNum.trim();
    }

    public String getGradId() {
        return gradId;
    }

    public void setGradId(String gradId) {
        this.gradId = gradId == null ? null : gradId.trim();
    }

    public Double getProductCount() {
        return productCount;
    }

    public void setProductCount(Double productCount) {
        this.productCount = productCount;
    }

    public String getSregUnitId() {
        return sregUnitId;
    }

    public void setSregUnitId(String sregUnitId) {
        this.sregUnitId = sregUnitId == null ? null : sregUnitId.trim();
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

    public String getPreProgress() {
        return preProgress;
    }

    public void setPreProgress(String preProgress) {
        this.preProgress = preProgress == null ? null : preProgress.trim();
    }

    public String getK3ReadFlag() {
        return k3ReadFlag;
    }

    public void setK3ReadFlag(String k3ReadFlag) {
        this.k3ReadFlag = k3ReadFlag == null ? null : k3ReadFlag.trim();
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId == null ? null : locId.trim();
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

    public String getWifNum() {
        return wifNum;
    }

    public void setWifNum(String wifNum) {
        this.wifNum = wifNum == null ? null : wifNum.trim();
    }

    public String getTspComId() {
        return tspComId;
    }

    public void setTspComId(String tspComId) {
        this.tspComId = tspComId == null ? null : tspComId.trim();
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

    public String getImportWmosDmode() {
        return importWmosDmode;
    }

    public void setImportWmosDmode(String importWmosDmode) {
        this.importWmosDmode = importWmosDmode == null ? null : importWmosDmode.trim();
    }

    public String getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(String rcvWarehId) {
        this.rcvWarehId = rcvWarehId == null ? null : rcvWarehId.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getOuterOrderno() {
        return outerOrderno;
    }

    public void setOuterOrderno(String outerOrderno) {
        this.outerOrderno = outerOrderno == null ? null : outerOrderno.trim();
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

    public String getIsCross() {
        return isCross;
    }

    public void setIsCross(String isCross) {
        this.isCross = isCross == null ? null : isCross.trim();
    }

    public String getInnerOrderno() {
        return innerOrderno;
    }

    public void setInnerOrderno(String innerOrderno) {
        this.innerOrderno = innerOrderno == null ? null : innerOrderno.trim();
    }

    public Double getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(Double boxCount) {
        this.boxCount = boxCount;
    }

    public Double getProductVol() {
        return productVol;
    }

    public void setProductVol(Double productVol) {
        this.productVol = productVol;
    }

    public String getNewGdnnum() {
        return newGdnnum;
    }

    public void setNewGdnnum(String newGdnnum) {
        this.newGdnnum = newGdnnum == null ? null : newGdnnum.trim();
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

    public String getIsuniform() {
        return isuniform;
    }

    public void setIsuniform(String isuniform) {
        this.isuniform = isuniform == null ? null : isuniform.trim();
    }

    public String getTransState() {
        return transState;
    }

    public void setTransState(String transState) {
        this.transState = transState == null ? null : transState.trim();
    }

	public List<GdnDtlVo> getGdnDtlVos() {
		return gdnDtlVos;
	}

	public void setGdnDtlVos(List<GdnDtlVo> gdnDtlVos) {
		this.gdnDtlVos = gdnDtlVos;
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

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}

	public Long getDrTbnId() {
		return drTbnId;
	}

	public void setDrTbnId(Long drTbnId) {
		this.drTbnId = drTbnId;
	}
}