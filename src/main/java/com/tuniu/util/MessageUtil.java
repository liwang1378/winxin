package com.tuniu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.tuniu.pojo.Image;
import com.tuniu.pojo.ImageMessage;
import com.tuniu.pojo.Music;
import com.tuniu.pojo.MusicMessage;
import com.tuniu.pojo.News;
import com.tuniu.pojo.NewsMessage;
import com.tuniu.pojo.TextMessage;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="click";
	public static final String MESSAGE_VIEW="view";
	public static final String MESSAGE_MUSIC="music";

	/**
	 * xml转为map
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		InputStream is = req.getInputStream();
		Document document = reader.read(is);
		Element rootElement = document.getRootElement();
		List<Element> list = rootElement.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		is.close();
		return map;
	}
	
	/**
	 * 将文本消息对象转为xml
	 * @param txt
	 * @return
	 */
	public static String txtToXml(TextMessage txt){
		XStream xStream = new XStream();
		xStream.alias("xml", txt.getClass());
		return xStream.toXML(txt);
	}
	
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注,请按照菜单提示进行操作:\n\n");
		sb.append("0、扫码关注 \n");
		sb.append("1、个人介绍 - 李旺\n");
		sb.append("2、工作履历 - 2002-至今\n");
		sb.append("3、个人作品\n");
		sb.append("4、项目经验 \n");
		sb.append("5、我的照片 \n");
		sb.append("6、自我评价 \n");
		sb.append("7、音乐欣赏 \n");
		sb.append("回复?调出此菜单");
		return sb.toString();
	}
	
	public static String initText(String toUserName, String fromUserName, String content){
		TextMessage txt = new TextMessage();
		txt.setFromUserName(toUserName);
		txt.setToUserName(fromUserName);
		txt.setMsgType(MESSAGE_TEXT);
		txt.setCreateTime(new Date().getTime());
		txt.setContent(content);
		return txtToXml(txt);
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("姓名:李旺,男,1980-11-13 \n手机:15007926045\n");
		sb.append("2002年毕业于海军工程大学,计算机科学与技术 本科\n");
		sb.append("家庭地址:九江市浔阳区滨江东路45号\n");
		sb.append("求职愿景:九江及周边本地正规企业,交纳五险一金,周末双休,希望有稳定长远的发展!\n");
		return sb.toString();
	}
	
	public static String introMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("◆　勤奋好学，积极向上，较好的心理素质，能承受较大的工作压力\n");
		sb.append("◆　对工作认真负责，注重团队合作，有强烈的进取意识及责任感\n");
		sb.append("◆　从事多年IT软件、通讯行业类的工作岗位，积累了丰富的项目开发/实施/测试/运维经验\n");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("●　(2016.5 - 2017.6) 南京途牛科技有限公司 J2EE软件工程师\n");
		sb.append("●　(2015.9 - 2016.4) 中兴网信股份有限公司 软件工程师\n");
		sb.append("●　(2014.3 - 2015.7) 南京博迅嘉德科技公司 软件工程师\n");
		sb.append("●　(2012.3 - 2013.12) 南京冠邦科技有限公司  PHP程序员\n");
		sb.append("●　(2008.9 - 2011.12) (上海)中兴通讯公司 无线通信工程师\n");
		sb.append("●　(2002.7 - 2008.9) 南京财经大学 信息技术专员\n");
		return sb.toString();
	}
	
	//将图文消息对象转换为xml
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", News.class);
		return xStream.toXML(newsMessage);
	}
	
	public static String initSelfMessage(String toUserName, String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news=new News();
		news.setTitle("我的个人介绍");
		StringBuffer sb = new StringBuffer();
		sb.append("姓名:李旺,男,1980-11-13\n手机:15007926045\n");
		sb.append("2002年毕业于海军工程大学\n专业:计算机科学与技术 本科\n");
		sb.append("家庭地址:九江市浔阳区滨江东路45号\n");
		sb.append("求职愿景:九江及周边本地正规企业,交纳五险一金,周末双休,希望有稳定长远的发展!\n");
		news.setDescription(sb.toString());
		news.setPicUrl("http://ibook8.imwork.net/weixin/images/liwang.png");
		news.setUrl("http://imooc.iego.cn/weixin/images/resume_liwang_15007926045.pdf");
		newsList.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	public static String initNewsMessage(String toUserName, String fromUserName){
		String message = null;
		List<News> newsList = new ArrayList<>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news=new News();
		news.setTitle("我的作品");
		news.setDescription("Jquery+springmvc+mybatis+shiro基础架框,实现细粒度权限访问控制,基础业务的增删改查操作,OA审批工作流,在线文档阅读,文件管理等功能.");
		news.setPicUrl("http://ibook8.imwork.net/weixin/images/boss.png");
		news.setUrl("http://imooc.iego.cn/baseFrame");
		newsList.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	public static String initMultiNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<News> newsList = new ArrayList<>();
		NewsMessage newsMessage=new NewsMessage();
		
		News news1=new News();
		news1.setTitle("项目一");
		news1.setDescription("80che.com - 帮您车网站");
		news1.setPicUrl("http://ibook8.imwork.net/weixin/images/80che.png");
		news1.setUrl("http://www.80che.com/");
		newsList.add(news1);
		News news2=new News();
		news2.setTitle("项目二");
		news2.setDescription("http://b.tuniu.com/ - 途牛企业频道");
		news2.setPicUrl("http://ibook8.imwork.net/weixin/images/tuniu.png");
		news2.setUrl("http://b.tuniu.com/");
		newsList.add(news2);
		News news3=new News();
		news3.setTitle("项目三");
		news3.setPicUrl("http://ibook8.imwork.net/weixin/images/file.png");
		news1.setUrl("http://imooc.iego.cn/baseFrame/");
		newsList.add(news3);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message = newsMessageToXml(newsMessage);
		return message;
		
	}
	
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	public static String initImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		image.setMediaId("qK3FJTzajrT-mnj2fXpEvE87jaaWNDTpL9SDgb9qERy5Pq1ZmJVGEEocMZVNT2Ni");
		ImageMessage imageMessage=new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	public static String initCodeImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		image.setMediaId("mRge8U9LaTsA7gwNlkyUAPmnqlqr_boisJddH4CJsvMm0mZd6zUVJ7RAOkczcLJn");
		ImageMessage imageMessage=new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", musicMessage.getClass());
		return xStream.toXML(musicMessage);
	}
	
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();
		music.setThumbMediaId("bql-y5Lb9-LMoltVUwtWXFRcylmCRjk79j3u7Y3EFvwAI-EwQYhq7ulOCBS5paKW");
		music.setTitle("友情岁月");
		music.setDescription("郑伊健 - 古惑仔系列之战无不胜,陈浩南,山鸡,小哑巴,包皮");
		music.setMusicUrl("http://ibook8.imwork.net/weixin/media/mp3/year.mp3");
		music.setHQMusicUrl("http://ibook8.imwork.net/weixin/media/mp3/year.mp3");
		MusicMessage musicMessage=new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
}
