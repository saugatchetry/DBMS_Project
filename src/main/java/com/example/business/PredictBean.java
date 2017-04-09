package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.PredictDao;
import com.example.model.PredictProperty;

public class PredictBean {
	
	@Autowired
	
	private PredictDao predictDao;
	
	
	
	public PredictDao getPropertyDAO() {
		return predictDao;
	}



	public void setPredictDao(PredictDao predictDao) {
		this.predictDao = predictDao;
	}



	public double predictValue(PredictProperty p){
		return predictDao.predictValue(p);
	}

}
