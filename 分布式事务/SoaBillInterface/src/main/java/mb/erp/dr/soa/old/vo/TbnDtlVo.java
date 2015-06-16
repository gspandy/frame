package mb.erp.dr.soa.old.vo;

/**
 * 调配单明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         TbnDtlVo
 * @since       全流通改造
 */
public class TbnDtlVo  extends BaseBizDtlVo  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4402830268982715019L;
	
	private String venderId ; // 供货方编码
	private String tbnNum ; // 订单编号
	private Double expdQty ; // 调配数量
	private Double delivQty ; // 发货数量
	private Double rcvQty ; // 收货数量
	private String remark ; // 备注
	private Double orderPrice ; // 订货单价
	private Double orderDiscRate ; // 订货折率

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

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getOrderDiscRate() {
        return orderDiscRate;
    }

    public void setOrderDiscRate(Double orderDiscRate) {
        this.orderDiscRate = orderDiscRate;
    }

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getTbnNum() {
		return tbnNum;
	}

	public void setTbnNum(String tbnNum) {
		this.tbnNum = tbnNum;
	}
}