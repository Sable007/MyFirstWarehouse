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
		
		//�˵�
		Button b=new Button();
		//һ���˵�
//		b.getButton().add(new ClickButton("��", "0"));
//		b.getButton().add(new ClickButton("��", "1"));
		//�����˵�
		SubButton subButton = new SubButton("ȡ��");
		//�������������˵�
		subButton.getSub_button().add(new ScancodePushButtton("ɨ��ȡ��", "31"));
		subButton.getSub_button().add(new ViewButton("�������", "https://kf.qq.com/product/weixinmp.html"));
		
		b.getButton().add(subButton);
		//ת��Ϊjson
		JSONObject fObj= JSONObject.fromObject(b);
		//׼��url
		String url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
//		String url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
		url=url.replace("ACCESS_TOKEN", WxService.getAccessToken());
		//��������
		String post = Util.post(url, fObj.toString());
		System.out.println(post);
	}
	
}
