package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

public class WprcLstDtlVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1655618227973656202L;

	private byte[] uidCol; // 唯一主键

	private String unitId; // 供货方编码

	private String prcLstType; // 价格类型

	private String currency; // 币种

	private String prodId; // 商品编码

	private String prodProp; // 商品性质编码

	private String prodSort; // 商品组别编码

	private String prodStyle; // 商品类别编码

	private String color; // 商品颜色编码

	private String edition; // 商品版型编码

	private String spec; // 商品规格编码

	private String prcPlcy; // 定价方式

	private Double price; // 单价

	private Double discRate; // 折率

	private Date effDate; // 有效日期

	private String brandId; // 品牌编码

	private String wunitId; // 购货方编码

	private String prnNum; // 价格单编号

	private Date execDate; // 执行日期

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	public String getPrcLstType() {
		return prcLstType;
	}

	public void setPrcLstType(String prcLstType) {
		this.prcLstType = prcLstType == null ? null : prcLstType.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId == null ? null : prodId.trim();
	}

	public String getProdProp() {
		return prodProp;
	}

	public void setProdProp(String prodProp) {
		this.prodProp = prodProp == null ? null : prodProp.trim();
	}

	public String getProdSort() {
		return prodSort;
	}

	public void setProdSort(String prodSort) {
		this.prodSort = prodSort == null ? null : prodSort.trim();
	}

	public String getProdStyle() {
		return prodStyle;
	}

	public void setProdStyle(String prodStyle) {
		this.prodStyle = prodStyle == null ? null : prodStyle.trim();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color == null ? null : color.trim();
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition == null ? null : edition.trim();
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec == null ? null : spec.trim();
	}

	public String getPrcPlcy() {
		return prcPlcy;
	}

	public void setPrcPlcy(String prcPlcy) {
		this.prcPlcy = prcPlcy == null ? null : prcPlcy.trim();
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

	public Date getEffDate() {
		return effDate;
	}

	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId == null ? null : brandId.trim();
	}

	public String getWunitId() {
		return wunitId;
	}

	public void setWunitId(String wunitId) {
		this.wunitId = wunitId == null ? null : wunitId.trim();
	}

	public String getPrnNum() {
		return prnNum;
	}

	public void setPrnNum(String prnNum) {
		this.prnNum = prnNum == null ? null : prnNum.trim();
	}

	public Date getExecDate() {
		return execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	public byte[] getUidCol() {
		return uidCol;
	}

	public void setUidCol(byte[] uidCol) {
		this.uidCol = uidCol;
	}
}