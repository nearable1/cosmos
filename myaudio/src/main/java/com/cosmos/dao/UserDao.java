package com.cosmos.dao;

import com.mysql.jdbc.Blob;

public interface UserDao {
	public Blob getAudio(int id);
}
