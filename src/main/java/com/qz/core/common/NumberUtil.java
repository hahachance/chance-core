package com.qz.core.common;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import com.qz.core.exception.MyException;

/**
 * @author chance
 * @date 2017年11月3日下午2:39:45
 */
public class NumberUtil {

	/**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){ 
        return null == str || "".equals(str) ? false : Pattern.compile("[0-9]*").matcher(str).matches();
    }
	
	/**
	 * 数据格式化
	 * @param d 原始数据
	 * @param scale 精度
	 * @return
	 */
	public static double scale(double d, int scale){
		return BigDecimalUtil.scale(d, scale).doubleValue();
	}
	
	/**
	 * 数据格式化
	 * @param d 原始数据
	 * @param format 格式（如：“0.000”）
	 * @return
	 */
	public static double format(double d, String format){
		String num = new DecimalFormat(format).format(d);
		return Double.parseDouble(num);
	}
	
	/**
	 * 字符串转数字
	 * @param str 字符串
	 * @return
	 */
	public static double parseDouble(String str){
		if(null == str || "".equals(str)){
			return 0.0;
		}
		double ret = 0.0;
		try {
			ret = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 字符串转数字
	 * @param str 字符串
	 * @return
	 */
	public static long parseLong(String str){
		if(null == str || "".equals(str)){
			return 0;
		}
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 字符串转数字
	 * @param str 字符串
	 * @return
	 */
	public static int parseInt(String str){
		if(null == str || "".equals(str)){
			return 0;
		}
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 字符串转数字并设置精度
	 * @param str 字符串
	 * @param scale 精度
	 * @return
	 */
	public static double parseDouble(String str, int scale){
		return NumberUtil.scale(parseDouble(str), scale);
	}
	
	/**
	 * 获取随机数
	 * @param length 随机数位数
	 * @return
	 */
	public static int random(int length){
		if(length < 1){
			throw new MyException("random number length is less than 1");
		}
		SecureRandom random = new SecureRandom();
		
		int bound = (int)Math.pow(10, length) - 1;
		
		return random.nextInt(bound);
	}
	
}
