package mb.mba.core.bean;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 2770523997686699318L;
	
	private Boolean success;
	
	private String code;
	
	private String msg;

	public Message() {
	
	}
	
	public Message(Boolean success){
		this.success = success;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
