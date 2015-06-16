/**
 * 日期时间工具类
 * 
 * @author liuchunbo liuchunbo@metersbonwe.com
 * @date 2015年1月22日 下午1:55:41
 * @since V1.0
 * @version V1.0
 */

package mb.mba.core.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 */
public class DateUtil {
    /**
     * 获取现在时间
     * 
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     * 
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     * 
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     * 
     * @param dateDate
     * @param k
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 比较开始时间和结束时间大小
     * @param beginDate
     * @param endDate
     * @return true 开始时间小于等于结束时间
     */
    public static boolean compare(Date beginDate,Date endDate) {
    	boolean result = false;
    	if (beginDate != null 
    			&& endDate != null && (beginDate.getTime() <= endDate.getTime())) {
    		result = true;
    	}
    	return result;
    }
    
    /**
     * 判断日期是否符合指定年度和月份 (例如:2007, 7 , 20007-07-07)
     * @param year
     * @param month
     * @param date
     * @return true:
     */
    public static boolean isConfirmYearAndMonth(String year,String month,Date date) {
    	boolean result = false;
    	if (CommonUtil.isEmpty(year) || CommonUtil.isEmpty(month)
    			|| null == date) {
    		return result;
    	}
    	String str = dateToStr(date);
    	String[] patten = str.split("-");
    	
    	if (null == patten || patten.length != 3) {
    		return result;
    	}
    	
    	if (CommonUtil.isEmpty(patten[0]) || CommonUtil.isEmpty(patten[1]) || CommonUtil.isEmpty(patten[2])){
    		return result;
    	}
    	
    	String cmonth = String.valueOf(Integer.valueOf(patten[1]));
    	
    	if (year.equals(patten[0]) && month.equals(cmonth)){
    		result = true;
    	}
    	return result;
    }
    
    
	/**
	 * 功能描述： 判断指定时间段是否有重叠
	 * 开始和结束时间已经部分有序
	 * @param startDate1 开始时间1
	 * @param endDate1 结束时间1
	 * @param startDate2 开始时间2
	 * @param endDate2 结束时间2
	 * @return     
	 */
	public static boolean compareTimePeriod(Date startDate1, Date endDate1,
			Date startDate2, Date endDate2) {
		if (null == startDate1 || null == endDate1 || null == startDate2 || null == endDate2) {
			return false;
		}
		if ((startDate1.getTime() > endDate2.getTime()) || startDate2.getTime() > endDate1.getTime()) {
			return true;
		}
		
		return false;
	}

}
