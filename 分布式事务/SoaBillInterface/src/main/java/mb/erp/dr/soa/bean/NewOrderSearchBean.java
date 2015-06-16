package mb.erp.dr.soa.bean;

import java.io.Serializable;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;


/**
 * 单据查询bean
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         NewOrderSearchBean
 * @since       全流通改造
 */
public class NewOrderSearchBean implements Serializable {
	/**
	 * 
	 */
	private Long unitId; // 组织id
	private String docNum; // 单据编号
	private Long prodId; // 商品id
	private NewBillType docType; // 单据类型
	private Long warehId; // 仓库id
	
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public NewBillType getDocType() {
		return docType;
	}
	public void setDocType(NewBillType docType) {
		this.docType = docType;
	}
	public Long getWarehId() {
		return warehId;
	}
	public void setWarehId(Long warehId) {
		this.warehId = warehId;
	}
}
