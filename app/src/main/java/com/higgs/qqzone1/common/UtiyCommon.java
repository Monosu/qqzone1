package com.higgs.qqzone1.common;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class UtiyCommon {
	/**
	 * 将日期转换成指定的格式默认为yyyy-MM-dd hh:mm:ss
	 * 
	 * @param oDate
	 * @return
	 */
	public static String dataFormt(Date oDate) {
		return dataFormt(oDate, "");
	}

	/**
	 * 将日期转换成指定的格式默认为yyyy-MM-dd hh:mm:ss
	 * 
	 * @param oDate
	 *            要转换的日期
	 * @param foramtString
	 *            要转换的格式
	 * @return
	 */
	public static String dataFormt(Date oDate,String foramtString) {
		String result = "";
		if (oDate!=null) {
			String foramt = "yyyy-MM-dd hh:mm:ss";
			if (foramtString!=null&&foramtString.length()>0) {
				foramt = foramtString;
			}
		
			SimpleDateFormat oDateFormat = new SimpleDateFormat(foramt);
			result = oDateFormat.format(oDate);
		}
		return result;
	}

	/**
	 * 将指定字符串转根据格式换成Date类型
	 * 
	 * @param date
	 *            要转换的date类型
	 * @param foramtString
	 *            要转换的格式
	 * @return
	 */
	public static Date stringParseDate(String date, String foramtString) {
		Date result = null;
		if (!TextUtils.isEmpty(date)) {
			String foramt = "yyyy-MM-dd hh:mm:ss";
			if (foramtString != null && foramtString.length() > 0) {
				foramt = foramtString;
			}

			SimpleDateFormat oDateFormat = new SimpleDateFormat(foramt);
			try {
				result = oDateFormat.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 将指定字符串转根据格式换成Date类型
	 * 
	 * @param date
	 *            要转换的date类型
	 *
	 * @return
	 */
	public static Date stringParseDate(String date) {
		return stringParseDate(date, "");
	}
	/**
	 * 获取时间格式的文件名
	 * @return
	 */
	public static String getDateFileName() {
		int romdomNum = (int)(Math.random()*1000000);
		return dataFormt(new Date(),"yyyyMMddhhmmss")+String.valueOf(romdomNum);
	}
	
	/**
	 * 将String类型转换成int
	 * 
	 * @param value
	 *            要转换的值
	 * @param defaultValue
	 *            默认值.转换失败返回此值
	 * @return
	 */
	public static int getParseInt(String value,int defaultValue) {
		int num=defaultValue;
		if (value!=null&&(!value.equals(""))&&value.length()>0) {
			try {
				num = Integer.parseInt(value);
			} catch (Exception e) {
				// TODO: handle exception
				num = defaultValue;
			}
		}
		return num;
	}

	/**
	 * 将String类型转换成int
	 * 
	 * @param value
	 *            要转换的值
	 * @return
	 */
	public static int getParseInt(String value) {
		return getParseInt(value,0);
	}

	/**
	 * 将Object类型转换成int
	 * 
	 * @param value
	 *            要转换的值
	 * @param defaultValue
	 *            转换失败的
	 * @return
	 */
	public static int getParseInt(Object value,int defaultValue) {
		int num = defaultValue;
		if (value!=null) {
			num=getParseInt(value.toString(), defaultValue);
		}
		return num;
	}

	/**
	 * 将Object类型转换成int
	 * 
	 * @param value
	 *            要转换的值
	 * @return
	 */
	public static int getParseInt(Object value) {
		int num = 0;
		if (value!=null) {
			num=getParseInt(value.toString(), 0);
		}
		return num;
	}

	/**
	 * 检测手机是否可以连接网络
	 * 
	 * @param oContext
	 * @return true为有 false为没有
	 */
	public static boolean isWorkConnection(Context oContext) {
		boolean result = false;
		try {
			ConnectivityManager oManager = (ConnectivityManager) oContext
					.getSystemService(CONNECTIVITY_SERVICE);
			if (oManager != null) {
				NetworkInfo oNetworkInfo = oManager.getActiveNetworkInfo();
				if (oNetworkInfo != null) {
					if (oNetworkInfo.isConnected()) {
						if (oNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
							result = true;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public static String urlEncode(String value) {
		String result = value;
		try {
			result = URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			result = value;
			// TODO: handle exception
		}
		return result;
	}
}
