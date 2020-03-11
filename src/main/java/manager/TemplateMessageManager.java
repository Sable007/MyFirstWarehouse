package manager;

import org.junit.Test;

import entity.Industry;
import net.sf.json.JSONObject;
import service.WxService;
import template.TemplateMessage;
import template.datas;
import template.vandc;
import util.Util;

public class TemplateMessageManager {
	
	@Test
	public void setIndustry(){
		String ACCESS_TOKEN=WxService.getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+ACCESS_TOKEN;
		JSONObject obj= JSONObject.fromObject(new Industry("14", "15"));
		String data=obj.toString();
		String result=Util.post(url, data);
		System.out.println(result);
	}
	
	@Test
	public void getIndustry(){
		String ACCESS_TOKEN=WxService.getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+ACCESS_TOKEN;
		String token = Util.getToken(url);
		System.out.println(token);
		
	}
	
	@Test
	public void postTemplateMessage(){
		String ACCESS_TOKEN=WxService.getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+ACCESS_TOKEN;
		//设置模板消息的内容
		datas dt=new datas(new vandc("您的快递已取走", "#173177"), new vandc("0x1234", "#173177"), new vandc("2019.12.6", "#173177"), new vandc("如不是本人操作请联系客服 : 10086", "#173177"));
		TemplateMessage Tm=new TemplateMessage("o2A0C6qTDDIZfXMIjv5WxTpYpExQ","sss9cq852gWYcVAsjYaoMcB4iRZWhVOAQakV1L8TTNs","http://baidu.com", null, dt);
		JSONObject obj=JSONObject.fromObject(Tm);                              
		String data=obj.toString();
		String token=Util.post(url, data);
		System.out.println(token);
	}
}
