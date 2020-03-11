package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

import util.MybatisUtil;

@WebServlet("/test")
public class testServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SqlSession session;
	private List<WxUser> list;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf8");
		resp.setCharacterEncoding("utf8");
		
		System.out.println(req.getParameter("phone"));
		String phone=req.getParameter("phone");
		req.getSession().setAttribute("Phone", phone);

		// {"name": "John Doe", "age": 18, "address": {"country" : "china", "zip-code": "10000"}}
		int a=(int)(Math.random()*1000000);
		req.getSession().setAttribute("random", a);
		System.out.println(a);
	}
}
