package mb.erp.dr.soa.old.vo;

/**
 * 现货订单详情实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         IdtDtlVo
 * @since       全流通改造
 */
public class IdtDtlVo extends BaseBizDtlVo{
	
	private static final long serialVersionUID = 4642385382785300462L;
	
    private String vendeeId;  //购货方编码
    
    private String idtNum;  //订单编号
    
	private Double orderQty;  //订货数量

    private Double admQty;  //配货数量

    private Double delivQty;  //发货数量

    private Double rcvQty;  //收货数量

    private String remark;  //备注

    public Double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Double orderQty) {
        this.orderQty = orderQty;
    }

    public Double getAdmQty() {
        return admQty;
    }

    public void setAdmQty(Double admQty) {
        this.admQty = admQty;
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

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getIdtNum() {
		return idtNum;
	}

	public void setIdtNum(String idtNum) {
		this.idtNum = idtNum;
	}
}