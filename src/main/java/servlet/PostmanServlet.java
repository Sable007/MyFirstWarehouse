package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.hqyj.entity.WxUser;
import com.hqyj.entity.WxUserExample;
import com.hqyj.mapper.WxUserMapper;

import util.WeixinUtil;
import net.sf.json.JSONObject;
import service.WxService;
import util.HttpUtil;
import util.MybatisUtil;

@WebServlet("/postman")
public class PostmanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String parameter;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//https://api.weixin.qq.com/cgi-bin/media/get?access_token=30_pd_zPDuNsewl9PNOMpqqdXILE3FPvoAeIJ4cwNRMVDiU7tUWo7G1wnHsi9lY8coP-1fqpk59L0WETkdELBb9d3dmQo7ZQ_O70dNF5X4VE6Jy-dEO0E-UT8hsUDzAPIikyAtAi0N4RG-ZwCE1AWAbABAJFJ&media_id=1237378768e7q8e7r8qwesafdasdfasdfaxss111 
//		parameter = req.getParameter("media_id");
//		String url="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
//		url.replace("MEDIA_ID",parameter).replace("ACCESS_TOKEN", WxService.getAccessToken());
//		System.out.println(parameter);
//		
//		try {
//			WeixinUtil.getFile(url, "C:\\Users\\j_c\\Desktop\\img\\"+parameter);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		req.getRequestDispatcher("/WEB-INF/html/gr.html").forward(req, resp);
//		System.out.println(req.getParameter("media_id"));
		
	}
}
