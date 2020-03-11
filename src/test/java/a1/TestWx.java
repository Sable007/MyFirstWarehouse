package a1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.crypto.Data;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.baidu.aip.ocr.AipOcr;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV2Request;
import com.baidubce.services.sms.model.SendMessageV2Response;
import com.thoughtworks.xstream.XStream;


import button.Button;
import button.ClickButton;
import button.ScancodePushButtton;
import button.SubButton;
import button.ViewButton;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.BaiduService;
import service.WxService;
import service.userService;
import util.HttpUtil;
import util.Util;
import util.WeixinUtil;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import com.hqyj.entity.WxUser;
import com.hqyj.entity.WxUserExample;
import com.hqyj.mapper.WxUserMapper;

import org.json.JSONException;
import java.io.IOException;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestWx {
	 //����APPID/AK/SK
    public static final String APP_ID = "17951845";
    public static final String API_KEY = "FnpTD8PPyFG4G3jpXv4Xk1m3";
    public static final String SECRET_KEY = "RcAj8EM3qO6CMlgIsNDtnUuWEe0mKFnp";
	
	@Test
	public void testPic(){
		 // ��ʼ��һ��AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
       // client.setHttpProxy("proxy_host", proxy_port);  // ����http����
       // client.setSocketProxy("proxy_host", proxy_port);  // ����socket����

        // ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
        // Ҳ����ֱ��ͨ��jvm�����������ô˻�������
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // ���ýӿ�
        String path = "C:/Users/j_c/Pictures/1.png";
        org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));
	}
	
	
	
	
	
	@Test
	public void testButton(){
		//�˵�
		Button b=new Button();
		//һ���˵�
		b.getButton().add(new ClickButton("һ�����", "1"));
		b.getButton().add(new ViewButton("һ����ת", "192.168.137.224/gpio/0"));
		//�����˵�
		SubButton subButton = new SubButton("�Ӳ˵�");
		//�������������˵�
		subButton.getSub_button().add(new ScancodePushButtton("ɨ��", "31"));
		subButton.getSub_button().add(new ClickButton("���", "12"));
		subButton.getSub_button().add(new ViewButton("��������", "192.168.137.224/gpio/1"));
		
		b.getButton().add(subButton);
		//ת��Ϊjson
		JSONObject fObj= JSONObject.fromObject(b);
		System.out.println(fObj.toString());
	}
	@Test
	public void testToken(){
		System.out.println(WxService.getAccessToken());
	}
	@Test
	public void testMsg(){
//		Map<String,String> map=new HashMap<>();
//		map.put("ToUserName", "jc");
//		map.put("FromUserName", "from");
//		
//		map.put("MsgType","type" );
//		TextMessage tm=new TextMessage(map,"666");
//		
//		XStream stream=new XStream();
//		stream.processAnnotations(TextMessage.class);
//		String xml=stream.toXML(tm);
//		System.out.println(xml);
		String a="��Ҫ�ÿ��";
		String b="��Ҫ��ȡ��";
		if(b.contains("���")){
			System.out.println("�ɹ�");
		}
		else {
			System.out.println("ʧ��");
		}
	}
	
	
	@Test
	public void testUpload(){
		String file="C:/Users/j_c/Pictures/863030221.jpg";
		String rest = WxService.upload(file, "image");
		System.out.println(rest);
		
	}
	
	//�ϴ���ʱ�ز�
	@Test
	public void uploadFileToWxmpMedia() {
		
		//"media_id":"lSVQnAZNVQsdVElx7WzreLa3nCe37YZi9kbIyfZOQ5dcN7WZJ-5T853onbr9hk4G"
		
		String access_token = "28_gYYHWU7KoQmsoP_PLCNtEMFqKq15tuswIs_3RuzxItJmnLUmR3i1G3iKvRT7iFKERBMmL_YTY6aeEOHsO_H8DCkObxalwzd1swA5XMz6kZfho6ZaJ2WzjzJcaqoFTEeAGAOCN";
		URL url = Thread.currentThread().getContextClassLoader().getResource("images/863030221.jpg");
		File file = new File(url.getFile());
		
		// ��װpost����
		HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+access_token+"&type=image");
		
		FileBody fileBody = new FileBody(file);
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.addPart("media", fileBody)
				.build();
		
		httpPost.setEntity(reqEntity);
		
		try {
			CloseableHttpResponse httpResponse = HttpClients.createDefault().execute(httpPost);
			InputStream content = httpResponse.getEntity().getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(content, "utf-8"));
			//InputStream is2=openConnection.getInputStream();
			String inputLine;
	        StringBuffer result = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            result.append(inputLine);
	            result.append("\r\n");
	        }
	        in.close();
			//String string = IOUtils.toString(content, "utf-8");
			System.out.println(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// ����Ӧ�� SDK AppID
	int appid = 1400316004; // SDK AppID ��1400��ͷ  �ҵ�:1400316004
	// ����Ӧ�� SDK AppKey
	String appkey = "384c5644dab20c198e01d57b87c2351d";
	// ��Ҫ���Ͷ��ŵ��ֻ�����
	String[] phoneNumbers = {"17585466160"};
	// ����ģ�� ID����Ҫ�ڶ���Ӧ��������
	int templateId = 535485; // NOTE: �����ģ�� ID`7839`ֻ��ʾ������ʵ��ģ�� ID ��Ҫ�ڶ��ſ���̨������ �ҵ�:535485
	// ǩ��
	String smsSign = "����ȡ��"; // NOTE: ǩ������ʹ�õ���`ǩ������`��������`ǩ��ID`�������ǩ��"��Ѷ��"ֻ��ʾ������ʵ��ǩ����Ҫ�ڶ��ſ���̨����
	private List<WxUser> list;
	private String sendPost;


	
	@Test
	public void smsTest(){
		
		try {
			Integer a=(int) (Math.random()*1000000);
			String b= String.valueOf(a);
			if(a<1000000){
				b= String.valueOf(Math.random()*1000000);
			}
			String[] params = {b};
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
					templateId, params, smsSign, "", "");
			System.out.println(result);
			
		} catch (HTTPException e) {
		  // HTTP ��Ӧ�����
		  e.printStackTrace();
		} catch (JSONException e) {
		  // JSON ��������
		  e.printStackTrace();
		} catch (IOException e) {
		  // ���� IO ����
		  e.printStackTrace();
		}
	}
	
	@Test
	public void Mybatis() throws IOException{
			WxUser user=new WxUser();
	        
//	        user.setWxOpenId("NUASHduqwe");
	        user.setWxPhone("911");
	//        userService.addUser(user);
	        
        InputStream in = org.apache.ibatis.io.Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=builder.build(in);
        SqlSession session=factory.openSession();
//        WxUserMapper mapper=session.getMapper(WxUserMapper.class);
        WxUserMapper mapper= session.getMapper(WxUserMapper.class);
        
        WxUserExample example =new WxUserExample();
        
        example.or().andWxOpenIdEqualTo("o2A0C6uYLAXw3bA_TFBBdOcGZm44");
        list= mapper.selectByExample(example);
        for(WxUser u:list){
        	System.out.println("==================");
        	System.out.println(u.getId());
        	System.out.println(u.getWxOpenId());
        	System.out.println(u.getWxPhone());
        	System.out.println("==================");
        	
        }
        
//        mapper.updateByExample(user, example);
        mapper.updateByExampleSelective(user, example);
        session.commit();
        session.close();
	}
	//756ac7ed8ccd0fa36a4fdae5419a4958464f9247
	//756ac7ed8ccd0fa36a4fdae5419a4958464f9247
	@Test
	public void JSSDK() throws Exception{
		
//		String timestamp=new SimpleDateFormat("yyyyMMdd").format(new Date());
//		String noncestr=UUID.randomUUID().toString();
		String timestamp="20200223";
		String noncestr="jcsnb";
		String url="http://jc02.free.idcfengye.com/a1/";
		
	 String jssdKsignature= WxService.JSSDKsignature(WxService.getJSTicket(),noncestr,timestamp , url);
	 
	 System.out.println(jssdKsignature);
	}
	
	
	
	@Test
	public void imgTest(){
		//https://api.weixin.qq.com/cgi-bin/media/get?access_token=30_UcxGb0St7Y0s1grbiCacAYhTc-tpn1d_EZKTGI__cbghJS2tO36y_ORnjdYa68C0YCWKjufNgqH40WsFQM81HE7uUTy8_4B_ZA4lUz_iDrEdlKwjYWIXkZ_7MmcBUOcACAEXR&media_id=yBoGt-kHVzEuTstl4MLgDFaAJU0zAz-sCo4CrUTO8Cq8JxXD4Lm87_4ELr0jQW0r
		//30_76Q-R0x40CgpAIFZiFmUajNqpIea0gcwLV3rhBiPFilyp0kp7EDRjadfqxG5cvMqxE4ZZ2bJ5vgUqtB5Nf2tibwQiZp0SI35NSiPzzoS8c3eOAKJjqkZGSd4umqUv3ja84qJ7YeKVVo54swZBHYgAAAKQH
		String mediaId="iBln9mi8_VElYgQ467JOKwwL-a_GPwIwuHdnLuJYIwtwxCUUD1PE4eHH0iLHCfDA";
		String url="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		url.replace("MEDIA_ID",mediaId).replace("ACCESS_TOKEN", "");

		try {
//			download(url, "C:\\Users\\j_c\\Desktop\\img\\a.jpg");
			WeixinUtil.getFile(url, "C:\\Users\\j_c\\Desktop\\img\\"+mediaId);
			
			//WeixinUtil.fetchTmpFile(mediaId, "image");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}
	public static void download(String _url,String path) throws Exception{
		try{  
		       URL url = new URL(_url);  
		       HttpURLConnection con = (HttpURLConnection) url.openConnection();  
		       con.setRequestMethod("GET"); // ������get��ʽ����

		       DataInputStream dataInputStream = new DataInputStream(url.openStream());
		       
	            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();
	 
	            byte[] buffer = new byte[1024];
	            int length;
	 
	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
 
		} catch (IOException e) {
			 e.printStackTrace();
		}        
		}
	
	@Test
	public void getnum(){
		//"C:\\Users\\j_c\\Desktop\\img\\IMG_20200224_161049_1.jpg";
//		String ret=BaiduService.getnumbers("C:\\Users\\j_c\\Desktop\\img\\1582533802051.jpg");
//		JSONObject job=JSONObject.fromObject(ret);
//		System.out.println(ret);
		String s=BaiduService.getnumbersByIocr("C:\\Users\\j_c\\Desktop\\img\\MQ{JG[JXS`JP)L0@GCCWAHW.png");
		//String s="{'data':{'ret':[{'probability':{'average':0.999239,'min':1.297976,'variance':0.0000010},'location':{'height':109,'left':751,'top':1741,'width':542},'word_name':'�ջ���','word':'13608507486'}],'templateSign':'121cad8a2f3b263f930e65b068e7bd84','templateName':'u=205917850,4077058899&fm=26&gp=0','scores':1.0,'isStructured':true,'logId':'158256002880502','templateMatchDegree':0.9327825113320822,'clockwiseAngle':0.0},'error_code':0,'error_msg':'','log_id':'158256002880502'}";
		JSONObject job=JSONObject.fromObject(s);
		
		/***
		 * {"data":
		 * 		{"ret":	
		 * 			[{"probability":
		 * 				{"average":0.999239,
		 * 				"min":1.297976,
		 * 				"variance":0.0000010},
		 * 			"location":
		 * 				{"height":109,
		 * 				"left":751,"top":1741,
		 * 				"width":542},
		 * 			"word_name":"�ջ���",
		 * 			"word":"13608507486"}],
		 * 		"templateSign":"121cad8a2f3b263f930e65b068e7bd84",
		 * 		"templateName":"u=205917850,4077058899&fm=26&gp=0",
		 * 		"scores":1.0,
		 * 		"isStructured":true,
		 * 		"logId":"158255862228276",
		 * 		"templateMatchDegree":0.9327825113320822,
		 * 		"clockwiseAngle":0.0},
		 * 	"error_code":0,
		 * 	"error_msg":"",
		 * 	"log_id":"158255862228276"}
		 */
		
		 System.out.println(JSONObject.fromObject(job.getJSONObject("data").getJSONArray("ret").get(0)).getString("word"));
		 
	
		
		
	}
}
