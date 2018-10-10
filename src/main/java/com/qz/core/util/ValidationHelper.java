package com.qz.core.util;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author chance
 * @date 2017年11月27日下午4:08:56
 */
public class ValidationHelper {

	private static String msg;
	
	public static <T> boolean valide(T model) {
		resetMsg();
		
		if (null == model) {
			msg = "入参不能为空";
			return false;
		}
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<T>> validResult = validator.validate(model);
		if (null != validResult && validResult.size() > 0) {
			StringBuilder sb = new StringBuilder();
			
			Iterator<ConstraintViolation<T>> iterator = validResult.iterator();
			while(iterator.hasNext()){
				ConstraintViolation<?> constraintViolation = iterator.next();
				
				String errMsg = constraintViolation.getPropertyPath().toString() 
						+ "(约束：" + constraintViolation.getMessage().toString()
						+ ")";
				
				
				// 如果需要其他信息可以自己处理
				sb.append(errMsg).append("、");
			}
			if (sb.lastIndexOf("、") == sb.length() - 1) {
				sb.delete(sb.length() - 1, sb.length());
			}
			msg = "请检查入参中的非法值:" + sb.toString();
			return false;
		}
		return true;
	}

	public static <T> boolean notValide(T dto){
		return !valide(dto);
	}
	
	public static String getMsg() {
		return msg;
	}

	private static void resetMsg(){
		msg = null;
	}
}
