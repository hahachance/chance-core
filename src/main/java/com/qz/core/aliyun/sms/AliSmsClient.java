package com.qz.core.aliyun.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.qz.core.common.DateUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chance
 * @date 2017年11月14日下午3:30:25
 */
public class AliSmsClient {
	
	//******************************* constants

	@Value("${SMS_ACCESS_KEY_ID}")
	private static String SMS_ACCESS_KEY_ID; //yr accessKeyId
	@Value("${SMS_ACCESS_KEY_SECRET}")
	private static String SMS_ACCESS_KEY_SECRET; //yr accessKeySecret
	@Value("${SMS_SIGN_NAME}")
	private static String SMS_SIGN_NAME; //短信签名（可在短信控制台中找到）

//	public static String SMS_ACCESS_KEY_ID = "LTAIaFmxlReRab19";
//	public static String SMS_ACCESS_KEY_SECRET = "nmTe3eqVNkJZw3tuKf48ocb2VuD7gM"; //accessKeySecret
//	public static String SMS_SIGN_NAME = "恒充科技"; //短信签名（可在短信控制台中找到）
	/**
	 * 阿里云oss的区域
	 */
//	private static String END_POINT = "cn-hangzhou";
	
	/**
	 * 默认超时时间
	 */
	private static String DEFAULT_CONNENT_TIMEOUT = "10000";
	private static String DEFAULT_READ_TIMEOUT = "10000";

	
	//******************************* constants variable input
	private boolean smsTest = false;
	private String phone;
	private String templateCode;
	private String templateContent;
	private String smsContent;
	private Map<String, String> templateParamsMap;
	
	//******************************* constants variable output
	
	private boolean success;
	private String messsage;
	
	
	//******************************* construct
	
	public AliSmsClient(String phone, String templateCode) {
		this.phone = phone;
		this.templateCode = templateCode;
	}
	
	public boolean send() {
		
		if (phone == null || "".equals(phone)) {
			return this.fail("请获取手机号");
		}
		if (templateCode == null || "".equals(templateCode)) {
			return this.fail("请获取短信模板");
		}
		
		templateContentProcessor();
		
		if(smsTest){
			return this.success();
		}
		
//		if (templateParam == null || "".equals(templateParam)) {
//			return smsRet.fail("请获取短信内容");
//		}
		
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", DEFAULT_CONNENT_TIMEOUT);
		System.setProperty("sun.net.client.defaultReadTimeout", DEFAULT_READ_TIMEOUT);
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SMS_ACCESS_KEY_ID, SMS_ACCESS_KEY_SECRET);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e) {
			e.printStackTrace();
			logErr(e.getErrMsg());
			return this.fail(e.getErrMsg());
		}
		
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);

		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SMS_SIGN_NAME);

		request.setTemplateCode(templateCode);
		
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(convertToJsonString(this.templateParamsMap));
//		request.setTemplateParam("{\"time\":\"2017年11月14号\",\"account\":\"大表哥\",\"amount\":\"1314.00\"}");

		// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return this.success();
			} else {
				logErr(sendSmsResponse.getMessage());
				return this.fail(sendSmsResponse.getMessage());
			}
		} catch (ServerException e) {
			e.printStackTrace();
			logErr(e.getErrMsg());
			return this.fail("短信发送失败");
		} catch (ClientException e) {
			e.printStackTrace();
			logErr(e.getErrMsg());
			return this.fail("短信发送失败");
		}
	}
	
	/**
	 * 包装成json形式数据
	 * @param map
	 * @return
	 */
	private String convertToJsonString(Map<String, String> templateParamsMap){
		String templateParam = null;
		if(null == templateParamsMap || templateParamsMap.isEmpty()){
			return templateParam;
		}
		
		Set<String> keys = templateParamsMap.keySet();
		templateParam = "";
		for(String key : keys){
			String param =  "\"" + key + "\":\"" + templateParamsMap.get(key) + "\"";
			templateParam += "".equals(templateParam) ? param : ("," + param);
			
			String keyParam = "${" + key + "}";
			templateContent.replace(keyParam, templateParamsMap.get(key));
			
		}
		templateParam = "{" + templateParam + "}";
		return templateParam;
	}
	
	/**
	 * 将模版短信结合实际参数处理后输出
	 */
	private void templateContentProcessor(){
		if(null == templateParamsMap || templateParamsMap.isEmpty()
				|| null == templateContent || "".equals(templateContent)){
			return;
		}
		
		String template = templateContent;
		
		Set<String> keys = templateParamsMap.keySet();
		for(String key : keys){
			String keyParam = "${" + key + "}";
			template = template.replace(keyParam, templateParamsMap.get(key));
			
		}
		smsContent = template;
		return;
	}
	
	private void logErr(String err){
		System.err.println("[" + this.getClass().getName() + "]: send sms fail\n---error message---: " + err);
	}
	
	public static void main(String[] args) {
//		String templateParam = "{\"time\":\"2017年11月14号\",\"account\":\"大表哥\",\"amount\":\"1314.00\"}";
//		AliSmsRet smsRet = new SmsClient().send("15355863672", "SMS_110265036", templateParam);
//		System.out.println(smsRet.isSuccess() + smsRet.getMesssage());
		
		Map<String, String>  map = new HashMap<String, String>();
		map.put("time", DateUtil.formatFullDate(new Date()));
		map.put("account", "大表哥");
		map.put("amount", "1314.00");
		AliSmsClient client = new AliSmsClient("17681839520", "SMS_110370049");
		client.setTemplateParamsMap(map);
		boolean isSuccess = client.send();
		System.out.println(isSuccess + client.getMesssage());
		
//		new AliSmsClient().logErr("12");
	}
	
	/**
	 * 例子，调用打印例子
	 */
	public static void forExample(){
		System.out.println("Map<String, String>  map = new HashMap<String, String>();\n" +
							"map.put(\"time\", DateUtil.formatFullDate(new Date()));\n" +
							"map.put(\"account\", \"大表哥\");\n" +
							"map.put(\"amount\", \"1314.00\");\n" +
							"AliSmsClient client = new AliSmsClient(\"13456949488\", \"SMS_110370049\");\n" +
							"client.setTemplateParamsMap(map);\n" +
							"boolean isSuccess = client.send();\n" +
							"System.out.println(isSuccess + client.getMesssage());");
	}

	public String getSmsContent() {
		if(null == smsContent || "".equals(smsContent)){
			logErr("****无法获取短信内容，请检查是否传入短信模版");
		}
		return smsContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public Map<String, String> getTemplateParamsMap() {
		return templateParamsMap;
	}

	public void setTemplateParamsMap(Map<String, String> templateParamsMap) {
		this.templateParamsMap = templateParamsMap;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMesssage() {
		return messsage;
	}
	
	private boolean fail(String msg){
		this.success = false;
		this.messsage = msg;
		return this.success;
	}
	
	private boolean success(){
		this.success = true;
		return this.success;
	}

	public boolean isSmsTest() {
		return smsTest;
	}

	public void setSmsTest(boolean smsTest) {
		this.smsTest = smsTest;
	}

}
