package com.tuniu.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class UploadUtil {
	
	private static final String UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	public static String upload(String filePath,String accessToken,String type) throws IOException{
		File file=new File(filePath);
		if(!file.exists()||!file.isFile()){
			throw new IOException("文件不存在");
		}
		String url=UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URL url2 = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		//设置请求头信息
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		//设置边界
		String BOUNDARY="---------------------------" + System.currentTimeMillis();
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDARY);
		StringBuffer sb=new StringBuffer();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getName()+"\"\r\n");
		sb.append("Content-Type: application/octet-stream\r\n\r\n");
		byte[] head=sb.toString().getBytes("utf-8");
		//获得输出流
		OutputStream out=new DataOutputStream(conn.getOutputStream());
		out.write(head);
		//上传文件数据
		DataInputStream in=new DataInputStream(new FileInputStream(file));
		int bytes=0;
		byte[] buffer=new byte[1024];
		while((bytes=in.read(buffer))!= -1){
			out.write(buffer,0,bytes);
		}
		in.close();
		//结尾部分 - 定义最后数据分隔线
		byte[] foot=("\r\n--"+BOUNDARY+"--\r\n").getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();
		
		//获取响应结果数据
		StringBuffer buffer2=new StringBuffer();
		BufferedReader reader=null;
		String result=null;
		reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line=null;
		while((line=reader.readLine())!=null){
			buffer2.append(line);
		}
		if(result==null){
			result=buffer2.toString();
		}
		if(reader!=null){
			reader.close();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println(jsonObject);
		String typeName="media_id";
		if(!"image".equals(type)){
			typeName=type+"_media_id";
		}
		String mediaId=jsonObject.getString(typeName);
		return mediaId;
		
	}
}
