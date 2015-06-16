package mb.mba.core.entity;
/**
 * 
 * 类描述： 成本核算商品精度表
 * @author:陈志杰
 * @version   
 * @2015年6月5日
 */
public class ProductCalculationAccuracyEntity extends BaseEntity {
	private static final long serialVersionUID = 2390075463432519828L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 商品编码
	 */
	private String goodsCode;

	/**
	 * 核算精度
	 */
	private String calculationAccuracy;

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

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode == null ? null : goodsCode.trim();
	}

	public String getCalculationAccuracy() {
		return calculationAccuracy;
	}

	public void setCalculationAccuracy(String calculationAccuracy) {
		this.calculationAccuracy = calculationAccuracy == null ? null
				: calculationAccuracy.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}