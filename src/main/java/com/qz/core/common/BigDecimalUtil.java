package com.qz.core.common;

import java.math.BigDecimal;

/**
 * @author chance
 * @date 2017年11月3日下午2:42:32
 */
public class BigDecimalUtil {

	private static int DEF_DIV_SCALE = 10; // 默认精确的小数位
	
	/**
	 * 数据取精度
	 * @param b 原始数据
	 * @param scale 精度
	 * @return
	 */
	public static BigDecimal scale(BigDecimal b, int scale){
		b = null == b ? BigDecimal.ZERO : b;
		return b.setScale(scale);
	}
	
	/**
	 * 数据格式化
	 * @param d 原始数据
	 * @param scale 精度
	 * @return
	 */
	public static BigDecimal scale(double d, int scale){
		BigDecimal b = new BigDecimal(Double.toString(d));
		BigDecimal s = b.setScale(scale, BigDecimal.ROUND_DOWN);
		return s;
	}
	
	/**
	 * 用于两个数大小的比较
	 * @param d1 比较数
	 * @param d2 被比较数
	 * @return -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
	 */
	public static int compareTo(double d1, double d2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(d1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(d2));
		return b1.compareTo(b2);
	}
	
	/**
	 * 提供精确的减法运算
	 * @param d1 被减
	 * @param d2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double d1, double d2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(d1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(d2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 提供精确的加法运算
	 * @param params 加数和被加数
	 * @return 
	 */
	public static double add(double... params){
		BigDecimal b = BigDecimal.ZERO;
		for(double d : params){
			b.add(new BigDecimal(Double.valueOf(d)));
		}
		return b.doubleValue();
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param d1 被乘
	 * @param d2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double d1, double d2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(d1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(d2));
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以0位，以后的数字四舍五入
	 * 
	 * @param v1 被除
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	/**
	 * 提供（相对）精确的除法运算当发生除不尽的情况时，由scale参数定精度，以后的数字四舍五入
	 * 
	 * @param v1 被除
	 * @param v2 除数
	 * @param scale 表示表示精确到小数点以后几位
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
