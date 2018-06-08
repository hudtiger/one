package test;

import com.alibaba.fastjson.JSON;

import test.Mapper.UserInfo;
import test.Mapper.UserVO;
import utils.DateWapper;
import utils.DozerUtils;

public class testDozer {

	public static void main(String[] args) {
		UserVO user = new UserVO(12,"2019-05-25 09:23:45:990","Micheal say hi");
		System.out.println(JSON.toJSONString(user));
		UserInfo userinfo = DozerUtils.map(user, UserInfo.class);
		System.err.println(JSON.toJSONString(userinfo));
		
		userinfo.setDateS(DateWapper.Create(userinfo.getDateS()).AddDays(-10).AddMiliSeconds(100).getDate());
		System.err.println(DozerUtils.map(userinfo, UserVO.class));
	}
}


