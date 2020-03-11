package service;


import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.hqyj.entity.WxUser;
import com.hqyj.mapper.WxUserMapper;



@Service
public class userService {
	@Autowired
	private static WxUserMapper um;
	
	public static void addUser(WxUser user){
		um.insert(user);
	}
}
