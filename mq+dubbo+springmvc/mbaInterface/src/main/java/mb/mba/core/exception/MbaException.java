package mb.mba.core.exception;

public class MbaException extends BasicException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8726907619831132586L;

	public MbaException(String errorCode) {
		super(errorCode);
	}
	
	public MbaException(String errorCode,String errorMsg){
		super(errorCode, errorMsg);
	}
	
	public MbaException(String errorCode, Throwable caused) {
		super(errorCode, caused);
	}

	public MbaException(String errorCode, String errorMsg, Throwable caused) {
		super(errorCode, errorMsg, caused);
	}
	
//	public static void main(String[] args) {
//		try {
//			throw new MbaException("a");
//		} catch (MbaException e) {
//			System.out.println(e.getErrorCode());
//		}
//	}
	

}
