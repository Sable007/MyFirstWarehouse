package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import service.WxService;
public class WeixinUtil {
	 public static File getFile(String requestUrl,String savePath) throws Exception {  

	            URL url = new URL(requestUrl);  
	            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
//	            httpUrlConn.setSSLSocketFactory(ssf);  

	            httpUrlConn.setDoOutput(true);  
	            httpUrlConn.setDoInput(true);  
	            httpUrlConn.setUseCaches(false);  
	            // 设置请求方式（GET/POST）  
	            httpUrlConn.setRequestMethod("GET");  

	            httpUrlConn.connect();  

	            //获取文件扩展名
	            String ext=getExt(httpUrlConn.getContentType());
	            savePath=savePath+ext;
	            System.out.println("savePath"+savePath);
	            //下载文件到f文件
	            File file = new File(savePath);

	            
	            // 获取微信返回的输入流
	            InputStream in = httpUrlConn.getInputStream(); 
	            
	            //输出流，将微信返回的输入流内容写到文件中
	            FileOutputStream out = new FileOutputStream(file);
	             
	            int length=100*1024;
	            byte[] byteBuffer = new byte[length]; //存储文件内容
	            
	            int byteread =0;
	            int bytesum=0;
	            
	            while (( byteread=in.read(byteBuffer)) != -1) {  
	                bytesum += byteread; //字节数 文件大小 
	                out.write(byteBuffer,0,byteread);  
	                
	            }  
	            System.out.println("bytesum: "+bytesum);
	            
	            in.close();  
	            // 释放资源  
	            out.close();  
	            in = null;  
	            out=null;
	            
	            httpUrlConn.disconnect();  

	            
	            return file;
	    }  
	 private static String getExt(String contentType){
	        if("image/jpeg".equals(contentType)){
	            return ".jpg";
	        }else if("image/png".equals(contentType)){
	            return ".png";
	        }else if("image/gif".equals(contentType)){
	            return ".gif";
	        }
	        
	        return null;
	    }
	 
	//定义两个成员变量常量
	//获取临时素材(视频不能使用https协议)
	   public static final String GET_TMP_MATERIAL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	    //获取临时素材(视频)
	    public static final String GET_TMP_MATERIAL_VIDEO = "http://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	    
	//获取微信服务器中生成的媒体文件

	//由于视频使用的是http协议，而图片、语音使用http协议，故此处需要传递media_id和type
	    //image/jpeg图片类型
	public static File fetchTmpFile(String media_id, String type){
	  try {
	       String token = WxService.getAccessToken();
	       String url = null;
	   //视频是http协议
	   if("video".equalsIgnoreCase(type)){
	    url = String.format(GET_TMP_MATERIAL_VIDEO, token, media_id);
	   }else{
	    url = String.format(GET_TMP_MATERIAL, token, media_id);;
	   }
	   URL u = new URL(url);
	   HttpURLConnection  conn = (HttpURLConnection) u.openConnection();
	   conn.setRequestMethod("POST");
	   conn.connect();
	   BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	   String content_disposition = conn.getHeaderField("content-disposition");
	   //微信服务器生成的文件名称
	   String file_name ="";
	   String[] content_arr = content_disposition.split(";");
	   if(content_arr.length  == 2){
	    String tmp = content_arr[1];
	    int index = tmp.indexOf("\"");
	    file_name =tmp.substring(index+1, tmp.length()-1);
	   }
	   //生成不同文件名称
	   File file = new File("C:\\Users\\j_c\\Desktop\\img\\"+file_name);
	   BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
	   byte[] buf = new byte[2048];
	   int length = bis.read(buf);
	   while(length != -1){
	    bos.write(buf, 0, length);
	    length = bis.read(buf);
	   }
	   bos.close();
	   bis.close();
	   return file;
	 } catch (Exception e) {
	  e.printStackTrace();
	 } 
	 return null;
	 }
}
