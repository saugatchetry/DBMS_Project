package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.PredictDAO;
import com.example.model.PredictProperty;

public class PredictBean {
	
	@Autowired
	
	private PredictDAO predictDAO;
	
	
	
	public PredictDAO getPredictDao() {
		return predictDAO;
	}



	public void setPredictDao(PredictDAO predictDAO) {
		this.predictDAO = predictDAO;
	}



	public double predictValue(PredictProperty p){
		return predictDAO.predictValue(p);
	}

}
