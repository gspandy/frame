package mb.mba.core.entity.auth;

import java.util.List;

import mb.mba.core.entity.BaseEntity;

/**
 * 角色信息
 * @author   maojq
 * @data 2015年5月20日    
 * @see       
 * @since
 */
public class RoleEntity extends BaseEntity{
    
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -1437806591156046972L;

    /**
     * id
     */
	private Integer id;
	
	/**
     * 编码
     */
    private String code;
    
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 权限列表
	 */
	private List<AuthorityMenuEntity> authorityMenus;
	
	/**
	 * 构造方法
	 */
	public RoleEntity(){}
	
	/**
	 * 构造方法
	 * @param id
	 * @param code
	 * @param name
	 */
	public RoleEntity(Integer id, String code, String name) {
        super();
        this.id = id;
        this.code = code;
        this.name = name;
    }
    public void setId(Integer id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	
	public Integer getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}

    public List<AuthorityMenuEntity> getAuthorityMenus() {
        return authorityMenus;
    }

    public void setAuthorityMenus(List<AuthorityMenuEntity> authorityMenus) {
        this.authorityMenus = authorityMenus;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
