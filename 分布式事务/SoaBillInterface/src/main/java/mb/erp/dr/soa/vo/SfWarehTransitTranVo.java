package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 在途库存事务表vo(本表为本次新加表)
 * PK : ID   
 * @author     余从玉
 * @version    1.0, 2015-3-23
 * @since       全流通改造
 */
public class SfWarehTransitTranVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4885641818577028813L;

	private Long id;

    private Long warehId;

    private Long prodId;

    private Date tranDate;

    private Date tranTime;

    private String docType;

    private String docCode;

    private Double tranQty;

    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehId() {
        return warehId;
    }

    public void setWarehId(Long warehId) {
        this.warehId = warehId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
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

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode == null ? null : docCode.trim();
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