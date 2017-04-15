package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.PredictDAO;
import com.example.model.PredictProperty;

public class PredictBean {
	
	@Autowired
	private PredictDAO predictDAO;
	
	
	

	public PredictDAO getPredictDAO() {
		return predictDAO;
	}



	public void setPredictDAO(PredictDAO predictDAO) {
		this.predictDAO = predictDAO;
	}



	public PredictProperty predictValue(PredictProperty p){
		return predictDAO.predictValue(p);
	}



}
