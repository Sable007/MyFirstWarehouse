package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baidu.aip.ocr.AipOcr;
import com.thoughtworks.xstream.XStream;

import entity.AccessToken;
import entity.Article;
import entity.FatherMessage;
import entity.ImageMessage;
import entity.JsapiTicket;
import entity.MusicMessage;
import entity.NewsMessage;
import entity.TextMessage;
import entity.VideoMessage;
import entity.VoiceMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.HttpUtil;
import util.Util;




public class WxService {
	private static final String TOKEN ="sable";
	public static final String URL = "https://api.jisuapi.com/iqa/query";
	public static String question ;// 问题(utf-8)
	public static final String APPKEY = "82815192bbd82b54";// 你的appkey
	
	
	private static final String APPID="wx9d2575f382dd1ee8";
	private static final String APPSECRET="daea75f30d6edbd32dd3d5add0b60483";
	private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	//用于储存AccessToken
	private static AccessToken at;
	
	private static void Token(){
		String url = GET_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		String tokenStr = Util.getToken(url);
		JSONObject fObj = JSONObject.fromObject(tokenStr);
		String token = fObj.getString("access_token");
		String expiresIn = fObj.getString("expires_in");
		at=new AccessToken(token, expiresIn);
		System.out.println(tokenStr);
	}
	//向外暴露获取Token的方法
	public static String getAccessToken(){
		if(at==null||at.isExpired()){
			Token();
		}
		return at.getAccessToken();
	}
	//用于储存JsapiTicket
	private static JsapiTicket jt;
	
	public static String getJSTicket() throws Exception{
		if(jt==null||jt.isExpired()){
			String JSTicketstr = Util.JSTicket();
			JSONObject fObj = JSONObject.fromObject(JSTicketstr);
			String ticket = fObj.getString("ticket");
			String expiresIn = fObj.getString("expires_in");
			jt=new JsapiTicket(ticket, expiresIn);
		}
		return jt.getTicket();
	}
	public static boolean check(String timestamp, String nonce, String signature) {
		 // 1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] strs=new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		 // 2）将三个参数字符串拼接成一个字符串进行sha1加密
		String str=strs[0]+strs[1]+strs[2];
		String mysig=sha1(str);
		System.out.println(mysig);
		System.out.println(signature);
		 // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return mysig.equalsIgnoreCase(signature);
	}
	
