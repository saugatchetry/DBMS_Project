package com.example.DAO;

import java.util.ArrayList;
import java.util.HashMap;

public interface TrendsDAO {
	public HashMap<String, String> getAveragePriceByZipcode(Integer zipcode);
	public HashMap<String, ArrayList<String>> getPricesGroupedByZipcode();
	public HashMap<String, ArrayList<String>> getPricesByCity(String cityName);
}
