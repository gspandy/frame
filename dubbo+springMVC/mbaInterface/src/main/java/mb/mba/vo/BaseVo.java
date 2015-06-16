package mb.mba.vo;

import java.io.Serializable;

/**
 * 表映射对象基类
 * @author cyyu
 */
public class BaseVo implements Serializable{
	
	private static final long serialVersionUID = 8408154459882968623L;
	
	private String remark; // 备注

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
