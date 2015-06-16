package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 货位库存表vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         StkDtlVo
 * @since       全流通改造
 */
public class StkDtlVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4271352985447307281L;
	
	private String warehId ; // 仓库编码
	private String locId ; // 货位编码
	private String prodId ; // 商品编码
	private Double stkOnHand ; // 实物库存
	private Double allocQty ; // 已分配库存
	private Double expdQty ; // 实际库存
	private Date stkChangeDate ; // 变更日期

    
    public String getWarehId() {
		return warehId;
	}

	public void setWarehId(String warehId) {
		this.warehId = warehId;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public Double getStkOnHand() {
        return stkOnHand;
    }

    public void setStkOnHand(Double stkOnHand) {
        this.stkOnHand = stkOnHand;
    }

    public Double getAllocQty() {
        return allocQty;
    }

    public void setAllocQty(Double allocQty) {
        this.allocQty = allocQty;
    }

    public Double getExpdQty() {
        return expdQty;
    }

    public void setExpdQty(Double expdQty) {
        this.expdQty = expdQty;
    }

    public Date getStkChangeDate() {
        return stkChangeDate;
    }

    public void setStkChangeDate(Date stkChangeDate) {
        this.stkChangeDate = stkChangeDate;
    }
}