package com.gls.demo.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import com.gls.demo.entity.User;
import com.gls.demo.mapper.UserInfoMapper;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoMapper userMapper;

//	@Cacheable(value = "gls",sync=true, key = "'user_'.concat(#root.args[0])")
	@Cacheable(value = "gls",sync=true,keyGenerator="wiselyKeyGenerator")
	public User getUserById(Integer id) {
		return userMapper.getUserById(id);
	}

	@CacheEvict(value = "gls",keyGenerator="wiselyKeyGenerator",allEntries=true,beforeInvocation=true)
	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	public int add(User user) {
		return userMapper.add(user);
	}

	@CachePut(value = "gls",keyGenerator="wiselyKeyGenerator")
	public int update(Integer id, User user) {
		return userMapper.update(id, user);
	}

	@CacheEvict(value = "gls",keyGenerator="wiselyKeyGenerator",allEntries=false,beforeInvocation=true)
	public int delete(Integer id) {
		return userMapper.delete(id);
	}
	
	@Autowired
	 RedisOperations<String, User> redisOps;
	
	@PostConstruct
	void init() {
		this.getUserList().forEach(user->redisOps.opsForValue().set(user.getId().toString(), user));
	}
}
