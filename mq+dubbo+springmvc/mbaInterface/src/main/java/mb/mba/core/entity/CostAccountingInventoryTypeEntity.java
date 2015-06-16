package mb.mba.core.entity;

/**
 * 类描述： 成本组出入库方式关系表
 * 
 * @author:陈志杰
 * @version
 * @2015年6月5日
 */
public class CostAccountingInventoryTypeEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2384040835213788371L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 成本组ID
	 */
	private Long costId;

	/**
	 * 出入库方式code
	 */
	private String inoutwarehCode;

	/**
	 * 出入库方式名称
	 */
	private String inoutwarehName;

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

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public String getInoutwarehCode() {
		return inoutwarehCode;
	}

	public void setInoutwarehCode(String inoutwarehCode) {
		this.inoutwarehCode = inoutwarehCode == null ? null : inoutwarehCode
				.trim();
	}

	public String getInoutwarehName() {
		return inoutwarehName;
	}

	public void setInoutwarehName(String inoutwarehName) {
		this.inoutwarehName = inoutwarehName == null ? null : inoutwarehName
				.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}