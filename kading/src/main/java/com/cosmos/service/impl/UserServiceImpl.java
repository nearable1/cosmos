package com.cosmos.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmos.dao.UserDao;
import com.cosmos.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;

}
