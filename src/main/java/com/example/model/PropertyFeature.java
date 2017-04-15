package com.example.model;

import java.util.ArrayList;

public class PropertyFeature {
	int propertyFeatureId;
	int propId;
	ArrayList<String> featureName;
	ArrayList<String> featureValues;
	public int getPropertyFeatureId() {
		return propertyFeatureId;
	}
	public void setPropertyFeatureId(int propertyFeatureId) {
		this.propertyFeatureId = propertyFeatureId;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public ArrayList<String> getFeatureName() {
		return featureName;
	}
	public void setFeatureName(ArrayList<String> featureName) {
		this.featureName = featureName;
	}
	public ArrayList<String> getFeatureValues() {
		return featureValues;
	}
	public void setFeatureValues(ArrayList<String> featureValues) {
		this.featureValues = featureValues;
	}
	
}
