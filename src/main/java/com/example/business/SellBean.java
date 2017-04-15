package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.SellDAO;
import com.example.model.Sell;
public class SellBean {

@Autowired

private SellDAO sellDAO;



public SellDAO getPredictDao() {
	return sellDAO;
}

public Sell getUser(Sell sell) {
	return sellDAO.getSell(sell);
}


public void setPredictDao(SellDAO predictDAO) {
	this.sellDAO = predictDAO;
}



public Sell insertDetails(Sell s){
	return sellDAO.insertDetails(s);
}


}
