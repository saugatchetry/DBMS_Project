package com.example.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public interface TrendsDAO {
	public HashMap<String, String> getAveragePriceByZipcode(Integer zipcode);
	public HashMap<String, ArrayList<String>> getPricesGroupedByZipcode();
	public HashMap<String, ArrayList<String>> getPricesByCity(String cityName);
	public ArrayList<String> getFeatureWidePrices(String carpet, String woodenFlooring, String waterFront, String view,	String furnishType);
	public LinkedHashMap<String, String> getDeltaByYear(String zipcode);
	public HashMap<String, String> getSalesByZipcode(String zipcodes, String fromDate, String toDate);
}
