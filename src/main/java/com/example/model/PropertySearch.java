package com.example.model;

import java.util.ArrayList;

public class PropertySearch {
	
	ArrayList<Integer> totalBedrooms;
	ArrayList<Integer> totalBathrooms;
	ArrayList<Integer> totalFloors;
	int startingSqFt;
	int endingSqFt;
	int startingPrice;
	int endingPrice;
	int currentPageNumber;
	int zipcode;
	String image;
	int orderBy;	
	
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public ArrayList<Integer> getTotalBedrooms() {
		return totalBedrooms;
	}
	public void setTotalBedrooms(ArrayList<Integer> totalBedrooms) {
		this.totalBedrooms = totalBedrooms;
	}
	public ArrayList<Integer> getTotalBathrooms() {
		return totalBathrooms;
	}
	public void setTotalBathrooms(ArrayList<Integer> totalBathrooms) {
		this.totalBathrooms = totalBathrooms;
	}
	public ArrayList<Integer> getTotalFloors() {
		return totalFloors;
	}
	public void setTotalFloors(ArrayList<Integer> totalFloors) {
		this.totalFloors = totalFloors;
	}
	public int getStartingSqFt() {
		return startingSqFt;
	}
	public void setStartingSqFt(int startingSqFt) {
		this.startingSqFt = startingSqFt;
	}
	public int getEndingSqFt() {
		return endingSqFt;
	}
	public void setEndingSqFt(int endingSqFt) {
		this.endingSqFt = endingSqFt;
	}
	public int getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}
	public int getEndingPrice() {
		return endingPrice;
	}
	public void setEndingPrice(int endingPrice) {
		this.endingPrice = endingPrice;
	}

}
