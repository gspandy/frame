package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;

public class NewBaseBizVo implements Serializable{
	private static final long serialVersionUID = 4067629358071549080L;
	
	private String progress ; // 进度
	private String remark; // 备注
	private String currency; // 币种
	private String controlStatus; // 规则编码，调用规则前，对其赋值
	
	// 以下几个属性用做判断
	private String vendeeCode; // 收货方code
    private String venderCode; // 发货方code
    private String rcvWarehCode; // 收货仓库code
    private String dispWarehCode; // 发货仓库code
    private String factSenWarehCode; //实际发货仓库code
    private String factRcvWarehCode; //实际收货仓库code
    private String brandCode; // 品牌code
    private String shopCode; //门店code
    private String lowShopCode; //下级门店code
    private String srcUnitCode; //上级组织code
    private String oprCode ; // 操作员编码
    
    private AllocType allocType; // 配货模式 (队列使用)
    private NewBillType newBillType; //单据类型
    private String lastFactDispWarehCode; // 最终发货仓库（调配审核和配货单确认规则使用）
    private List<String> rulesGroup = new ArrayList<String>();//规则调用分支判断用
    private String sfDgnCode; //交货单code
    private String sfDgnCodeUPZ; //上层转配交货单code
    private String lastFactDispWarehId; // 最终发货仓库（出库规则使用）、
    private String lastFactRcvWarehId; // 最终收货仓库（判断生成现货单时是否根据公式算价格）
    
    private List<Integer> docStateList = new ArrayList<Integer>();
    private Map<String, Object> extraParams = new HashMap<String, Object>(); // 队列参数
    
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getControlStatus() {
		return controlStatus;
	}
	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}
	
	public String getVendeeCode() {
		return vendeeCode;
	}
	public void setVendeeCode(String vendeeCode) {
		this.vendeeCode = vendeeCode;
	}
	public String getVenderCode() {
		return venderCode;
	}
	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}
	public String getRcvWarehCode() {
		return rcvWarehCode;
	}
	public void setRcvWarehCode(String rcvWarehCode) {
		this.rcvWarehCode = rcvWarehCode;
	}
	public String getDispWarehCode() {
		return dispWarehCode;
	}
	public void setDispWarehCode(String dispWarehCode) {
		this.dispWarehCode = dispWarehCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public AllocType getAllocType() {
		return allocType;
	}
	public void setAllocType(AllocType allocType) {
		this.allocType = allocType;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getLowShopCode() {
		return lowShopCode;
	}
	public void setLowShopCode(String lowShopCode) {
		this.lowShopCode = lowShopCode;
	}
	public String getFactSenWarehCode() {
		return factSenWarehCode;
	}
	public void setFactSenWarehCode(String factSenWarehCode) {
		this.factSenWarehCode = factSenWarehCode;
	}
	public NewBillType getNewBillType() {
		return newBillType;
	}
	public void setNewBillType(NewBillType newBillType) {
		this.newBillType = newBillType;
	}
	public String getFactRcvWarehCode() {
		return factRcvWarehCode;
	}
	public void setFactRcvWarehCode(String factRcvWarehCode) {
		this.factRcvWarehCode = factRcvWarehCode;
	}
	public String getSrcUnitCode() {
		return srcUnitCode;
	}
	public void setSrcUnitCode(String srcUnitCode) {
		this.srcUnitCode = srcUnitCode;
	}
	public String getOprCode() {
		return oprCode;
	}
	public void setOprCode(String oprCode) {
		this.oprCode = oprCode;
	}
	public List<String> getRulesGroup() {
		return rulesGroup;
	}
	public void setRulesGroup(List<String> rulesGroup) {
		this.rulesGroup = rulesGroup;
	}
	public String getLastFactDispWarehCode() {
		return lastFactDispWarehCode;
	}
	public void setLastFactDispWarehCode(String lastFactDispWarehCode) {
		this.lastFactDispWarehCode = lastFactDispWarehCode;
	}
	public String getSfDgnCode() {
		return sfDgnCode;
	}
	public void setSfDgnCode(String sfDgnCode) {
		this.sfDgnCode = sfDgnCode;
	}
	public String getSfDgnCodeUPZ() {
		return sfDgnCodeUPZ;
	}
	public void setSfDgnCodeUPZ(String sfDgnCodeUPZ) {
		this.sfDgnCodeUPZ = sfDgnCodeUPZ;
	}
	public String getLastFactDispWarehId() {
		return lastFactDispWarehId;
	}
	public void setLastFactDispWarehId(String lastFactDispWarehId) {
		this.lastFactDispWarehId = lastFactDispWarehId;
	}
	public String getLastFactRcvWarehId() {
		return lastFactRcvWarehId;
	}
	public void setLastFactRcvWarehId(String lastFactRcvWarehId) {
		this.lastFactRcvWarehId = lastFactRcvWarehId;
	}
	public Map<String, Object> getExtraParams() {
		return extraParams;
	}
	public void setExtraParams(Map<String, Object> extraParams) {
		this.extraParams = extraParams;
	}
	public List<Integer> getDocStateList() {
		return docStateList;
	}
	public void setDocStateList(List<Integer> docStateList) {
		this.docStateList = docStateList;
	}
}
