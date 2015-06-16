package mb.mba.core.entity.auth;

import java.util.List;

import mb.mba.core.entity.BaseEntity;

/**
 * @类描述：部门组织实体
 * @author  毛建强
 * @2015年5月29日
 * @version
 */
public class DepartmentEntity extends BaseEntity{
    
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private String id;
    
    /**
     * name
     */
    private String name;
    
    /**
     * code
     */
    private String code;
    
    /**
     * 父组织
     */
    private DepartmentEntity parent;
    
    /**
     * 子部门
     */
    private List<DepartmentEntity> childrens;
    
    /**
     * 是否生效/作废
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DepartmentEntity getParent() {
        return parent;
    }

    public void setParent(DepartmentEntity parent) {
        this.parent = parent;
    }

    public List<DepartmentEntity> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<DepartmentEntity> childrens) {
        this.childrens = childrens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
