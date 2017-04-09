package com.example.model;

import java.sql.Date;

public class PredictProperty {
	
	private float sq_ft;
	private int no_of_bedrooms;
	private int no_of_bath;
	private int no_of_floors;
	private String yr_renovated;
	private String yr_built;
	private Date sale_dt;
	private int zipcode;
	private String city;
	private int errorStatus; //0 is error 1 is success
	private String errorMessage;
	
	public int getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public float getSq_ft() {
		return sq_ft;
	}
	public void setSq_ft(float f) {
		this.sq_ft = f;
	}
	public int getNo_of_bedrooms() {
		return no_of_bedrooms;
	}
	public void setNo_of_bedrooms(int no_of_bedrooms) {
		this.no_of_bedrooms = no_of_bedrooms;
	}
	public int getNo_of_bath() {
		return no_of_bath;
	}
	public void setNo_of_bath(int no_of_bath) {
		this.no_of_bath = no_of_bath;
	}
	public int getNo_of_floors() {
		return no_of_floors;
	}
	public void setNo_of_floors(int no_of_floors) {
		this.no_of_floors = no_of_floors;
	}
	public String getYr_renovated() {
		return yr_renovated;
	}
	public void setYr_renovated(String yr_renovated) {
		this.yr_renovated = yr_renovated;
	}
	public String getYr_built() {
		return yr_built;
	}
	public void setYr_built(String yr_built) {
		this.yr_built = yr_built;
	}
	public Date getSale_dt() {
		return sale_dt;
	}
	public void setSale_dt(Date sale_dt) {
		this.sale_dt = sale_dt;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	
	@Override
	public String toString() {
		return "PredictProp [sq_ft=" + sq_ft + ", no_of_bedrooms=" + no_of_bedrooms + ", no_of_bath=" + no_of_bath + ", no_of_floors=" + no_of_floors
				+ ", yr_renovated=" + yr_renovated + ", yr_built=" + yr_built + ",sale_dt=" +sale_dt+",zipcode="+zipcode+", city="+city+" ]";
	}	
}
