package com.cosmos.service.impl;

import java.sql.Blob;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosmos.dao.UserDao;
import com.cosmos.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;

	public Blob getAudio(int id) {
		// TODO Auto-generated method stub
		return ud.getAudio(id);
	}

}
