package com.gls.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import com.gls.demo.entity.Coffee;
import com.gls.demo.entity.User;

@Service
public class CoffeeService {
	@Autowired
    RedisOperations<String, User> coffeeOps;
	
	public List<User> GetAll(){
		List<User> list = new ArrayList<>();
		for(String key : coffeeOps.keys("*")) {
			System.out.println(key);
			list.add(coffeeOps.opsForValue().get(key));
		}
		return list;
	}
	
	public void Add(User coffee) {
		coffeeOps.opsForValue().getAndSet(coffee.getId().toString(), coffee);
	}
}
