package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.JssdkPar;
import net.sf.json.JSONObject;
import service.WxService;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private ServletOutputStream outputStream;
	private PrintWriter writer;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		resp.setContentType("text/json;charset=UTF-8");
		
		try {
		String timestamp=new SimpleDateFormat("yyyyMMdd").format(new Date());
		String noncestr="woshinidei";
		String url="http://jc02.free.idcfengye.com/a1/postman";
		String jssdKsignature= WxService.JSSDKsignature(WxService.getJSTicket(),noncestr,timestamp , url);
		System.out.println(jssdKsignature);
		JssdkPar jp=new JssdkPar();
		jp.setAppId("wx9d2575f382dd1ee8");
		jp.setNonceStr(noncestr);
		jp.setTimestamp(timestamp);
		jp.setSignature(jssdKsignature);
		JSONObject js=JSONObject.fromObject(jp);
		writer = resp.getWriter();
		writer.println(js);
		writer.flush();
		writer.close();
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
