package com.cosmos.dao;

import java.util.HashMap;
import java.util.List;

import com.cosmos.entity.User;

public interface UserDao {
	User getUserList(HashMap<String, String> map);
	
}
