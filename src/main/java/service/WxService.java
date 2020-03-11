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
	public static String question ;// ����(utf-8)
	public static final String APPKEY = "82815192bbd82b54";// ���appkey
	
	
	private static final String APPID="wx9d2575f382dd1ee8";
	private static final String APPSECRET="daea75f30d6edbd32dd3d5add0b60483";
	private static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	//���ڴ���AccessToken
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
	//���Ⱪ¶��ȡToken�ķ���
	public static String getAccessToken(){
		if(at==null||at.isExpired()){
			Token();
		}
		return at.getAccessToken();
	}
	//���ڴ���JsapiTicket
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
		 // 1����token��timestamp��nonce�������������ֵ������� 
		String[] strs=new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(strs);
		 // 2�������������ַ���ƴ�ӳ�һ���ַ�������sha1����
		String str=strs[0]+strs[1]+strs[2];
		String mysig=sha1(str);
		System.out.println(mysig);
		System.out.println(signature);
		 // 3�������߻�ü��ܺ���ַ�������signature�Աȣ���ʶ��������Դ��΢��
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
	 * ����sha1����
	 * @param str
	 * @return
	 */
		private static String sha1(String src) {
			try {
				//��ȡһ�����ܶ���
				MessageDigest md= MessageDigest.getInstance("sha1");
				//���ܲ��� 
				byte[] digest=md.digest(src.getBytes());
				char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
				StringBuilder sb=new StringBuilder();
				//������ܽ��
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
		
//		����XML���ݰ�
	public static Map<String, String> ParseRequest(InputStream is) {
		Map<String,String>map=new HashMap<>();
		SAXReader reader=new SAXReader();
		try {
			//��ȡ��������ȡ�ĵ�����
			Document read=reader.read(is);
			//�����ĵ������ȡ���ڵ�
			Element root=read.getRootElement();
			//��ȡ���ڵ�������ӽڵ�
			List<Element> elements=root.elements();
			for(Element e:elements ){
				map.put(e.getName(), e.getStringValue());
			}
		} catch (DocumentException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return map;
		
	}
	//���ڴ���������Ϣ���¼��ͻظ�
	//���ص���һ��XML���ݰ�
	public static String getResponse(Map<String, String> requestMap) throws Exception {
		FatherMessage fm = null;
		switch (requestMap.get("MsgType")) {
		case "text":
			//���ô����ı���Ϣ�ķ�������һ���ı�����
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
		
		//���ð���Ϣ�������xml���ݰ��ķ���
		if(fm!=null){
			url=null;
			return beanToXml(fm);
			
		}
		return null;
	}
	
	
	 //����APPID/AK/SK
    public static final String APP_ID = "17951845";
    public static final String API_KEY = "FnpTD8PPyFG4G3jpXv4Xk1m3";
    public static final String SECRET_KEY = "RcAj8EM3qO6CMlgIsNDtnUuWEe0mKFnp";
	//����ͼ����Ϣ(ʶ��ͼ��)
	private static FatherMessage dealImageMessage(Map<String, String> requestMap) {
		 // ��ʼ��һ��AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // ���ýӿ�
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
	//�����¼�����
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
	//����CLICK���͵Ĳ˵�
	private static FatherMessage dealClick(Map<String, String> requestMap) {
		
		String key=requestMap.get("EventKey");
		switch (key) {
		case "0":
			//�����һ��һ���˵�
			if(url==null){
				url="http://192.168.137.198/gpio/0";
				 Util.post(url, "û��");
				 
			}
			return new TextMessage(requestMap,"�򿪳ɹ�");
		case "1":
			//�����һ�������˵�
			if(url==null){
			 url="http://192.168.137.198/gpio/1";
			Util.post(url, "û��");
			
			}
			return new TextMessage(requestMap,"�رճɹ�");
			
			
		default:
			
			break;
		}
		return null;

	}
	//����VIEW���͵Ĳ˵�
	private static FatherMessage dealView(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//����Ϣ�������xml���ݰ�
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
	//�����ı���Ϣ
	private static FatherMessage dealTextMessage(Map<String, String> requestMap) throws Exception {
		String mag=requestMap.get("Content");
		
		if(mag.contains("���")||mag.contains("ȡ��")){
			List<Article> articles = new ArrayList<Article>();
			articles.add(new Article("ɨ��һ��ȡ��", "ȡ����ַXX-XX-XX", "http://mmbiz.qpic.cn/mmbiz_jpg/txpRE3GGZGAZm8lRUdiaqatgbdeuYYpu4ZeuiaOPSpphB6bmoKm4Ee5HoDzvzlL6W4jRUsYibz074Uw1kd1ZIsNUg/0", "http://www.baidu.com"));
			NewsMessage nm= new NewsMessage(requestMap, articles);
			
			
			return nm;
		}
		String s=chat(mag);
		System.out.println("==================================");
		System.out.println(s);
		TextMessage tm=new TextMessage(requestMap,s);
		return tm;
	}
	
		//�ش��û����͵��ı���Ϣ(����ͼ�������)
	private static String chat(String mag) throws Exception {
		// TODO Auto-generated method stub
		
	    // ����(utf-8)
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
		 return "����������";
	}
	
	//�ϴ���ʱ�ز�
	
	public static String upload(String path,String type){
		File file=new File(path);
		//��ַ
		String url ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		url=url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
		
		  try {
			//�������Ӷ���
			URL urlObj=new URL(url);
			//ǿתΪ���Ӱ���
			HttpsURLConnection openConnection = (HttpsURLConnection) urlObj.openConnection();
			//�������ӵ���Ϣ
			openConnection.setDoInput(true);//����д��
			openConnection.setDoOutput(true);//����д��
			openConnection.setUseCaches(false);//��������
			//��������ͷ��Ϣ
			openConnection.setRequestProperty("Connection", "Keep-Alive");
			openConnection.setRequestProperty("Charset", "utf8");
			//���ݱ߽�
			String boundary="-----"+System.currentTimeMillis();
			openConnection.setRequestProperty("Content-Type","multipart/from-databoundary="+boundary);
			//��ȡ�����
			DataOutputStream  out=new DataOutputStream(openConnection.getOutputStream());
			//�����ļ�������
			InputStream is=new FileInputStream(file);
			//д����
			//ͷ����Ϣ
			StringBuilder sb=new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			out.write(sb.toString().getBytes());
			
			//�ļ�����
			byte[] b=new byte[1024];
			int len;
			while((len=is.read(b))!=-1){
				out.write(b, 0, len);
			}
			//β����Ϣ
			String foot="\r\n--"+boundary+"--\r\n";
			out.write(foot.getBytes());
			out.flush();
			out.close();
			//��ȡ����
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
