package com.tuniu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.tuniu.pojo.TextMessage;
import com.tuniu.util.CheckUtil;
import com.tuniu.util.MessageUtil;

public class WxServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//		timestamp	时间戳
//		nonce	随机数
//		echostr	随机字符串
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		System.out.println("签名："+signature+" , 时间戳："+timestamp+" , 随机数："+nonce+" , 响应字符串："+echostr);
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("0".equals(content)){
					message = MessageUtil.initCodeImageMessage(toUserName, fromUserName);
				}else if("1".equals(content)){
					//message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
					message = MessageUtil.initSelfMessage(toUserName, fromUserName);
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("3".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("4".equals(content)){
					message = MessageUtil.initMultiNewsMessage(toUserName, fromUserName);
				}else if("5".equals(content)){
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				}else if("6".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.introMenu());
				}else if("7".equals(content)){
					message = MessageUtil.initMusicMessage(toUserName, fromUserName);
				}else if("？".equals(content) || "?".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else{
//					TextMessage txt = new TextMessage();
//					txt.setFromUserName(toUserName);
//					txt.setToUserName(fromUserName);
//					txt.setMsgType(MessageUtil.MESSAGE_TEXT);
//					txt.setCreateTime(new Date().getTime());
//					txt.setContent("您发送的消息是:"+content);
//					message = MessageUtil.txtToXml(txt);
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

}
