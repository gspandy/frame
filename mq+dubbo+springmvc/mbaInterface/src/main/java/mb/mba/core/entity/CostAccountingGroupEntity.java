package mb.mba.core.entity;

/**
 * 类描述： 成本组实体类
 * @author:陈志杰
 * @version   
 * @2015年6月5日           
 */
public class CostAccountingGroupEntity extends BaseEntity {

	private static final long serialVersionUID = -1813449810562246649L;
	/**
	 * id
	 */
	private Long id;

	/**
	 * 成本组名称
	 */
	private String costAccountingName;

	/**
	 * 成本组code
	 */
	private String code;

	/**
	 * 组织code
	 */
	private String unitCode;

	/**
	 * 成本核算方式
	 */
	private Integer costacCode;

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

	public String getCostAccountingName() {
		return costAccountingName;
	}

	public void setCostAccountingName(String costAccountingName) {
		this.costAccountingName = costAccountingName == null ? null
				: costAccountingName.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode == null ? null : unitCode.trim();
	}

	public Integer getCostacCode() {
		return costacCode;
	}

	public void setCostacCode(Integer costacCode) {
		this.costacCode = costacCode;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}