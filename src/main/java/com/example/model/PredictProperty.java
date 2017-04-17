package com.example.model;

import java.sql.Date;

public class PredictProperty {
	
	private float squareFeet;
	private int numberOfBedrooms;
	private int numberOfBathrooms;
	private int numberOfFloors;
	private String yearRenovated;
	private String yearBuilt;
	private Date sale_dt;
	private int zipcode;
	private String city;
	private int woodenFlooring;
	private int carpet;
	private int parking;
	private int petFriendly;
	private int pool;
	private int waterFront;
	private int view;
	private String suggestions;
	private double estimatedValue;
	private int errorStatus; //0 is error 1 is success
	private String errorMessage;
	private double actualPrice;
	private String furnishStatus;
	
	public String getFurnishStatus() {
		return furnishStatus;
	}
	public void setFurnishStatus(String furnishStatus) {
		this.furnishStatus = furnishStatus;
	}
	public double getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}
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
	public float getSquareFeet() {
		return squareFeet;
	}
	public void setSquareFeet(float squareFeet) {
		this.squareFeet = squareFeet;
	}
	public int getNumberOfBedrooms() {
		return numberOfBedrooms;
	}
	public void setNumberOfBedrooms(int numberOfBedrooms) {
		this.numberOfBedrooms = numberOfBedrooms;
	}
	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}
	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
	}
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}
	public String getYearRenovated() {
		return yearRenovated;
	}
	public void setYearRenovated(String yearRenovated) {
		this.yearRenovated = yearRenovated;
	}
	public String getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(String yearBuilt) {
		this.yearBuilt = yearBuilt;
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
	public int getWoodenFlooring() {
		return woodenFlooring;
	}
	public void setWoodenFlooring(int woodenFlooring) {
		this.woodenFlooring = woodenFlooring;
	}
	public int getCarpet() {
		return carpet;
	}
	public void setCarpet(int carpet) {
		this.carpet = carpet;
	}
	public int getParking() {
		return parking;
	}
	public void setParking(int parking) {
		this.parking = parking;
	}
	public int getPetFriendly() {
		return petFriendly;
	}
	public void setPetFriendly(int petFriendly) {
		this.petFriendly = petFriendly;
	}
	public int getPool() {
		return pool;
	}
	public void setPool(int pool) {
		this.pool = pool;
	}
	public int getWaterFront() {
		return waterFront;
	}
	public void setWaterFront(int waterFront) {
		this.waterFront = waterFront;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public String getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}
	public double getEstimatedValue() {
		return estimatedValue;
	}
	public void setEstimatedValue(double estimatedValue) {
		this.estimatedValue = estimatedValue;
	}
	
	/*@Override
	public String toString() {
		return "PredictProp [sq_ft=" + sq_ft + ", no_of_bedrooms=" + no_of_bedrooms + ", no_of_bath=" + no_of_bath + ", no_of_floors=" + no_of_floors
				+ ", yr_renovated=" + yr_renovated + ", yr_built=" + yr_built + ",sale_dt=" +sale_dt+",zipcode="+zipcode+", city="+city+" ]";
	}	*/
}
