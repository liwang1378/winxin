package com.tuniu.pojo;

public class BaseMessage {
//	ToUserName	开发者微信号
//	FromUserName	发送方帐号（一个OpenID）
//	CreateTime	消息创建时间 （整型）
//	MsgType	text
	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	
	
}
