package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class registerServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(req.getSession().getAttribute("random")!=null){
			String code=req.getParameter("code");
			  String random= req.getSession().getAttribute("random").toString();
			
			System.out.println(code+"========="+random);
			System.out.println(random.equals(code));
			
			PrintWriter out=resp.getWriter();
			int b;
			if(random.equals(code)){
				b=1;
			}else{
				b=0;
			}
			String ok=req.getParameter("ok");
			if("ok".equals(ok)){
				req.getSession().removeAttribute("random");
			}
			out.print(b);
			out.flush();
			out.close();
		}
		else{
			PrintWriter out=resp.getWriter();
			out.print(0);
			out.flush();
			out.close();
		}
		 
	}
}
