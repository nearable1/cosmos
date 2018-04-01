package com.cosmos.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmos.dao.UserDao;
import com.cosmos.entity.Sound;
import com.cosmos.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao ud;

	public ArrayList<String> getTypes() {
		// TODO Auto-generated method stub
		return ud.getTypes();
	}

	public ArrayList<Sound> getAudio(String type) {
		// TODO Auto-generated method stub
		return ud.getAudio(type);
	}

	public ArrayList<Sound> getHot() {
		// TODO Auto-generated method stub
		return ud.getHot();
	}


}
