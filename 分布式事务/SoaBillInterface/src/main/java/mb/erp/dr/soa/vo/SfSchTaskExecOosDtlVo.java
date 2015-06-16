package mb.erp.dr.soa.vo;

import java.io.Serializable;

/**
 * MQ实体明细类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         SfSchTaskExecOosDtlVo
 * @since       全流通改造
 */
public class SfSchTaskExecOosDtlVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3852236852636078624L;

	private Long id;

    private Long execOosId;

    private String prodNum;

    private Long qty;

    private String locId;

    private String rcptLocId;

    private Double price;

    private Double discRate;

    private String brandId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExecOosId() {
        return execOosId;
    }

    public void setExecOosId(Long execOosId) {
        this.execOosId = execOosId;
    }

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum == null ? null : prodNum.trim();
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId == null ? null : locId.trim();
    }

    public String getRcptLocId() {
        return rcptLocId;
    }

    public void setRcptLocId(String rcptLocId) {
        this.rcptLocId = rcptLocId == null ? null : rcptLocId.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscRate() {
        return discRate;
    }

    public void setDiscRate(Double discRate) {
        this.discRate = discRate;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId == null ? null : brandId.trim();
    }
}