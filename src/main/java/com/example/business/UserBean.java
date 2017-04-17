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

	public User insertUser(User user){
		return userDAO.insertUser(user);

	}
	
	public int getTotalRecords(){
		return userDAO.getTotalRecords();
	}

	public User insertSearchedProperty(Integer userId, Long propertyId) {
		// TODO Auto-generated method stub
		return userDAO.insertSearchedProperty(userId,propertyId);
	}
	
	


}
