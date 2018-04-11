package gls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gls.entity.User;
import gls.mapper.UserInfoMapper;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoMapper userMapper;

	public User getUserById(Integer id) {
		return userMapper.getUserById(id);
	}

	public List<User> getUserList() {
		return userMapper.getUserList();
	}

	public int add(User user) {
		return userMapper.add(user);
	}

	public int update(Integer id, User user) {
		return userMapper.update(id, user);
	}

	public int delete(Integer id) {
		return userMapper.delete(id);
	}
}
