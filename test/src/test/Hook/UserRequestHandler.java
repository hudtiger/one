package test.Hook;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class UserRequestHandler implements Callable{
	private static Logger logger  = LogManager.getLogger(UserRequestHandler.class); 
	private User user;
	public UserRequestHandler(User user) {
		this.user = user;
	}
	public String call(){
		return this.user.toString();
	}
	public static UserRequestHandler fromJson(String json) {
		logger.info("UserRequestHandler initinalized.");
		return new UserRequestHandler(JSON.parseObject(json, User.class));
	}
}