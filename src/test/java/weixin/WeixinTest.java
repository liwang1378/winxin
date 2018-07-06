package weixin;

import java.io.IOException;

import org.junit.Test;

import com.tuniu.pojo.AccessToken;
import com.tuniu.util.UploadUtil;
import com.tuniu.util.WeixinUtil;

public class WeixinTest {
	
	@Test
	public void testGetAccessToken(){
		AccessToken token=WeixinUtil.getAccessToken();
		String accessToken=token.getAccess_token();
		System.out.println("票据:"+token.getAccess_token());
		System.out.println("有效时间:"+token.getExpires_in());
		
		String path="D:\\indigo\\weixin\\WebContent\\images\\user.png";
		try {
			String mediaId=UploadUtil.upload(path, accessToken, "image");
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetMusic(){
		AccessToken token=WeixinUtil.getAccessToken();
		String accessToken=token.getAccess_token();
		System.out.println("票据:"+token.getAccess_token());
		System.out.println("有效时间:"+token.getExpires_in());
		
		String path="D:\\indigo\\weixin\\WebContent\\images\\Winter2.jpg";
		try {
			String mediaId=UploadUtil.upload(path, accessToken, "thumb");
			System.out.println(mediaId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
