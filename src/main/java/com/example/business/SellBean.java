package com.example.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.DAO.SellDAO;
import com.example.model.Sell;
public class SellBean {

@Autowired

private SellDAO sellDAO;


public Sell insertDetails(Sell s){
	return sellDAO.insertDetails(s);
}



public SellDAO getSellDAO() {
	return sellDAO;
}



public void setSellDAO(SellDAO sellDAO) {
	this.sellDAO = sellDAO;
}


}
