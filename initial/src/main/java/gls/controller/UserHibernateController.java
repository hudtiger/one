package gls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gls.entity.User;
import gls.repository.UserRepository;

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
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String content) {
		User n = new User();
		n.setName(name);
		n.setContent(content);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
}
