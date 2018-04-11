package gls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gls.entity.User;
import gls.service.UserInfoService;

@RestController
@RequestMapping(path = "Mybatis")
public class UserMybatisController {
	@Autowired
	UserInfoService userinfoService;
	
	@GetMapping(path="/allForMybatis")
	public List<User> AllUsers() {
		return userinfoService.getUserList();
	}
}
