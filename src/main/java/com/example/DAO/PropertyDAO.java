package com.example.DAO;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Property;

public interface PropertyDAO {
	ArrayList<Property> getProperty();
	boolean insertImage(byte[] arr);
	List<byte[]> getImages(String imageId);
	ArrayList<String> getCities();
}
