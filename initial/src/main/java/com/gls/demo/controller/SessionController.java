package com.gls.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.gls.demo.entity.User;

@RestController
@RequestMapping(path="session")
public class SessionController {

	@PostMapping("/login")
	public User login(HttpSession session,@RequestParam String userName,@RequestParam String passWord) {
		User user = new User();
		user.setId(1001);
		user.setName(userName);
		user.setContent(passWord);
		session.setAttribute("user", user);
	    return user;
	}
	
	@GetMapping("/getUser")
	public User getUser(@SessionAttribute User user) {
		return user;
	}
}
