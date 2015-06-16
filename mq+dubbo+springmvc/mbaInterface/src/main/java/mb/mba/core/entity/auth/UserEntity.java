package mb.mba.core.entity.auth;

import java.util.List;

import mb.mba.core.entity.BaseEntity;

/**
 * 用户信息
 * @author maojq
 * @data 2015年5月20日     
 * @see       
 * @since
 */
public class UserEntity extends BaseEntity{
	
    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * id
     */
    private Integer id; 
    
    /**
     * number
     */
	private String code; 
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 用户邮箱
	 */
	private String email;
	
	/**
	 * 用户所属部门信息
	 */
	private DepartmentEntity  department;
	
	/**
	 * 用户绑定角色
	 */
	private List<RoleEntity> roles;
	
	/**
	 * 用户对应的所有菜单和按钮权限,此处为迭代存储
	 */
	private List<AuthorityMenuEntity> authorityMenus;
	
	/**
	 * 用户对应所有的菜单和按钮权限，此处是讲按钮和菜单放到同一个list中，方便后续判断权限
	 */
	private List<AuthorityMenuEntity> authorityMenusList;
	
	/**
	 * 构造方法
	 */
	public UserEntity(){};
	
	/**
	 * 构造方法
	 * @param id
	 * @param code
	 * @param userName
	 */
	public UserEntity(Integer id,String code,String userName){
	    this.id = id;
	    this.code = code;
	    this.userName = userName;
	}
	public void setId(Integer id){
		this.id=id;
	}
	
	
	public Integer getId(){
		return this.id;
	}
	
	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
    
    public List<AuthorityMenuEntity> getAuthorityMenus() {
        return authorityMenus;
    }

    public void setAuthorityMenus(List<AuthorityMenuEntity> authorityMenus) {
        this.authorityMenus = authorityMenus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AuthorityMenuEntity> getAuthorityMenusList() {
        return authorityMenusList;
    }

    public void setAuthorityMenusList(List<AuthorityMenuEntity> authorityMenusList) {
        this.authorityMenusList = authorityMenusList;
    }
    

}
