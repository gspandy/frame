package mb.mba.core.entity;

/**
 * 类描述： 成本组仓库门店关系表
 * @author:陈志杰
 * @version   
 * @2015年6月5日           
 */
public class CostAccountingWarehouseEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8350339249205624185L;

	/**
	 * id
	 */
	private Long id; 

	/**
	 * 成本组id
	 */
	private Long costId; 

	/**
	 * 仓库编码
	 */
	private String warehCode; 

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

	public String getWarehCode() {
		return warehCode;
	}

	public void setWarehCode(String warehCode) {
		this.warehCode = warehCode == null ? null : warehCode.trim();
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}