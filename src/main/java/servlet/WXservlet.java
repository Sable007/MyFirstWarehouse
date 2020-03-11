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
         * signature	΢�ż���ǩ����signature����˿�������д��token�����������е�timestamp������nonce������
           timestamp	ʱ���
		   nonce		�����
		   echostr		����ַ���
         */
		String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //У������
        if(WxService.check(timestamp,nonce,signature)){
        	System.out.println("����ɹ�");
        	PrintWriter out=response.getWriter();
        	//ԭ������echostr����
        	out.print(echostr);
        	out.flush();
        	out.close();
        }else {
        	System.out.println("����ʧ��");
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
