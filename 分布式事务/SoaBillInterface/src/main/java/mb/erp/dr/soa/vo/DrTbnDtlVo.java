package mb.erp.dr.soa.vo;

/**
 * 新ERP调配单明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-11-21
 * @see         DrTbnDtlVo
 * @since       全流通改造
 */
public class DrTbnDtlVo extends NewBaseBizDtlVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2538552305300101565L;
	
	private Long id ; // 内码
	private Long drTbnId ; // 外键ID
	private Double expdQty ; // 调配数量
	private Double delivQty ; // 发货数量
	private Double rcvQty ; // 收货数量
	private String remark ; // 备注
	private Double crVal ; // 订货金额

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDrTbnId() {
        return drTbnId;
    }

    public void setDrTbnId(Long drTbnId) {
        this.drTbnId = drTbnId;
    }

    public Double getExpdQty() {
        return expdQty;
    }

    public void setExpdQty(Double expdQty) {
        this.expdQty = expdQty;
    }

    public Double getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(Double delivQty) {
        this.delivQty = delivQty;
    }

    public Double getRcvQty() {
        return rcvQty;
    }

    public void setRcvQty(Double rcvQty) {
        this.rcvQty = rcvQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getCrVal() {
        return crVal;
    }

    public void setCrVal(Double crVal) {
        this.crVal = crVal;
    }
}