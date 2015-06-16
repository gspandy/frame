package mb.mba.core.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 出入库交易流水
 * @author sun@mb.com
 * @date 2015年5月29日
 * @version
 */
public class TradesTransEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 仓库code
	 */
	private String warehCode;

	/**
	 * 商品编码
	 */
	private String goodsCode;

	/**
	 * 单据类型
	 */
	private String docType;

	/**
	 * 单据编号
	 */
	private String docNum;

	/**
	 * 交易数量
	 */
	private BigDecimal tranQuantity;

	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 交易时间
	 */
	private Date tranTime;

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

	public String getWarehCode() {
		return warehCode;
	}

	public void setWarehCode(String warehCode) {
		this.warehCode = warehCode == null ? null : warehCode.trim();
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode == null ? null : goodsCode.trim();
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType == null ? null : docType.trim();
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum == null ? null : docNum.trim();
	}

	public BigDecimal getTranQuantity() {
		return tranQuantity;
	}

	public void setTranQuantity(BigDecimal tranQuantity) {
		this.tranQuantity = tranQuantity;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}