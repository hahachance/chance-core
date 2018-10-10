package com.qz.core.support;

/**
 * 返回消息类型
 * <ul>
 * <li>success(boolean): 是否成功
 * <li>code(String):返回码 
 * <li>msg(String): 返回消息
 * <li>data(Object): 返回数据
 * </ul>
 * @author chance
 * @date 2017年11月10日上午9:56:21
 */
public class RetMap {

	private boolean success;
	private String code;
	private String msg;
	private Object data;

	public RetMap success(){
		RetCode retCode = RetCode.SUCCESS_GENERAL;
		return this.success(retCode.getCode(), retCode.getMessage());
	}
	
	public RetMap success(String msg){
		return this.success(RetCode.SUCCESS_GENERAL.getCode(), msg);
	}
	
	private RetMap success(String code, String msg){
		return this.setSuccess(true).setRet(code, msg);
	}
	
//	public RetMap fail(){
//		RetCode retCode = RetCode.FAIL_GENERAL;
//		return this.fail(retCode.getCode(), retCode.getMessage());
//	}
	
	public RetMap fail(RetCode retCode){
		return this.fail(retCode.getCode(), retCode.getMessage());
	}
	
	public RetMap fail(RetCode retCode, String msg){
		return this.fail(retCode.getCode(), msg);
	}
	
	private RetMap fail(String code, String msg){
		return this.setSuccess(false).setRet(code, msg);
	}
	
	private RetMap setRet(String code, String msg){
		return this.setCode(code).setMsg(msg);
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public boolean isFail() {
		return !success;
	}

	public RetMap setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getCode() {
		return code;
	}

	public RetMap setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public RetMap setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public RetMap setData(Object data) {
		this.data = data;
		return this;
	}
}
