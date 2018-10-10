package com.qz.core.support;

/**
 * 返回码定义文件
 * 所有返回码都在这里定义，0是成功，-1基本错误，2、3普通错误，4业务错误
 *
 * @author chance
 * @date 2017年11月10日上午9:56:08
 */
public enum RetCode {

    SUCCESS_GENERAL("0", "操作成功"),

    FAIL_GENERAL("-1", "操作失败"),
    FAIL_SYSTEM("2000", "系统错误"),
    FAIL_PARAMETER("2001", "参数错误"),

    FAIL_NORESULT("3001", "查询无结果"),
    FAIL_PRIVILEGE("3002", "无操作权限"),
    FAIL_SESSION_INVALID("3101", "会话错误"),
    FAIL_USER_UNREGISTERED("3102", "用户未注册"),
    FAIL_USER_UNLOGIN("3103", "用户未登录"),
    FAIL_LOGIN_INVALID("3104", "自动登录失效"),
    FAIL_LOGIN_ERROR_PWD("3105", "密码错误"),
    FAIL_SMS("3106", "验证码错误"),
    FAIL_SMS_SEND("3107", "短信发送失败"),
    FAIL_SMS_TIMEOUT("3108", "验证码已失效"),
    FAIL_RECH_WECHAT("3201", "调用微信接口失败"),

    FAIL_CARD_UNDEFINED("4101", "查询不到该电卡");


    private String code;
    private String message;

    private RetCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
