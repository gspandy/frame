package mb.mba.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共方法
 * @author cyyu
 *
 */
public class CommonUtil {

	/** 
     * MD5 加密 
     */  
    public static String MD5(String str) {  
        MessageDigest messageDigest = null;  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        byte[] byteArray = messageDigest.digest();  
        StringBuffer md5StrBuff = new StringBuffer();  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
        return md5StrBuff.toString();  
    }  
    
    /** 
     * MD5 加密 
     */  
    public static Map<String,String> MD5List(List<String> list) {  
        MessageDigest messageDigest = null;  
        Map<String,String> result = new HashMap<String, String>();
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            for (String str : list) {
            	messageDigest.reset();  
            	messageDigest.update(str.getBytes("UTF-8"));  
            	byte[] byteArray = messageDigest.digest();  
            	StringBuffer md5StrBuff = new StringBuffer();  
            	for (int i = 0; i < byteArray.length; i++) {              
            		if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
            			md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            		else  
            			md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
            	}  
            	result.put( md5StrBuff.toString(),str);  
            }
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("NoSuchAlgorithmException caught!");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;
    }  
    
    
    /**
     * 功能描述： 判断字符串是否为空
     * @param str
     * @return     
     */
    public static boolean isEmpty(String str){
    	boolean result = false;
    	if (null == str || str.length() == 0) {
    		result = true;
    	}
    	return result;
    }
}
