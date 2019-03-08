package io.kading.modules.sys.service.impl;

import java.util.ArrayList;

import io.kading.modules.sys.dao.UserDao;
import io.kading.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao ud;

}
