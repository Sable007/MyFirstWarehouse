package util;

import button.Button;
import button.ClickButton;
import button.ScancodePushButtton;
import button.SubButton;
import button.ViewButton;
import net.sf.json.JSONObject;
import service.WxService;

public class CreateMenu {
	
	public static void main(String[] args) {
		
		//菜单
		Button b=new Button();
		//一级菜单
//		b.getButton().add(new ClickButton("开", "0"));
//		b.getButton().add(new ClickButton("关", "1"));
		//二级菜单
		SubButton subButton = new SubButton("取件");
		//包含三个二级菜单
		subButton.getSub_button().add(new ScancodePushButtton("扫码取件", "31"));
		subButton.getSub_button().add(new ViewButton("更多服务", "https://kf.qq.com/product/weixinmp.html"));
		
		b.getButton().add(subButton);
		//转化为json
		JSONObject fObj= JSONObject.fromObject(b);
		//准备url
		String url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
//		String url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
		url=url.replace("ACCESS_TOKEN", WxService.getAccessToken());
		//发送请求
		String post = Util.post(url, fObj.toString());
		System.out.println(post);
	}
	
}
