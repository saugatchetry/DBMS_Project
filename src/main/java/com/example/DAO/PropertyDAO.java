package com.example.DAO;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Property;

public interface PropertyDAO {
	ArrayList<Property> getProperty();
	boolean insertImage();
	List<byte[]> getImages();
}
