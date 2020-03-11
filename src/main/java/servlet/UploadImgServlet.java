package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
import service.BaiduService;
import service.WxService;
import util.HttpUtil;
import util.MybatisUtil;

@WebServlet("/uploadImg")
public class UploadImgServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String parameter;
	private File file;
	private PrintWriter writer;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//https://api.weixin.qq.com/cgi-bin/media/get?access_token=30_pd_zPDuNsewl9PNOMpqqdXILE3FPvoAeIJ4cwNRMVDiU7tUWo7G1wnHsi9lY8coP-1fqpk59L0WETkdELBb9d3dmQo7ZQ_O70dNF5X4VE6Jy-dEO0E-UT8hsUDzAPIikyAtAi0N4RG-ZwCE1AWAbABAJFJ&media_id=1237378768e7q8e7r8qwesafdasdfasdfaxss111 
		parameter = req.getParameter("media_id");
		String url="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		url.replace("MEDIA_ID",parameter).replace("ACCESS_TOKEN", WxService.getAccessToken());
		writer = resp.getWriter();
		try {
			file = WeixinUtil.getFile(url, "C:\\Users\\j_c\\Desktop\\img\\"+parameter);
			file=WeixinUtil.fetchTmpFile(parameter, "image");
			String s=BaiduService.getnumbersByIocr(file.getAbsolutePath());
			JSONObject job=JSONObject.fromObject(s);
			System.out.println(JSONObject.fromObject(job.getJSONObject("data").getJSONArray("ret").get(0)).getString("word"));
			writer.print("200");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
