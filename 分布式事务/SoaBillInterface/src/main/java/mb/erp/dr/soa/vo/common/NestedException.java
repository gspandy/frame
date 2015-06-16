package mb.erp.dr.soa.vo.common;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NestedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1546048893101909203L;
	private Throwable cause;
	private String ecode;// 错误码

	/**
	 * 特殊的构造器
	 * @param e
	 */
	public NestedException(Exception e) {
		super(e.getMessage());
		if(e instanceof NestedException){
			 NestedException ne=(NestedException)e;
			 this.ecode=ne.getEcode();
		}else{
			this.ecode="error";
		}
	}
	
	public NestedException(String msg) {
		super(msg);
	}

	public NestedException(String ecode, String msg) {
		super(msg);
		this.ecode = ecode;
	}

	public NestedException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	public NestedException(String ecode, String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
		this.ecode = ecode;
	}

	public Throwable getCause() {
		return (this.cause == null ? super.getCause() : this.cause);
	}

	public String getMessage() {
		String message = "";
		Throwable cause = getCause();
		if (cause != null) {
			message =cause.getMessage();
		}else{
			message =super.getMessage();
		}
		return message;
	}

	public void printStackTrace(PrintStream ps) {
		if (getCause() == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		if (getCause() == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			getCause().printStackTrace(pw);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
}
