/*
 ***********************************************************************
 * Program name: <DateUtil>
 * Program description:TODO
 * Author: Su Gen
 * Mail: sugen@live.com
 * Revision history: 1.0
 *
 * Description of Change	Programmer		Date
 * ---------------------	----------		----
 * Initial creation		    Su Gen		2013-9-09
 *  
 * Copyright (c) 2013 by 3TI  ALL RIGHTS RESERVED
 *
 ***********************************************************************
 */
package mb.erp.dr.soa.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public final class DateUtil {
	
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT = "yyyy/MM/dd";

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getDateOfString(long time, String patten) throws ParseException {
		if(time!=0){
			return (new SimpleDateFormat(patten).format(new Date(time))).toString();
		}
		return "";
	}
	
	public static String formatDate(Date d) {
		return format(d, DATE_FORMAT);
	}

	public static String formatDateTime(Date d) {
		return format(d, DATETIME_FORMAT);
	}
	
	public static Timestamp getNow(){
        return new Timestamp((new Date()).getTime());
    }

	public static String format(Date d, String format) {
		return format(d, format, Locale.getDefault());
	}

	public static String format(Date d, String format, Locale locale) {
		if (d == null)
			return "";
		DateFormat df = new SimpleDateFormat(format, locale);
		return df.format(d);
	}

	public static String now(String format)
    {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(new Date());
    }

	public static Date now() {
		return new Date();
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static Date today(){
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(formatDate(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean checkFormat(String s) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		df.setLenient(false);

		try {
			df.parse(s);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static Date firstDay(Date d) {
		if (d == null)
			return d;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Calendar convert(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}

	public static Date lastDay(Date d) {
		if (d == null)
			return d;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	public static int getDaysInMonth(Date d) {
		if (d == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date nextMonth(Date d) {
		return addMonth(d, 1);
	}

	public static Date addMonth(Date d, int mth) {
		if (d == null)
			return d;
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(d);
		cal.add(Calendar.MONTH, mth);

		return cal.getTime();
	}

	public static Date previousMonth(Date d) {
		return addMonth(d, -1);
	}

	/**
	 * return array including 12 months of one year
	 *
	 * @param year
	 *            which year's month will be return, if the year equals 0,
	 *            return current year
	 * @return
	 */
	public static Date[] getYearMonthList(int year) {
		Calendar cal = Calendar.getInstance();
		if (year > 0)
			cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date[] d = new Date[12];
		for (int i = 0; i < 12; i++) {
			Calendar c = (Calendar) cal.clone();
			c.set(Calendar.MONTH, i);
			d[i] = c.getTime();
		}
		return d;
	}


	/**
	 * caculate the working days int "month"
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWorkingDay(String year, String month) {
		Calendar cal_start = Calendar.getInstance();
		cal_start.set(Calendar.YEAR, Integer.parseInt(year));
		cal_start.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal_start.set(Calendar.DAY_OF_MONTH, 1);

		Calendar cal_end = Calendar.getInstance();
		cal_end.set(Calendar.YEAR, Integer.parseInt(year));
		cal_end.set(Calendar.MONTH, Integer.parseInt(month) - 1);

		int monthdays = cal_end.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal_end.set(Calendar.DAY_OF_MONTH, monthdays);

		return getWorkingDay(cal_start, cal_end);
	}

	public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR) + 1;
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	public static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = 0;
		int betweendays = getDaysBetween(d1, d2);
		int firstDayInWeek = d1.get(Calendar.DAY_OF_WEEK);
		//the 1st day is not start with sunday
		if (firstDayInWeek > 1) {
			result = 7 - firstDayInWeek;
			betweendays -= (result + 1);
		}
		result += betweendays / 7 * 5;

		int days = betweendays % 7;
		if (days > 0)
			result += days - 1 ;

		return result;
	}
	
	public static String getDateOfString(Timestamp time, String patten) throws ParseException {
    	if(time!=null){
    		return (new SimpleDateFormat(patten).format(new Date(time.getTime()))).toString();
    	}
    	return "";
}
	
	public static long compareTo(Date a,Date b){
		 long l=a.getTime()-b.getTime();
		 long day=l/(24*60*60*1000);
		 return day;
	}
	
	public static Date addDay(String d){
		SimpleDateFormat sd=new SimpleDateFormat(DATE_FORMAT);  
		Date daten = null;
		try {
			daten = sd.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c=Calendar.getInstance();  
		c.setTime(daten);  
		c.add(Calendar.DATE, 1);  
		Date date=c.getTime(); 
		return date;
	}
	
	public static boolean isNowDay(String d){
		boolean flg = false;
		SimpleDateFormat sd=new SimpleDateFormat(DATE_FORMAT);
		String nowDate = sd.format(new Date());
		try {
			d = sd.format(sd.parse(d));
			if (d.equals(nowDate)) {
				flg = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flg;
	}
	
	public static String dateToString(Date d){
		SimpleDateFormat sd=new SimpleDateFormat(DATE_FORMAT);
		String date = sd.format(d);
		return date;
	}
	
	public static Date stringToDate(String s) throws ParseException{
		SimpleDateFormat sd=new SimpleDateFormat(DATE_FORMAT);
		Date date = sd.parse(s);
		return date;
	}
}