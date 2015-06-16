package mb.erp.dr.soa.bean;

import java.io.Serializable;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;


/**
 * 单据查询bean
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         OrderSearchBean
 * @since       全流通改造
 */
public class OrderSearchBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 398433406662698935L;
	private String unitId; // 组织编码
	private String docNum; // 单据编号
	private String prodId; // 商品id
	private BillType docType; // 单据类型
	private String warehId; // 仓库id
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public BillType getDocType() {
		return docType;
	}
	public void setDocType(BillType docType) {
		this.docType = docType;
	}
	public String getWarehId() {
		return warehId;
	}
	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}
	
}
