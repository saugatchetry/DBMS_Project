package com.example.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.TrendsDAO;

public class TrendsBean {
	@Autowired
	private TrendsDAO trendsDAO;

	public TrendsDAO getTrendsDAO() {
		return trendsDAO;
	}

	public void setTrendsDAO(TrendsDAO trendsDAO) {
		this.trendsDAO = trendsDAO;
	}	
	
	public HashMap<String, String> getAveragePriceByZipcode(Integer zipcode){
		return trendsDAO.getAveragePriceByZipcode(zipcode);
	}
	
	public HashMap<String, ArrayList<String>> getPricesGroupedByZipcode(){
		return trendsDAO.getPricesGroupedByZipcode();
	}
	
	public HashMap<String, ArrayList<String>> getPricesByCity(String cityName){
		return trendsDAO.getPricesByCity(cityName);
	}

	public ArrayList<String> getFeatureWidePrices(String carpet, String woodenFlooring, String waterFront, String view,
			String furnishType) {
		return trendsDAO.getFeatureWidePrices(carpet, woodenFlooring, waterFront, view, furnishType);
	}
	public LinkedHashMap<String, String> getDeltaByYear(String zipcode){
		return trendsDAO.getDeltaByYear(zipcode);
	}
	public HashMap<String, String> getSalesByZipcode(String zipcodes, String fromDate, String toDate){
		return trendsDAO.getSalesByZipcode(zipcodes, fromDate, toDate);
	}
}
