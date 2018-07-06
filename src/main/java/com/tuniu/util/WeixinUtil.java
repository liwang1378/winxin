package com.tuniu.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.tuniu.pojo.AccessToken;

public class WeixinUtil {
	
	private static final String APPID="wxa7720479c931c5aa";
	private static final String APPSECRET="d58e6c83c67ffeffb2622ccc170167f2";
	
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static JSONObject doGet(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		JSONObject jsonObject=null;
		try {
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result=EntityUtils.toString(entity,"UTF-8");
				jsonObject=JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doPost(String url,String param){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		JSONObject jsonObject=null;
		try {
			httpPost.setEntity(new StringEntity(param, "UTF-8"));
			HttpResponse response=httpClient.execute(httpPost);
			String result=EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public static AccessToken getAccessToken(){
		AccessToken token=new AccessToken();
		String url=ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject=doGet(url);
		if(jsonObject!=null){
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}
}
