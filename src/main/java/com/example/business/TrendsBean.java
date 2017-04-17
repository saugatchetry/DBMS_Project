package com.example.business;

import java.util.ArrayList;
import java.util.HashMap;

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
}
