package com.example.DAO;


import com.example.model.User;

public interface UserDAO {
	User getUser(User user);
	User insertUser(User user);
	int getTotalRecords();
}