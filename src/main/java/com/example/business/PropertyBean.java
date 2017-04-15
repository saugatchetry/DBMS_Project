package com.example.business;

import java.util.ArrayList;
import java.util.List;

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
	
	public boolean insertImage(){
		return propertyDAO.insertImage();
	}
	
	public List<byte[]> getImages(){
		return propertyDAO.getImages();
	}
	
	
	public ArrayList<Property> getSearchedProperties(Property property){
		return propertyDAO.getSearchedProperties(property);
	}
	
	public ArrayList<String> getCities(){
		return propertyDAO.getCities();
	}

}
