package gls.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import gls.entity.Coffee;

@Service
public class CoffeeService {
	@Autowired
    RedisOperations<String, Coffee> coffeeOps;
	
	public List<Coffee> GetAll(){
		List<Coffee> list = new ArrayList<>();
		coffeeOps.keys("*").forEach(key->{list.add(coffeeOps.opsForValue().get(key));});
		return list;
	}
	
	public void Add(Coffee coffee) {
		coffeeOps.opsForValue().getAndSet(coffee.getId(), coffee);
	}
}
