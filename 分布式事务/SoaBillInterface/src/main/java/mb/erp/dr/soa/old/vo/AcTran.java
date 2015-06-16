package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 往来事务表vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         AcTran
 * @since       全流通改造
 */
public class AcTran  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1189103449163742477L;
	
	private String unitId ; // 组织编码
	private Double tranNum ; // 事务编码
	private Date tranDate ; // 交易日期
	private String tranType ; // 交易类型
	private BillType docType ; // 单据类型
	private String docNum ; // 单据编码
	private Date tranTime ; // 发生时间

    public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Double getTranNum() {
		return tranNum;
	}

	public void setTranNum(Double tranNum) {
		this.tranNum = tranNum;
	}

	public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType == null ? null : tranType.trim();
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

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }
}