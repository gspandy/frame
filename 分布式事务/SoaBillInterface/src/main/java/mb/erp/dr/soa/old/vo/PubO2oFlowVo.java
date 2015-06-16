package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;

/**
 * 队列信息处理vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-11-14
 * @see         PubO2oFlowVo
 * @since       全流通改造
 */
public class PubO2oFlowVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3391069767697584710L;

	private Long o2oSeqid;	//	流水号
    private String bizType;	//	业务类型
    private String dataType;	//	单据类型
    private String unitId;	//	组织编码
    private String dataNo;	//	单据编号
    private Long qty;	//	数量
    private Double amount;	//	金额
    private String innerNo;	//	关联单号
    private String batchNo;	//	批次号
    private Date createDate;	//	创建时间
    private String remark;	//	备注
    private String shopid;	//	订货门店编码
    private String warehid;	//	发货仓库编码
    private String vendeeid;	//	购货方编码
    private AllocType bussinessMode; // 配货模式
    private POF_DocStatus docStatus; // 单据状态
    private Integer dataFlowSeqid; // 流程编号，如果一个流程包含多个子流程，那么每个子流程都各有一个编号
    private String dataTypeNo; // 单据类型编号

    public Long getO2oSeqid() {
        return o2oSeqid;
    }

    public void setO2oSeqid(Long o2oSeqid) {
        this.o2oSeqid = o2oSeqid;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getDataNo() {
        return dataNo;
    }

    public void setDataNo(String dataNo) {
        this.dataNo = dataNo == null ? null : dataNo.trim();
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInnerNo() {
        return innerNo;
    }

    public void setInnerNo(String innerNo) {
        this.innerNo = innerNo == null ? null : innerNo.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }

    public String getWarehid() {
        return warehid;
    }

    public void setWarehid(String warehid) {
        this.warehid = warehid == null ? null : warehid.trim();
    }

    public String getVendeeid() {
        return vendeeid;
    }

    public void setVendeeid(String vendeeid) {
        this.vendeeid = vendeeid == null ? null : vendeeid.trim();
    }

	public AllocType getBussinessMode() {
		return bussinessMode;
	}

	public void setBussinessMode(AllocType bussinessMode) {
		this.bussinessMode = bussinessMode;
	}

	public POF_DocStatus getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(POF_DocStatus docStatus) {
		this.docStatus = docStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDataTypeNo() {
		return dataTypeNo;
	}

	public void setDataTypeNo(String dataTypeNo) {
		this.dataTypeNo = dataTypeNo;
	}

	public Integer getDataFlowSeqid() {
		return dataFlowSeqid;
	}

	public void setDataFlowSeqid(Integer dataFlowSeqid) {
		this.dataFlowSeqid = dataFlowSeqid;
	}
    
}