package com.joviansoft.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HlStringUtils {

	/**
	 * 反回系统当前时间，格式是yyyyMMddHHmmss 返回系统的时间应该调用CodeManager类中的getServerStringDate方法
	 * 
	 * @return
	 */
	// public static String getCurrentDate(){
	// return formatDateBy(new Date(), "yyyyMMddHHmmss");
	// }

	/**
	 * 根据传入的格式输出日期（如yyyy年MM月dd日） 年月日时分秒：20040412090909(yyyyMMddHHmmss)
	 * 
	 * @param date
	 * @param sFormat
	 * @return
	 */
	public static String formatDateBy(java.util.Date date, String sFormat) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat(sFormat);
		return newFormat.format(date);
	}

	/**
	 * 将格式化的日期字串转化成日期类型(如yyyy年MM月DD日->date)
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.util.Date strToDate(String strDate, String sFormat) {
		if ((null == strDate) || ("".equals(strDate.trim()))) {
			return null;
		}
		SimpleDateFormat newFormat = new SimpleDateFormat(sFormat);
		try {
			return newFormat.parse(strDate);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * hl add 用来判断一个字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean StringIsEmpty(String string) {
		return (string == null || string == "" || string.equals(""));
	}

	/**
	 * hl add 用来判断一个字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean StringIsEmpty(Object string) {
//		return (string == null || string == "" || string.equals(""));
		return (string == null || string.toString().equals(""));
	}

	/**
	 * hl add 20100420 判断两个类是否相同
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean IsSame(Object obj1, Object obj2){
		return (obj1==null&&obj2==null)||(obj1!=null&&obj1.equals(obj2));
	}
	
	/**
	 * 根据一个行政区划，返回这个行政区划的头，用于组织LIKE语句 比如:330100返回3301
	 * 
	 * @param qhdm
	 * @return
	 */
	public static String getQhdmLikeHead(String qhdm) {
		String tmp = qhdm.substring(0, 2);
		if (tmp.equals("00")) {// 全局用户
			return "";
		} else {
			tmp = qhdm.substring(2, 4);
			if (tmp.equals("00")) {// 全省用户
				return qhdm.substring(0, 2);
			} else {
				tmp = qhdm.substring(4, 6);
				if (tmp.equals("00")) {// 地市用户
					return qhdm.substring(0, 4);
				} else {
					return qhdm;
				}
			}
		}
	}

	/**
	 * 根据一个单位代码，返回这个单位代码的头，用于组织LIKE语句 比如:33010100返回330101
	 * 
	 * @param dwdm
	 * @return
	 */
	public static String getDwdmLikeHead(String dwdm) {
		String tmp = dwdm.substring(0, 2);
		if (tmp.equals("00")) {// 全局用户
			return "";
		} else {
			tmp = dwdm.substring(2, 4);
			if (tmp.equals("00")) {// 全省用户
				return dwdm.substring(0, 2);
			} else {
				tmp = dwdm.substring(4, 6);
				if (tmp.equals("00")) {// 地市用户
					return dwdm.substring(0, 4);
				} else {
					tmp = dwdm.substring(6, 9);
					if (tmp.equals("000")) {// 区县用户
						return dwdm.substring(0, 6);
					} else {
						return dwdm;
					}
				}
			}
		}
	}

}
