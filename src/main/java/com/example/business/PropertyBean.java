package com.example.business;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.PropertyDAO;
import com.example.model.Property;

public class PropertyBean {
	
	@Autowired
	private PropertyDAO propertyDAO;
	
	
	
	public PropertyDAO getPropertyDAO() {
		return propertyDAO;
	}



	public void setPropertyDAO(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}



	public ArrayList<Property> getProperty(){
		return propertyDAO.getProperty();
	}

}
