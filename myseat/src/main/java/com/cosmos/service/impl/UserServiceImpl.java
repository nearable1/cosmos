package com.cosmos.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmos.dao.UserDao;
import com.cosmos.entity.User;
import com.cosmos.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;
	
	@Override
	public User getUserList(HashMap map) {
		// TODO Auto-generated method stub
		return ud.getUserList(map);
	}

}
