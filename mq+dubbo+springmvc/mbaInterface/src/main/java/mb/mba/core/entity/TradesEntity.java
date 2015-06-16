package mb.mba.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 出入库交易实体类
 * @author sun@mb.com
 * @date 2015年5月29日
 * @version
 */
public class TradesEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 单据号
	 */
	private String docNum;

	/**
	 * 单据类型
	 */
	private String docType;

	/**
	 * 单据模式，包括新模式，老模式
	 */
	private String docMod;

	/**
	 * 单据日期
	 */
	private Date docDate;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 数量
	 */
	private BigDecimal quantity;

	/**
	 * 发货方
	 */
	private String venderCode;

	/**
	 * 发货仓库
	 */
	private String venderWarehCode;

	/**
	 * 收货方
	 */
	private String vendeeCode;

	/**
	 * 收货仓库
	 */
	private String vendeeWarehCode;

	/**
	 * 出入库方式
	 */
	private String inoutwarehCode;

	/**
	 * 原始单据类型
	 */
	private String srcDocCode;

	/**
	 * 原始单据编号
	 */
	private String srcDocNum;

	/**
	 * 来源单据类别
	 */
	private String fromDocCode;

	/**
	 * 来源单据编号
	 */
	private String fromDocNum;

	/**
	 * 配货方式
	 */
	private String delivCode;

	/**
	 * 实际发货方
	 */
	private String relTraderCode;

	/**
	 * 实际发货仓库
	 */
	private String relTradeWarehCode;

	/**
	 * 传入sap状态
	 */
	private String tosap;

	/**
	 * 往来账处理状态
	 */
	private String process;

	/**
	 * 含税总金额
	 */
	private BigDecimal intaxAmount;

	/**
	 * 税额1
	 */
	private BigDecimal tax1Amount;

	/**
	 * 税额2
	 */
	private BigDecimal tax2Amount;

	/**
	 * 税额3
	 */
	private BigDecimal tax3Amount;

	/**
	 * 折让使用金额
	 */
	private BigDecimal disrateAmount;

	/**
	 * 是否有效
	 */
	private String isvalid;

	/**
	 * 交易明细
	 */
	private List<TradesDtlEntity> dtls;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum == null ? null : docNum.trim();
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType == null ? null : docType.trim();
	}

	public String getDocMod() {
		return docMod;
	}

	public void setDocMod(String docMod) {
		this.docMod = docMod;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * 发货方
	 */
	public String getVenderCode() {
		return venderCode;
	}

	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode == null ? null : venderCode.trim();
	}

	/**
	 * 发货仓
	 * 
	 * @return
	 */
	public String getVenderWarehCode() {
		return venderWarehCode;
	}

	public void setVenderWarehCode(String venderWarehCode) {
		this.venderWarehCode = venderWarehCode == null ? null : venderWarehCode
				.trim();
	}
	/**
	 * 收货方
	 */
	public String getVendeeCode() {
		return vendeeCode;
	}

	public void setVendeeCode(String vendeeCode) {
		this.vendeeCode = vendeeCode == null ? null : vendeeCode.trim();
	}

	/**
	 * 收货仓
	 * 
	 * @return
	 */
	public String getVendeeWarehCode() {
		return vendeeWarehCode;
	}

	public void setVendeeWarehCode(String vendeeWarehCode) {
		this.vendeeWarehCode = vendeeWarehCode == null ? null : vendeeWarehCode
				.trim();
	}

	public String getInoutwarehCode() {
		return inoutwarehCode;
	}

	public void setInoutwarehCode(String inoutwarehCode) {
		this.inoutwarehCode = inoutwarehCode == null ? null : inoutwarehCode
				.trim();
	}

	public String getSrcDocCode() {
		return srcDocCode;
	}

	public void setSrcDocCode(String srcDocCode) {
		this.srcDocCode = srcDocCode == null ? null : srcDocCode.trim();
	}

	public String getSrcDocNum() {
		return srcDocNum;
	}

	public void setSrcDocNum(String srcDocNum) {
		this.srcDocNum = srcDocNum == null ? null : srcDocNum.trim();
	}

	public String getFromDocCode() {
		return fromDocCode;
	}

	public void setFromDocCode(String fromDocCode) {
		this.fromDocCode = fromDocCode == null ? null : fromDocCode.trim();
	}

	public String getFromDocNum() {
		return fromDocNum;
	}

	public void setFromDocNum(String fromDocNum) {
		this.fromDocNum = fromDocNum == null ? null : fromDocNum.trim();
	}

	public String getDelivCode() {
		return delivCode;
	}

	public void setDelivCode(String delivCode) {
		this.delivCode = delivCode == null ? null : delivCode.trim();
	}

	public String getRelTraderCode() {
		return relTraderCode;
	}

	public void setRelTraderCode(String relTraderCode) {
		this.relTraderCode = relTraderCode == null ? null : relTraderCode
				.trim();
	}

	public String getRelTradeWarehCode() {
		return relTradeWarehCode;
	}

	public void setRelTradeWarehCode(String relTradeWarehCode) {
		this.relTradeWarehCode = relTradeWarehCode == null ? null
				: relTradeWarehCode.trim();
	}

	public String getTosap() {
		return tosap;
	}

	public void setTosap(String tosap) {
		this.tosap = tosap == null ? null : tosap.trim();
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process == null ? null : process.trim();
	}

	public BigDecimal getIntaxAmount() {
		return intaxAmount;
	}

	public void setIntaxAmount(BigDecimal intaxAmount) {
		this.intaxAmount = intaxAmount;
	}

	public BigDecimal getTax1Amount() {
		return tax1Amount;
	}

	public void setTax1Amount(BigDecimal tax1Amount) {
		this.tax1Amount = tax1Amount;
	}

	public BigDecimal getTax2Amount() {
		return tax2Amount;
	}

	public void setTax2Amount(BigDecimal tax2Amount) {
		this.tax2Amount = tax2Amount;
	}

	public BigDecimal getTax3Amount() {
		return tax3Amount;
	}

	public void setTax3Amount(BigDecimal tax3Amount) {
		this.tax3Amount = tax3Amount;
	}

	public BigDecimal getDisrateAmount() {
		return disrateAmount;
	}

	public void setDisrateAmount(BigDecimal disrateAmount) {
		this.disrateAmount = disrateAmount;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}

	public List<TradesDtlEntity> getDtls() {
		return dtls;
	}

	public void setDtls(List<TradesDtlEntity> dtls) {
		this.dtls = dtls;
	}
}