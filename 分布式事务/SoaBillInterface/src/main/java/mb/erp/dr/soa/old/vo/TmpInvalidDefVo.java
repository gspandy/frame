package mb.erp.dr.soa.old.vo;

import java.io.Serializable;

public class TmpInvalidDefVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 6539665912556716854L;

	private String code;

	private String type;

	private String action;

	private String flg;

	private String remark; // 字段名称

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action == null ? null : action.trim();
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}