package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 已分配库存事务表vo(本表为本次新加表)
 * PK : WAREH_ID+PROD_ID   流水号：WAREH_COMMIT_TRANID
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehCommitedTranVo
 * @since       全流通改造
 */
public class WarehCommitedTranVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 470022232783493415L;
	
	private String warehId ; // 仓库编码
	private Long commitedTranId ; // 事务编号
	private String prodId ; // 商品编码
	private Date tranDate ; // 发生日期
	private Date tranTime ; // 发生时间
	private Double tranQty ; // 发生数量
	private BillType docType ; // 单据类型
	private String docNum ; // 单据编码 
	private Double balance ; // 库存余额

    public String getWarehId() {
		return warehId;
	}

	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}

	public Long getCommitedTranId() {
		return commitedTranId;
	}

	public void setCommitedTranId(Long commitedTranId) {
		this.commitedTranId = commitedTranId;
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