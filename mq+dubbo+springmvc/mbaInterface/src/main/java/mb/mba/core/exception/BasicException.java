/**
 * BasicException.java
 * com.mtsbw.platform.core.exception
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   1.0		 2015年2月4日 赵向兵_ZhaoXiangBing
 *
 * Copyright (c) 2015, Meters/Bonwe All Rights Reserved.
*/
package mb.mba.core.exception;

/**
 * 异常基本类
 */
public class BasicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8228241622743907252L;

	protected String errorCode;

	/** 异常错误信息，由实际抛出异常的类定义 */
	protected String errorMsg;

	/** 根异常，保持异常链 */
	protected Throwable caused;
	
	public BasicException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
	
	public BasicException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public BasicException(String errorCode, Throwable caused) {
		super(caused);
		this.errorCode = errorCode;
		this.caused = caused;
	}

	public BasicException(String errorCode, String errorMsg, Throwable caused) {
		super(errorMsg, caused);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.caused = caused;
	}

	/**
	 * 获得异常的错误代码
	 * 
	 * @return 异常错误代码
	 */
	public String getErrorCode() {
		/** 如果异常定义了错误代码 */
		if (errorCode != null && !"".equals(errorCode.trim())) {
			return errorCode;
		}

		/**
		 * 如果没有定义错误代码,并且该异常是一个间接异常 则返回根异常的错误代码
		 */
		if (caused != null) {
			if (caused instanceof BasicException) {
				BasicException causedException = (BasicException) caused;
				return causedException.getErrorCode();
			} else {
				return errorCode;
			}
		}

		return errorCode;
	}

	/**
	 * 设置异常的错误代码
	 * 
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 获得异常的错误信息
	 * 
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		/** 如果异常定义了错误信息 */
		if (errorMsg != null && !"".equals(errorMsg))
			return errorMsg;

		/**
		 * 如果没有定义错误信息,并且该异常是一个间接异常 则返回根异常的错误信息
		 */
		if (caused != null) {
			if (caused instanceof BasicException) {
				BasicException causedException = (BasicException) caused;
				return causedException.getErrorMsg();
			} else {
				return errorMsg;
			}
		}

		return errorMsg;
	}

	/**
	 * 设置异常的错误信息
	 * 
	 * @param errorMsg
	 *         
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 获得根异常
	 * 
	 * @return 
	 */
	public Throwable getCaused() {
		return caused;
	}

	/**
	 * 设置根异常
	 * 
	 * @param caused
	 *           
	 */
	public void setCaused(Throwable caused) {
		this.caused = caused;
	}
	
}
