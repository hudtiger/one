package com.gls.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gls.demo.entity.User;
import com.gls.demo.service.CoffeeService;
import com.gls.global.RtnResult;

@RestController
public class CoffeeController {
	
	@Autowired
	CoffeeService coffeeService;
	
    @GetMapping("/")
    public RtnResult index() {
    	return RtnResult.Success( "Hello");
    }
    
    @GetMapping("/user")
    public User user() {
    	User u = new User();
    	u.setId(12312);
    	u.setName("micheal");
    	u.setContent("sdflsdf");
    	return u;
     //  return "Hello";
    }
    
    @PostMapping("/coffees/add")
    public String add(@RequestBody User coffee) {
    	coffeeService.Add(coffee);
    	return "Created";
    }

    @GetMapping("/coffees")
    public List<User> all() {
        return coffeeService.GetAll();
    }
}
