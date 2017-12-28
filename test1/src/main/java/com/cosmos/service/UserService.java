package com.cosmos.service;

import org.springframework.transaction.annotation.Transactional;

import com.cosmos.dao.UserDao;

@Transactional
public interface UserService extends UserDao{

}
