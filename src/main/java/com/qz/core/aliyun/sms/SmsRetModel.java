package com.qz.core.aliyun.sms;

/**
 * 
 * <ul>
 * <li>success(boolean): 是否成功
 * <li>message(String): 消息
 * </ul>
 * 
 * @author chance
 * @date 2017年11月24日上午11:41:18
 */
public class SmsRetModel {

	private boolean success;
	private String messsage;
	private String code;

	public SmsRetModel fail(String msg){
		return this.setSuccess(false).setMesssage(msg);
	}
	
	public SmsRetModel success(){
		return this.setSuccess(true);
	}
	
	public boolean isSuccess() {
		return success;
	}

	public SmsRetModel setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMesssage() {
		return messsage;
	}

	public SmsRetModel setMesssage(String messsage) {
		this.messsage = messsage;
		return this;
	}

	public String getCode() {
		return code;
	}

	public SmsRetModel setCode(String code) {
		this.code = code;
		return this;
	}
	
}
