package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/root")
public class rootServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/**
		 * https://open.weixin.qq.com/connect/oauth2/authorize?
		 * appid=APPID
		 * &redirect_uri=REDIRECT_URI
		 * &response_type=code
		 * &scope=SCOPE
		 * &state=STATE#wechat_redirect
		 */
		
		String url="";
		String parameter="";
		url="https://open.weixin.qq.com/connect/oauth2/authorize?";
		parameter=parameter+"appid=wx9d2575f382dd1ee8";
		parameter=parameter+"&redirect_uri="+java.net.URLEncoder.encode("http://jc02.free.idcfengye.com/a1/sms");
		
		parameter=parameter+"&scope=snsapi_userinfo";
		parameter=parameter+"&state=STATE#wechat_redirect";
		url=url+parameter;

		resp.sendRedirect(url);
				
			
	
	}
}
