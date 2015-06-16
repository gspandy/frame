package mb.mba.core.entity;

/**
 * 类描述： 数据字典表
 * @author:陈志杰
 * @version   
 * @2015年6月5日           
 */
public class DataDirectoryEntity extends BaseEntity {
	private static final long serialVersionUID = 8655782149711153171L;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 描述
	 */
	private String name;

	/**
	 * 编码所属类别
	 */
	private String codeClass;

	/**
	 * 编码所属类别名称
	 */
	private String className;

	/**
	 * 排序号
	 */
	private Integer descno;

	/**
	 * 是否有效
	 */
	private String isvalid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCodeClass() {
		return codeClass;
	}

	public void setCodeClass(String codeClass) {
		this.codeClass = codeClass == null ? null : codeClass.trim();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className == null ? null : className.trim();
	}

	public Integer getDescno() {
		return descno;
	}

	public void setDescno(Integer descno) {
		this.descno = descno;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid == null ? null : isvalid.trim();
	}
}