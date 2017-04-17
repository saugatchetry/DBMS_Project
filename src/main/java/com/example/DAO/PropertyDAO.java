package com.example.DAO;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Property;
import com.example.model.PropertySearch;

public interface PropertyDAO {
	ArrayList<Property> getProperty();
	boolean insertImage(byte[] arr);
	List<byte[]> getImages(String imageId);
	ArrayList<Property> getSearchedProperties(PropertySearch propertySearch);		
	ArrayList<String> getCities();
}
