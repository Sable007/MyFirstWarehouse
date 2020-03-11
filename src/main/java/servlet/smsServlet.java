package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.hqyj.entity.WxUser;
import com.hqyj.mapper.WxUserMapper;

import entity.AccessToken;
import net.sf.json.JSONObject;
import service.WxService;
import service.userService;
import util.HttpUtil;
import util.MybatisUtil;
import util.Util;

@WebServlet("/sms")
public class smsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		WxUser userp=(WxUser) req.getSession().getAttribute("User");
		if(userp!=null){
			if("普通用户".equals(userp.getIdentity())){
			req.getRequestDispatcher("/").forward(req, resp);
			}else if("快递员".equals(userp.getIdentity())){
				req.getRequestDispatcher("/").forward(req, resp);
				return;
			}

		}
		String code=req.getParameter("code");
		System.out.println("code="+code);
		if(code!=null){
			try {
				/**
				 * https://api.weixin.qq.com/sns/oauth2/access_token?
				 * appid=APPID
				 * &secret=SECRET
				 * &code=CODE
				 * &grant_type=authorization_code
				 */
		    	String url="";
		        String result = null;
		        url=url+"https://api.weixin.qq.com/sns/oauth2/access_token?";
		        url=url+"appid=wx9d2575f382dd1ee8";
		        url=url+"&secret=daea75f30d6edbd32dd3d5add0b60483";
		        url=url+"&code="+code;
		        url=url+"&grant_type=authorization_code";
		            result = Util.getToken(url);
		            JSONObject json = JSONObject.fromObject(result);
		            System.out.println(json.toString());
		            if(json!=null){
		            	
		            	String refresh_token = json.getString("refresh_token");
		            	String openid = json.getString("openid");
		            	String scope = json.getString("scope");
		            	
		            	WxUser user=new WxUser();
		            	user.setWxOpenId(openid);
		            	req.getSession().setAttribute("User",user);
		            }
	     
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
		}else{
			req.getRequestDispatcher("404.html").forward(req, resp);
			return;
		}
		
			System.out.println("SMS");
			req.getRequestDispatcher("/WEB-INF/html/sms.html").forward(req, resp);
			
			
	}
	
}
