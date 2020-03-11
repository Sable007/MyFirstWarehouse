package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WxService;

/**
 * Servlet implementation class WXservlet
 */
@WebServlet("/Wx")
public class WXservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
         * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
           timestamp	时间戳
		   nonce		随机数
		   echostr		随机字符串
         */
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //校验请求
        if(WxService.check(timestamp,nonce,signature)){
        	System.out.println("接入成功");
        	PrintWriter out=response.getWriter();
        	//原样返回echostr参数
        	out.print(echostr);
        	out.flush();
        	out.close();
        }else {
        	System.out.println("接入失败");
        }
	
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");

		Map<String,String> requestMap=WxService.ParseRequest(request.getInputStream());
		System.out.println(requestMap);
//	String respXml="<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+"]]></ToUserName><FromUserName><![CDATA["+requestMap.get("ToUserName")+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime><MsgType><![CDATA["+requestMap.get("MsgType")+"]]></MsgType><Content><![CDATA["+"why?"+"]]></xml>";
		
		String respXml;
		try {
			respXml = WxService.getResponse(requestMap);
			System.out.println(respXml);
			PrintWriter out=response.getWriter();
			out.print(respXml);
			out.flush();
			out.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
