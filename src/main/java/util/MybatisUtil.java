package util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hqyj.mapper.WxUserMapper;


public class MybatisUtil {
	
	private static SqlSessionFactory factory =null;
	
	static{
		try {
			factory=new SqlSessionFactoryBuilder().build(org.apache.ibatis.io.Resources.getResourceAsStream("mybatis-config.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSession(){
		return factory.openSession();
		
	}
}
