package com.gls.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gls.demo.entity.User;
import com.gls.demo.repository.UserRepository;

@RestController
@RequestMapping(path = "Hibernate")
public class UserHibernateController {

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.datasource.gls.jdbc-url}")
	public String connStr;

	@GetMapping(path = "/")
	public String Index() {
		double a  = 10/0;
		return connStr;
	}

	@GetMapping(path = "/add")
	public String addNewUser(@RequestParam String name, @RequestParam String content) {
		User n = new User();
		n.setName(name);
		n.setContent(content);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}
}
