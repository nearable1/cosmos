package com.cosmos.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmos.dao.UserDao;
import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Service()
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User getUserList(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return userDao.getUserList(map);
	}

}
