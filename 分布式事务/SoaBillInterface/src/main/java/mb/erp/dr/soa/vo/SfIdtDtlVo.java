package mb.erp.dr.soa.vo;

/**
 * 新ERP现货订单详情实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfIdtDtlVo
 * @since       全流通改造
 */
public class SfIdtDtlVo extends NewBaseBizDtlVo{
	private static final long serialVersionUID = -2691022892051248546L;

	private Long id; //内码

    private Long sfIdtId; //外键
    
    private Long prodId; //商品ID

    private Double orderQty; //订货数量

    private Double aturePrice; //实际单价

    private String isPresent; //是否赠品

    private Double amount; //金额 小计=折后价*数量

    private String remark; //备注

    private String promotionDesc; //促销备注

    private Double allocQty; //分配数量

    private Double actQty; //发货数量

    private Double oossettleprice;

    private Double oosbanggogain;

    private Double oosmarketgain;

    private Double ooscompgain;

    private Double rcvQty;

    private Double settlementPrice;

    private Double shopUnitPrice; //断色断码订单下发时的结算价

    private Double shopDiscRate; //断色断码订单下发时的折率

    private Double shopAmount; //冻结的金额

    private Long rcptLocId; //收货货位ID

    private String saleLocCode; //销售货位

    private String clerk; //销售顾问

    private Double shopCurrQty; //门店当前库存

    private Long line; //行号

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSfIdtId() {
		return sfIdtId;
	}

	public void setSfIdtId(Long sfIdtId) {
		this.sfIdtId = sfIdtId;
	}

	public Long getRcptLocId() {
		return rcptLocId;
	}

	public void setRcptLocId(Long rcptLocId) {
		this.rcptLocId = rcptLocId;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public Double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Double orderQty) {
        this.orderQty = orderQty;
    }

    public Double getAturePrice() {
        return aturePrice;
    }

    public void setAturePrice(Double aturePrice) {
        this.aturePrice = aturePrice;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent == null ? null : isPresent.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc == null ? null : promotionDesc.trim();
    }

    public Double getAllocQty() {
        return allocQty;
    }

    public void setAllocQty(Double allocQty) {
        this.allocQty = allocQty;
    }

    public Double getActQty() {
        return actQty;
    }

    public void setActQty(Double actQty) {
        this.actQty = actQty;
    }

    public Double getOossettleprice() {
        return oossettleprice;
    }

    public void setOossettleprice(Double oossettleprice) {
        this.oossettleprice = oossettleprice;
    }

    public Double getOosbanggogain() {
        return oosbanggogain;
    }

    public void setOosbanggogain(Double oosbanggogain) {
        this.oosbanggogain = oosbanggogain;
    }

    public Double getOosmarketgain() {
        return oosmarketgain;
    }

    public void setOosmarketgain(Double oosmarketgain) {
        this.oosmarketgain = oosmarketgain;
    }

    public Double getOoscompgain() {
        return ooscompgain;
    }

    public void setOoscompgain(Double ooscompgain) {
        this.ooscompgain = ooscompgain;
    }

    public Double getRcvQty() {
        return rcvQty;
    }

    public void setRcvQty(Double rcvQty) {
        this.rcvQty = rcvQty;
    }

    public Double getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Double settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public Double getShopUnitPrice() {
        return shopUnitPrice;
    }

    public void setShopUnitPrice(Double shopUnitPrice) {
        this.shopUnitPrice = shopUnitPrice;
    }

    public Double getShopDiscRate() {
        return shopDiscRate;
    }

    public void setShopDiscRate(Double shopDiscRate) {
        this.shopDiscRate = shopDiscRate;
    }

    public Double getShopAmount() {
        return shopAmount;
    }

    public void setShopAmount(Double shopAmount) {
        this.shopAmount = shopAmount;
    }

    public String getSaleLocCode() {
        return saleLocCode;
    }

    public void setSaleLocCode(String saleLocCode) {
        this.saleLocCode = saleLocCode == null ? null : saleLocCode.trim();
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk == null ? null : clerk.trim();
    }

    public Double getShopCurrQty() {
        return shopCurrQty;
    }

    public void setShopCurrQty(Double shopCurrQty) {
        this.shopCurrQty = shopCurrQty;
    }

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
}