package mb.erp.dr.soa.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;

/**
 * 库存服务 -- 方法参数bean
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehBean
 * @since       全流通改造
 */
public class WarehBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3845273514533418197L;
	private String unitId ; // 组织编码
	private String  warehId; // 仓库编码
	private String locId; // 货位编码
	private String venderId ; // 供货方编码
	private String gdnNum; // 出库单编码
	private String grnNum; // 入库单编码
	private String tbnNum; // 调配单编码
	private String adnNum ; // 计划配货单编码
	private List<ProdBean> prodList; // 商品数量列表
	private BillType docType; // 单据类型
	private String docNum; // 单据编码
	private Date tranDate;  // 发生日期
	private Boolean isIncrease; // 是否增加库存（不需设值，程序里面统一处理）
	private String tbnUnitId; //调配单供货方编码
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getWarehId() {
		return warehId;
	}
	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}
	public String getVenderId() {
		return venderId;
	}
	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}
	public String getGdnNum() {
		return gdnNum;
	}
	public void setGdnNum(String gdnNum) {
		this.gdnNum = gdnNum;
	}
	public String getGrnNum() {
		return grnNum;
	}
	public void setGrnNum(String grnNum) {
		this.grnNum = grnNum;
	}
	public String getTbnNum() {
		return tbnNum;
	}
	public void setTbnNum(String tbnNum) {
		this.tbnNum = tbnNum;
	}
	public String getAdnNum() {
		return adnNum;
	}
	public void setAdnNum(String adnNum) {
		this.adnNum = adnNum;
	}
	public String getLocId() {
		return locId;
	}
	public void setLocId(String locId) {
		this.locId = locId;
	}
	public List<ProdBean> getProdList() {
		return prodList;
	}
	public void setProdList(List<ProdBean> prodList) {
		this.prodList = prodList;
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
		this.docNum = docNum;
	}
	public Date getTranDate() {
		return tranDate;
	}
	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	public Boolean getIsIncrease() {
		return isIncrease;
	}
	public String getTbnUnitId() {
		return tbnUnitId;
	}
	public void setTbnUnitId(String tbnUnitId) {
		this.tbnUnitId = tbnUnitId;
	}
	/**
	 * 是否增加库存.
	 * 不需设值，程序里面统一处理
	 * @param isIncrease
	 */
	public void setIsIncrease(Boolean isIncrease) {
		this.isIncrease = isIncrease;
	}
	
}