	public static String JSSDKsignature(String jsapi_ticket,String noncestr,String timestamp,String url){
		String[] strs=new String[]{jsapi_ticket,noncestr,timestamp,url};
		Arrays.sort(strs);
		
		String str="jsapi_ticket="+strs[1]+"&noncestr="+strs[3]+"&timestamp="+strs[0]+"&url="+strs[2];
		System.out.println(str);
		String mysig=sha1(str);
		
		return mysig;
		
	}
	/**
	 * 进行sha1加密
	 * @param str
	 * @return
	 */
		private static String sha1(String src) {
			try {
				//获取一个加密对象
				MessageDigest md= MessageDigest.getInstance("sha1");
				//加密操作 
				byte[] digest=md.digest(src.getBytes());
				char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
				StringBuilder sb=new StringBuilder();
				//处理加密结果
				for(byte b:digest){
					sb.append(chars[(b>>4)&15]);
					sb.append(chars[b&15]);
				}
				return sb.toString();
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return null;
		}
		
//		解析XML数据包
	public static Map<String, String> ParseRequest(InputStream is) {
		Map<String,String>map=new HashMap<>();
		SAXReader reader=new SAXReader();
		try {
			//读取输入流获取文档对象
			Document read=reader.read(is);
			//根据文档对象获取根节点
			Element root=read.getRootElement();
			//获取根节点的所有子节点
			List<Element> elements=root.elements();
			for(Element e:elements ){
				map.put(e.getName(), e.getStringValue());
			}
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return map;
		
	}
	//用于处理所有消息的事件和回复
	//返回的是一个XML数据包
	public static String getResponse(Map<String, String> requestMap) throws Exception {
		FatherMessage fm = null;
		switch (requestMap.get("MsgType")) {
		case "text":
			//调用处理文本消息的方法返回一个文本对象
			fm=dealTextMessage(requestMap);
			break;
		case "image":
			fm=dealImageMessage(requestMap);	
			break;
		case "voice":
				
			break;
		case "video":
			
			break;
		case "shortvideo":
			
			break;
		case "location":
	
			break;
		case "link":
	
			break;
		case "event":
			fm=dealEvent(requestMap);
			
			break;
		default:
			break;
		}
		
		//调用把消息对象处理成xml数据包的方法
		if(fm!=null){
			url=null;
			return beanToXml(fm);
			
		}
		return null;
	}
	
	
	 //设置APPID/AK/SK
    public static final String APP_ID = "17951845";
    public static final String API_KEY = "FnpTD8PPyFG4G3jpXv4Xk1m3";
    public static final String SECRET_KEY = "RcAj8EM3qO6CMlgIsNDtnUuWEe0mKFnp";
	//处理图像消息(识别图像)
	private static FatherMessage dealImageMessage(Map<String, String> requestMap) {
		 // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        String path =requestMap.get("PicUrl");
        // org.json.JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        org.json.JSONObject res = client.basicGeneralUrl(path, new HashMap<String,String>());
        String json = res.toString();
        JSONObject fromObject = JSONObject.fromObject(json);
        JSONArray jsonArray = fromObject.getJSONArray("words_result");
        Iterator<JSONObject> it = jsonArray.iterator();
        StringBuilder sb=new StringBuilder();
        while(it.hasNext()){
        	JSONObject next = it.next();
        	sb.append(next.get("words"));
        }
        
		return new TextMessage(requestMap, sb.toString());
	}
	//处理事件推送
	private static FatherMessage dealEvent(Map<String, String> requestMap) {
		String event=requestMap.get("Event");
		switch (event) {
		case "CLICK":
			return dealClick(requestMap);
		
			
		case "VIEW":
			return dealView(requestMap);
		
		default:
			break;
		}

		return null;
	}
	
	static String url;
	//处理CLICK类型的菜单
	private static FatherMessage dealClick(Map<String, String> requestMap) {
		
		String key=requestMap.get("EventKey");
		switch (key) {
		case "0":
			//处理第一个一级菜单
			if(url==null){
				url="http://192.168.137.198/gpio/0";
				 Util.post(url, "没用");
				 
			}
			return new TextMessage(requestMap,"打开成功");
		case "1":
			//处理第一个二级菜单
			if(url==null){
			 url="http://192.168.137.198/gpio/1";
			Util.post(url, "没用");
			
			}
			return new TextMessage(requestMap,"关闭成功");
			
			
		default:
			
			break;
		}
		return null;

	}
	//处理VIEW类型的菜单
	private static FatherMessage dealView(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//把消息对象处理成xml数据包
	private static String beanToXml(FatherMessage fm) {
		XStream stream=new XStream();
		stream.processAnnotations(TextMessage.class);
		stream.processAnnotations(ImageMessage.class);
		stream.processAnnotations(MusicMessage.class);
		stream.processAnnotations(NewsMessage.class);
		stream.processAnnotations(VideoMessage.class);
		stream.processAnnotations(VoiceMessage.class);
		
		String xml=stream.toXML(fm);
		
		return xml;
	}
	//处理文本消息
	private static FatherMessage dealTextMessage(Map<String, String> requestMap) throws Exception {
		String mag=requestMap.get("Content");
		
		if(mag.contains("快递")||mag.contains("取货")){
			List<Article> articles = new ArrayList<Article>();
			articles.add(new Article("扫码一键取货", "取货地址XX-XX-XX", "http://mmbiz.qpic.cn/mmbiz_jpg/txpRE3GGZGAZm8lRUdiaqatgbdeuYYpu4ZeuiaOPSpphB6bmoKm4Ee5HoDzvzlL6W4jRUsYibz074Uw1kd1ZIsNUg/0", "http://www.baidu.com"));
			NewsMessage nm= new NewsMessage(requestMap, articles);
			
			
			return nm;
		}
		String s=chat(mag);
		System.out.println("==================================");
		System.out.println(s);
		TextMessage tm=new TextMessage(requestMap,s);
		return tm;
	}
	
		//回答用户发送的文本消息(调用图灵机器人)
	private static String chat(String mag) throws Exception {
		// TODO Auto-generated method stub
		
	    // 问题(utf-8)
		try {
	    	question = mag;
	        String result = null;
	        String url = URL + "?appkey=" + APPKEY + "&question=" + URLEncoder.encode(question, "utf-8");
	            result = HttpUtil.sendGet(url, "utf-8");
	            JSONObject json = JSONObject.fromObject(result);
	            
	            if (json.getInt("status") != 0) {
	                System.out.println(json.getString("msg"));
	               
	            } else {
	                JSONObject resultarr = json.optJSONObject("result");
	                String type = resultarr.getString("type");
	                String content = resultarr.getString("content");
	                String relquestion = resultarr.getString("relquestion");
	                System.out.println(type + " " + content + " " + relquestion);
	                return content;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return "请重新输入";
	}
	
	//上传临时素材
	
	public static String upload(String path,String type){
		File file=new File(path);
		//地址
		String url ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		url=url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
		
		  try {
			//创建连接对象
			URL urlObj=new URL(url);
			//强转为连接案列
			HttpsURLConnection openConnection = (HttpsURLConnection) urlObj.openConnection();
			//设置连接的信息
			openConnection.setDoInput(true);//允许写入
			openConnection.setDoOutput(true);//允许写出
			openConnection.setUseCaches(false);//不允许缓存
			//设置请求头信息
			openConnection.setRequestProperty("Connection", "Keep-Alive");
			openConnection.setRequestProperty("Charset", "utf8");
			//数据边界
			String boundary="-----"+System.currentTimeMillis();
			openConnection.setRequestProperty("Content-Type","multipart/from-databoundary="+boundary);
			//获取输出流
			DataOutputStream  out=new DataOutputStream(openConnection.getOutputStream());
			//创建文件输入流
			InputStream is=new FileInputStream(file);
			//写数据
			//头部信息
			StringBuilder sb=new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			out.write(sb.toString().getBytes());
			
			//文件内容
			byte[] b=new byte[1024];
			int len;
			while((len=is.read(b))!=-1){
				out.write(b, 0, len);
			}
			//尾部信息
			String foot="\r\n--"+boundary+"--\r\n";
			out.write(foot.getBytes());
			out.flush();
			out.close();
			//读取数据
			BufferedReader in = new BufferedReader(new InputStreamReader(openConnection.getInputStream(), "utf-8"));
			//InputStream is2=openConnection.getInputStream();
			String inputLine;
	        StringBuffer result = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            result.append(inputLine);
	            result.append("\r\n");
	        }
	        in.close();
	        openConnection.disconnect();
			return result.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		  
		
		return null;
		
	}

}
