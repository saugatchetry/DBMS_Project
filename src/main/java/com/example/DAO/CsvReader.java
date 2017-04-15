package com.example.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {	
	private String csvFile;   
    private String csvSplitBy;
    
	public CsvReader(String csvFile, String csvSplitBy) {
		super();
		this.csvFile = csvFile;
		this.csvSplitBy = csvSplitBy;
	}
	
	public double[][] loadFeatureMatrix(){		
		String line = "";
		ArrayList<double[]> featureMatrix = new ArrayList<double[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.csvFile))) {
	        while ((line = br.readLine()) != null) {
	            // use comma as separator
	            String[] featureForSingleHouse = line.split(csvSplitBy);
	            double[] features = new double[featureForSingleHouse.length];
	            int k = 0 ;
	            for(String feature : featureForSingleHouse){
	            	features[k++] =  Double.parseDouble(feature);
	            }
	            featureMatrix.add(features);
	        }	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return featureMatrix.toArray(new double[featureMatrix.size()][]);			
	}	
}
