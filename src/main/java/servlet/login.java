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

@WebServlet("/login")
public class login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrintWriter writer;
	private SqlSession session;
	private WxUserMapper mapper;
	private WxUser User;
	private List<WxUser> selectByExample;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		writer = resp.getWriter();
		String phone=(String) req.getSession().getAttribute("Phone");
		User = (WxUser) req.getSession().getAttribute("User");
		User.setWxPhone(phone);
		if("0".equals(req.getParameter("IDcard"))){
			User.setIdentity("普通用户");
			writer.print("pickup");
		}else{
			User.setIdentity("快递员");
			writer.print("postman");
		}
		req.getSession().setAttribute("User", User);
		
		session = MybatisUtil.getSession();
		mapper = session.getMapper(WxUserMapper.class);
		WxUserExample example=new WxUserExample();
		example.or().andWxOpenIdEqualTo(User.getWxOpenId());
		selectByExample = mapper.selectByExample(example);
		if(selectByExample.size()==0){
			mapper.insert(User);
		}else{
			mapper.updateByExampleSelective(User, example);
		}
		session.commit();
		session.close();
		writer.flush();
		writer.close();
	}
}
