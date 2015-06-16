package mb.mba.core.entity;

import java.math.BigDecimal;

/**
 * @Description: 出入库交易明细实体类
 * @author sun@mb.com
 * @date 2015年5月29日
 * @version
 */
public class TradesDtlEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 交易id
	 */
	private Long tradesId;

	/**
	 * 交易单据号
	 */
	private String docNum;

	/**
	 * 货主
	 */
	private String goodsOwner;

	/**
	 * 商品编码
	 */
	private String goodsCode;

	/**
	 * 数量
	 */
	private BigDecimal quantity;

	/**
	 * 单价
	 */
	private BigDecimal price;

	/**
	 * 折率
	 */
	private BigDecimal disrate;

	/**
	 * 含税总金额
	 */
	private BigDecimal intaxAmount;

	/**
	 * 货物收入(税率1)
	 */
	private BigDecimal goodsIncome;

	/**
	 * 货物销项税率
	 */
	private BigDecimal goodsSaletax;

	/**
	 * 货物销项税额
	 */
	private BigDecimal goodsSaletaxamount;

	/**
	 * 货物税率占比
	 */
	private BigDecimal goodsSaletaxScale;

	/**
	 * 服务收入(税率2)
	 */
	private BigDecimal serviceIncome;

	/**
	 * 服务销项税率
	 */
	private BigDecimal serviceSaletax;

	/**
	 * 服务销项税额
	 */
	private BigDecimal serviceSaletaxamount;

	/**
	 * 服务税率占比
	 */
	private BigDecimal serviceSaletaxScale;

	/**
	 * 预留税收入(税率3)
	 */
	private BigDecimal tax3Income;

	/**
	 * 预留税销项税率
	 */
	private BigDecimal tax3Saletax;

	/**
	 * 预留税销项税额
	 */
	private BigDecimal tax3Saletaxamount;

	/**
	 * 预留税占比
	 */
	private BigDecimal tax3SaletaxScale;

	/**
	 * 单位成本
	 */
	private BigDecimal costPerunit;

	/**
	 * 成本金额
	 */
	private BigDecimal costAmount;

	/**
	 * 折让金额使用
	 */
	private BigDecimal disAmount;

	/**
	 * 是否有效
	 */
	private String isvalid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTradesId() {
		return tradesId;
	}

	public void setTradesId(Long tradesId) {
		this.tradesId = tradesId;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum == null ? null : docNum.trim();
	}

	public String getGoodsOwner() {
		return goodsOwner;
	}

	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner == null ? null : goodsOwner.trim();
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode == null ? null : goodsCode.trim();
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDisrate() {
		return disrate;
	}

	public void setDisrate(BigDecimal disrate) {
		this.disrate = disrate;
	}

	public BigDecimal getIntaxAmount() {
		return intaxAmount;
	}

	public void setIntaxAmount(BigDecimal intaxAmount) {
		this.intaxAmount = intaxAmount;
	}

	public BigDecimal getGoodsIncome() {
		return goodsIncome;
	}

	public void setGoodsIncome(BigDecimal goodsIncome) {
		this.goodsIncome = goodsIncome;
	}

	public BigDecimal getGoodsSaletax() {
		return goodsSaletax;
	}

	public void setGoodsSaletax(BigDecimal goodsSaletax) {
		this.goodsSaletax = goodsSaletax;
	}

	public BigDecimal getGoodsSaletaxamount() {
		return goodsSaletaxamount;
	}

	public void setGoodsSaletaxamount(BigDecimal goodsSaletaxamount) {
		this.goodsSaletaxamount = goodsSaletaxamount;
	}

	public BigDecimal getGoodsSaletaxScale() {
		return goodsSaletaxScale;
	}

	public void setGoodsSaletaxScale(BigDecimal goodsSaletaxScale) {
		this.goodsSaletaxScale = goodsSaletaxScale;
	}

	public BigDecimal getServiceIncome() {
		return serviceIncome;
	}

	public void setServiceIncome(BigDecimal serviceIncome) {
		this.serviceIncome = serviceIncome;
	}

	public BigDecimal getServiceSaletax() {
		return serviceSaletax;
	}

	public void setServiceSaletax(BigDecimal serviceSaletax) {
		this.serviceSaletax = serviceSaletax;
	}

	public BigDecimal getServiceSaletaxamount() {
		return serviceSaletaxamount;
	}

	public void setServiceSaletaxamount(BigDecimal serviceSaletaxamount) {
		this.serviceSaletaxamount = serviceSaletaxamount;
	}

	public BigDecimal getServiceSaletaxScale() {
		return serviceSaletaxScale;
	}

	public void setServiceSaletaxScale(BigDecimal serviceSaletaxScale) {
		this.serviceSaletaxScale = serviceSaletaxScale;
	}

	public BigDecimal getCostPerunit() {
		return costPerunit;
	}

	public void setCostPerunit(BigDecimal costPerunit) {
		this.costPerunit = costPerunit;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getDisAmount() {
		return disAmount;
	}

	public void setDisAmount(BigDecimal disAmount) {
		this.disAmount = disAmount;
	}

	public BigDecimal getTax3Income() {
		return tax3Income;
	}

	public void setTax3Income(BigDecimal tax3Income) {
		this.tax3Income = tax3Income;
	}

	public BigDecimal getTax3Saletax() {
		return tax3Saletax;
	}

	public void setTax3Saletax(BigDecimal tax3Saletax) {
		this.tax3Saletax = tax3Saletax;
	}

	public BigDecimal getTax3Saletaxamount() {
		return tax3Saletaxamount;
	}

	public void setTax3Saletaxamount(BigDecimal tax3Saletaxamount) {
		this.tax3Saletaxamount = tax3Saletaxamount;
	}

	public BigDecimal getTax3SaletaxScale() {
		return tax3SaletaxScale;
	}

	public void setTax3SaletaxScale(BigDecimal tax3SaletaxScale) {
		this.tax3SaletaxScale = tax3SaletaxScale;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}