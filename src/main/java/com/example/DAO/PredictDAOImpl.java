package com.example.DAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.KeyGenerator;
import com.example.model.PredictProperty;

public class PredictDAOImpl implements PredictDAO {
	private JdbcTemplate jdbcTemplate;
	private KeyGenerator keyGenerator;
	
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	@Override
	public PredictProperty predictValue(PredictProperty p){
		ArrayList<PredictProperty> returnList = new ArrayList<>();
		StringBuilder querySelect = new StringBuilder();
		querySelect.append("select price, SQ_FT,NUMBER_OF_BATHROOM,NUMBER_OF_BEDROOMS,"
		+"NUMBER_OF_FLOORS,YEAR_RENOVATED,YEAR_BUILT,ZIPCODE"); 
		StringBuilder queryWhere = new StringBuilder();
		queryWhere.append(" from PROPERTY where ");
		boolean flag=false;
		
		
		if(p.getSquareFeet() != 0){
			//querySelect.append("SQ_FT,");
			queryWhere.append("SQ_FT != 0 ");
			flag=true;
		}
		if(p.getNumberOfBathrooms()!=0){
			//querySelect.append("NUMBER_OF_BATHROOM,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_BATHROOM != 0 ");
		}
		if(p.getNumberOfBedrooms()!=0){
		//	querySelect.append("NUMBER_OF_BEDROOMS,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_BEDROOMS != 0 ");
		}
		if(p.getNumberOfFloors()!=0){
			//querySelect.append("NUMBER_OF_FLOORS,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_FLOORS != 0 ");
		}
		if(p.getYearRenovated()!= null){
			//querySelect.append("YEAR_RENOVATED,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" YEAR_RENOVATED > 0 ");
		}
		if(p.getYearBuilt()!=null){
			//querySelect.append("YEAR_BUILT,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" YEAR_BUILT > 0 ");
		}
		/*if(p.getSale_dt()!=null){
			querySelect.append("SALE_DATE,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" SALE_DATE != null ");
		}*/
		if(p.getZipcode()!= 0){
			//querySelect.append("ZIPCODE,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" ZIPCODE != 0 ");
		}
		/*if(p.getCity()!=null){
		//	querySelect.append("CITY,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" CITY != null ");
		}*/
		querySelect.append(queryWhere);
		try{
		jdbcTemplate.query(querySelect.toString(), new ResultSetExtractor<ArrayList<PredictProperty>>() {
            public ArrayList<PredictProperty> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                	PredictProperty prop = new PredictProperty();    
                	prop.setActualPrice(rs.getDouble(1));
                	prop.setSquareFeet(rs.getFloat(2));
                	prop.setNumberOfBathrooms(rs.getInt(3));
                	prop.setNumberOfBedrooms(rs.getInt(4));
                	prop.setNumberOfFloors(rs.getInt(5));
                	prop.setYearRenovated(rs.getString(6));
                	prop.setYearBuilt(rs.getString(7));
                	//prop.setSale_dt(rs.getDate(7));
                	prop.setZipcode(rs.getInt(8));
                	//prop.setCity(rs.getString(9));
                	returnList.add(prop);
                }
                
                System.out.println("Size =  "+returnList.size());
                return returnList;
            }
        });
	
		double [][] featureMatrix =loadFeatureMatrix(returnList,p);
		double[] featuresProvidedByUser = loadUserFeatureMatrix(p);
		p.setEstimatedValue(MultipleRegression.printPredictedPrices(featureMatrix, featuresProvidedByUser));
		return p;
	}
    catch(InvalidResultSetAccessException e){
    	System.out.println("Invalid thing Exception is "+e);
    	return p;
    }
    catch(DataAccessException e){
    	System.out.println("Exception is "+e.getCause().getMessage());
    	p.setErrorStatus(0);
    	p.setErrorMessage(e.getCause().getMessage());
    	return p;
    }
	}
	
	public double[] loadUserFeatureMatrix(PredictProperty userFeatures){
		ArrayList<Double> feature = new ArrayList<Double>();		
        // use comma as separator
		feature.add((double) 1);
		feature.add((double) userFeatures.getSquareFeet());
		feature.add((double) userFeatures.getNumberOfBathrooms());
		feature.add((double) userFeatures.getNumberOfBedrooms());
		feature.add((double) userFeatures.getNumberOfFloors());
		feature.add(Double.parseDouble(userFeatures.getYearRenovated()));
		feature.add(Double.parseDouble(userFeatures.getYearBuilt()));		
		feature.add((double)userFeatures.getZipcode());
				
		double[] copiedArray = new double[feature.size()];
		for(int i = 0 ; i < copiedArray.length; i++){
			copiedArray[i] = feature.get(i).doubleValue();
		}
		
		return copiedArray;			
	}
	
	
	
	public static double[][] loadFeatureMatrix(ArrayList<PredictProperty> returnList, PredictProperty p){		
		
		ArrayList<double[]> featureMatrix = new ArrayList<double[]>();
	        for (PredictProperty p1:returnList){
	        	StringBuilder line = new StringBuilder();
	            // use comma as separator
	        	
	        	line.append(p1.getActualPrice() + ",");
	        	line.append(p1.getSquareFeet()+",");
	        	line.append(p1.getNumberOfBathrooms()+",");
	        	line.append(p1.getNumberOfBedrooms()+",");
	        	line.append(p1.getNumberOfFloors()+",");
	        	line.append(p1.getYearRenovated()+",");
	        	line.append(Double.parseDouble(p1.getYearBuilt())+",");
	        	line.append(p1.getZipcode()+",");
	        	
	        	/*if(p1.getActualPrice() != 0){
	        		line.append(p1.getActualPrice() + ",");
	        	}	        	
	        	if(p1.getSquareFeet() != 0){
	    			line.append(p1.getSquareFeet()+",");
	    		}
	    		if(p1.getNumberOfBathrooms()!=0){
	    			line.append(p1.getNumberOfBathrooms()+",");
	    		}
	    		if(p1.getNumberOfBedrooms()!=0){
	    			line.append(p1.getNumberOfBedrooms()+",");
	    		}
	    		if(p1.getNumberOfFloors()!=0){
	    			line.append(p1.getNumberOfFloors()+",");
	    		}
	    		if(p1.getYearRenovated()!=null){
	    			line.append(p1.getYearRenovated()+",");
	    		}
	    		if(p1.getYearBuilt()!=null){
	    			line.append(Double.parseDouble(p1.getYearBuilt())+",");
	    		}
	    		if(p.getSale_dt()!=null){
	    			line.append(p1.getSale_dt()+",");
	    		}
	    		if(p1.getZipcode()!= 0){
	    			line.append(p1.getZipcode()+",");
	    		}*/
	    		/*if(p.getCity()!=null){
	    		
	    		}*/
	            String[] featureForSingleHouse = line.toString().split(",");
	            double[] features = new double[featureForSingleHouse.length];
	            int k = 0 ;
	            for(String feature : featureForSingleHouse){
	            	features[k++] =  Double.parseDouble(feature);
	            }
	            featureMatrix.add(features);
	        }	        
		return featureMatrix.toArray(new double[featureMatrix.size()][]);			
	}	

	
 
}