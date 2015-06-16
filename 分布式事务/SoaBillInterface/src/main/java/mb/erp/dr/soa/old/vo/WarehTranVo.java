package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 仓库事务表
 * 流水号获取：WAREH_TRAN_ID
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehTranVo
 * @since       全流通改造
 */
public class WarehTranVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6246629250685124359L;
	
	private String warehId ; // 仓库编码
	private Long warehTranId ; // 仓库事务编号
	private String prodId ; // 商品编码(SKU)
	private Date tranDate ; // 发生日期
	private Date tranTime ; // 发生时间
	private BillType docType ; // 单据类型
	private String docNum ; // 单据编码
	private Double tranQty ; // 发生数量
	private Double balance ; // 余额


    public String getWarehId() {
		return warehId;
	}

	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}

	public Long getWarehTranId() {
		return warehTranId;
	}

	public void setWarehTranId(Long warehTranId) {
		this.warehTranId = warehTranId;
	}

	public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public BillType getDocType() {
		return docType;
	}

	public void setDocType(BillType docType) {
		this.docType = docType;
	}

	public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum == null ? null : docNum.trim();
    }

    public Double getTranQty() {
        return tranQty;
    }

    public void setTranQty(Double tranQty) {
        this.tranQty = tranQty;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}