package com.example.model;

public class BuyerPreference {
	
	private String id;
	private int minArea;
	private int maxArea;
	private float minBudget;
	private float maxBudget;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getMinArea() {
		return minArea;
	}
	public void setMinArea(int minArea) {
		this.minArea = minArea;
	}
	public int getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(int maxArea) {
		this.maxArea = maxArea;
	}
	public float getMinBudget() {
		return minBudget;
	}
	public void setMinBudget(float minBudget) {
		this.minBudget = minBudget;
	}
	public float getMaxBudget() {
		return maxBudget;
	}
	public void setMaxBudget(float maxBudget) {
		this.maxBudget = maxBudget;
	}	
}
