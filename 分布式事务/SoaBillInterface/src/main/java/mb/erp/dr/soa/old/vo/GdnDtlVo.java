package mb.erp.dr.soa.old.vo;

import java.util.Date;

/**
 * 出库单明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GdnDtlVo
 * @since       全流通改造
 */
public class GdnDtlVo extends BaseBizDtlVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8758289293894566547L;
	
	private String unitId ; // 组织编码
	private String gdnNum ; // 出库单编号
	private Date docDate ; // 单据日期
	private Double quantity ; // 数量
	private Double unitAddtCost ; // 单位附加成本
	private Double unitCost ; // 单位成本
	private Double oldDiscRate ; // 原折率
	private Double preQty ; // 预分拣数量
	private Double cancelQty ; // 取消数量
	private String remark ; // 备注
	private String remark1 ; // 扩展备注1
	private String flag ; // 标记
	private Double amount ; // 金额
	private String lineNo ; // 行号
	private String remark2 ; // 扩展备注2
	private Double sapQty ; // SAP下发数量
	private Double unionQty ; // 统一配货数量
	private Double qtyCommited ; // 已分配数量
	private String locId ; // 货位编码

	
    public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getGdnNum() {
		return gdnNum;
	}

	public void setGdnNum(String gdnNum) {
		this.gdnNum = gdnNum;
	}

	public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitAddtCost() {
        return unitAddtCost;
    }

    public void setUnitAddtCost(Double unitAddtCost) {
        this.unitAddtCost = unitAddtCost;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getOldDiscRate() {
        return oldDiscRate;
    }

    public void setOldDiscRate(Double oldDiscRate) {
        this.oldDiscRate = oldDiscRate;
    }

    public Double getPreQty() {
        return preQty;
    }

    public void setPreQty(Double preQty) {
        this.preQty = preQty;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public Double getCancelQty() {
        return cancelQty;
    }

    public void setCancelQty(Double cancelQty) {
        this.cancelQty = cancelQty;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public Double getSapQty() {
        return sapQty;
    }

    public void setSapQty(Double sapQty) {
        this.sapQty = sapQty;
    }

    public Double getUnionQty() {
        return unionQty;
    }

    public void setUnionQty(Double unionQty) {
        this.unionQty = unionQty;
    }

    public Double getQtyCommited() {
        return qtyCommited;
    }

    public void setQtyCommited(Double qtyCommited) {
        this.qtyCommited = qtyCommited;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId == null ? null : locId.trim();
    }
}