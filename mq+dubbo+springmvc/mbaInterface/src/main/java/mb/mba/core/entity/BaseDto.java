package mb.mba.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 明细共同信息
 */
public class BaseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920114453215515201L;

	private Date createTime;

	private Date modifyTime;

	private String createUser;

	private String modifyUser;
	
	private String isValid; //是否有效

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
}
