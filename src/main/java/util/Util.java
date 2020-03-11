package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import net.sf.json.JSONObject;
import service.WxService;

public class Util {

	
	public static final String APPKEY = "82815192bbd82b54";// ���appkey
	public static final String URL = "https://api.jisuapi.com/iqa/query";
	public static final String question = "��������";// ����(utf-8)
	//ҳ��ʹ��JSSDK��ƾ��
	public static final String GET_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	 //ͼ�������
	    public static void Get() throws Exception {
	        String result = null;
	        String url = URL + "?appkey=" + APPKEY + "&question=" + URLEncoder.encode(question, "utf-8");
	 
	        try {
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
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //��ȡaccess_token
	public static  String getToken(String url){
		
		try {
			URL urlObj=new URL(url);
			//������
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			
			 String inputLine;
		        StringBuffer result = new StringBuffer();
		        while ((inputLine = in.readLine()) != null) {
		            result.append(inputLine);
		            result.append("\r\n");
		        }
		        in.close();
		        connection.disconnect();
		        return result.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	//��ָ���ĵ�ַ����һ��post���󣬴�������date
	public static String post(String url,String data){
		try {
			URL urlObj=new URL(url);
			 HttpURLConnection  connection = (HttpURLConnection) urlObj.openConnection();
			//Ҫ�������ݳ�ȥ������Ҫ����Ϊ�ɷ�������״̬
			connection.setDoOutput(true);
			//��ȡ�����
			OutputStream os = connection.getOutputStream();
			BufferedWriter out= new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));
			
			//д������
			out.write(data);
			out.close();
			//��ȡ����������ȡ����

	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
	        String inputLine;
	        StringBuffer result = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            result.append(inputLine);
	            result.append("\r\n");
	        }
	        in.close();
	        connection.disconnect();
	        return result.toString();
//			InputStream is= connection.getInputStream();
//			byte[] b=new byte[1024];
//			int len;
//			StringBuilder sb= new StringBuilder();
//			while((len=is.read(b))!=-1){
//				sb.append(new String(b,0,len));
//			}
//			return sb.toString();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//��ȡJSSDK��TICKET
	public static String JSTicket() throws Exception{
		String result=HttpUtil.sendGet(GET_TICKET_URL.replace("ACCESS_TOKEN", WxService.getAccessToken()), "utf-8");
		System.out.println(result);
		return result;
	}
}
