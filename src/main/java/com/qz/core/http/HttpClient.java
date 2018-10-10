package com.qz.core.http;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author chance
 * @date 2017年11月21日 上午12:25:54
 */
public class HttpClient {
	
	private static Log log = LogFactory.getLog(HttpClient.class);

	public static String doPost(String url, String requestParameters){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type","application/json; charset=utf-8");  
		post.setHeader("Accept", "application/json");
		post.setEntity(new StringEntity(requestParameters, Charset.forName("UTF-8")));  

		String responseContent = null;
        
		try {
			
			log.info("**********post url:" + url);
			
			CloseableHttpResponse httpResponse = httpClient.execute(post);  
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();  
			
			log.info("**********post statusCode: " + statusCode);
			log.info("**********post requestParameters: " + requestParameters);
			responseContent = EntityUtils.toString(httpResponse.getEntity());
			if (statusCode == HttpStatus.SC_OK) {  
				return responseContent;
			}else {
				log.error("**********send failed: " + httpResponse.getStatusLine());
			}
			
		}  catch (IOException e) {
			log.error("**********send error: cause IOException" + e.getMessage());
		} catch (Exception e) {
			log.error("**********http error: " + e.getMessage());
			
		}
		return null;
		
	}
	
}
