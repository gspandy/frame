package mb.mba.core.entity;

/**
 * @类描述：贸易伙伴关系
 * @author  毛建强
 * @2015年5月29日
 * @version
 */
public class PartnerShipEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -8699668644288351897L;
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 组织code
     */
    private String unitCode;
    
    /**
     * 组织名称
     */
    private String unitName;
    
    /**
     * 贸易伙伴关系
     */
    private String relationCode;
    
    /**
     * 伙伴组织code
     */
    private String relationUnitCode;
    
    /**
     * 伙伴组织Name
     */
    private String relationUnitName;
    
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

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getRelationUnitCode() {
		return relationUnitCode;
	}

	public void setRelationUnitCode(String relationUnitCode) {
		this.relationUnitCode = relationUnitCode;
	}

	public String getRelationUnitName() {
		return relationUnitName;
	}

	public void setRelationUnitName(String relationUnitName) {
		this.relationUnitName = relationUnitName;
	}

	public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

}
