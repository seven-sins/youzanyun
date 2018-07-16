package com.hiya3d.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiya3d.conf.exception.HiyaException;
import com.hiya3d.mapper.UserMapper;
import com.hiya3d.po.User;
import com.hiya3d.service.UserService;

/**
 * @author seven sins
 * @datetime 2018年7月14日 下午11:15:25
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public List<User> find(User entity) {
		return userMapper.find(entity);
	}

	@Override
	public int count(User entity) {
		return 0;
	}

	@Override
	public User get(Serializable id) {
		return userMapper.get(id);
	}

	@Override
	public void insert(User entity) {
		entity.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		if(entity.getPoint() == null) {
			entity.setPoint(0);
		}
		userMapper.insert(entity);		
	}

	@Override
	public void update(User entity) {
		userMapper.update(entity);		
	}

	@Override
	public void deleteById(Serializable id) {
		userMapper.deleteById(id);		
	}

	@Override
	public void delete(Serializable[] ids) {
		userMapper.delete(ids);		
	}

	@Override
	public void updatePoint(String mobile, Integer amount) {
		User query = new User();
		query.setMobile(mobile);
		List<User> list = this.find(query);
		if(list == null || list.isEmpty()) {
			throw new HiyaException(400, "用户不存在");
		}
		User user = list.get(0);
		user.setPoint(user.getPoint() + amount);
		this.update(user);
	}

	@Override
	public User getByMobile(String mobile) {
		return userMapper.getByMobile(mobile);
	}

}
