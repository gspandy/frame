package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 在购库存事务表vo(本表为本次新加表)
 * PK : WAREH_ID+PROD_ID   流水号：WAREH_ORDER_TRANID
 * @author     陈志杰
 * @version    1.0, 2015-01-22
 * @see         WarehOrderTranVo
 * @since       全流通改造
 */
public class WarehOrderTranVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 359583410157777074L;
	
	private String warehId ; // 仓库编码
	private Long orderTranId ; // 事务编号
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

	public Long getOrderTranId() {
		return orderTranId;
	}

	public void setOrderTranId(Long orderTranId) {
		this.orderTranId = orderTranId;
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