package mb.erp.dr.soa.vo.common;

import java.io.Serializable;

/**
 * 公用的规则字典
 * 
 * @author 孙建明
 * @version 1.0, 2015-03-04
 * @see NDirectoryVo
 * @since 全流通改造
 */
public class NDirectoryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6014771652173549128L;

	private String type;

	private String name;

	private String code;

	private String describe;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
