package com.example.DAO;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Property;
import com.example.model.PropertySearch;
import com.example.model.User;

public interface PropertyDAO {
	ArrayList<Property> getProperty();
	boolean insertImage(byte[] arr);
	List<byte[]> getImages(String imageId);
	ArrayList<Property> getSearchedProperties(PropertySearch propertySearch);		
	ArrayList<String> getCities();
	ArrayList<Property> getRecentProperties(String userId);
	public boolean insertImageByFile(String imageId);
	public ArrayList<ArrayList<String>> getTopSearchedProperties();
	public ArrayList<User> getTopSellers(String zipcode);
}
