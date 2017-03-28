package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.UserDAO;
import com.example.model.User;

public class UserBean {
	@Autowired
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User getUser(User user) {
		return userDAO.getUser(user);
	}

}
