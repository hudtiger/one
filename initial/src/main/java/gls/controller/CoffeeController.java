package gls.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gls.entity.Coffee;
import gls.service.CoffeeService;

@RestController
public class CoffeeController {
	
	@Autowired
	CoffeeService coffeeService;
	
    @GetMapping("/")
    public String index() {
        return "Hello";
    }
    
    @PostMapping("/coffees/add")
    public void add(@RequestBody Coffee coffee) {
    	coffeeService.Add(coffee);
    }

    @GetMapping("/coffees")
    public List<Coffee> all() {
        return coffeeService.GetAll();
    }
}
