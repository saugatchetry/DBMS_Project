package com.example.model;

import java.io.File;
import java.sql.Date;


public class Sell {
	int propId;
	float squareFeet;
	int numberOfBedrooms;
	int numberOfFloors;
	int numberOfBathrooms;
	String yearRenovated;
	String yearBuilt;
//image
	File image;
	String status;
	String description;
	Date saleDate;
	float price;
	float latitude;
	float longitude;
	String street;
	int zipcode;
	String city;
	int sellerId;
	int negotiable;
	PropertyFeature propFeature;
	String path;
	int propertyFeatureId;
	int woodenFlooring;
	int carpet;
	int parking;
	
	int petFriendly;
	int pool;
	int waterFront;
	int view;
	private int errorStatus; //0 is error 1 is success
	private String errorMessage;
	
	public int getPropertyFeatureId() {
		return propertyFeatureId;
	}
	public void setPropertyFeatureId(int propertyFeatureId) {
		this.propertyFeatureId = propertyFeatureId;
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
	public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}
	public int getNumberOfBathrooms() {
		return numberOfBathrooms;
	}
	public void setNumberOfBathrooms(int numberOfBathrooms) {
		this.numberOfBathrooms = numberOfBathrooms;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getNegotiable() {
		return negotiable;
	}
	public void setNegotiable(int negotiable) {
		this.negotiable = negotiable;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	
	public PropertyFeature getPropFeature() {
		return propFeature;
	}
	public void setPropFeature(PropertyFeature propFeature) {
		this.propFeature = propFeature;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
}
