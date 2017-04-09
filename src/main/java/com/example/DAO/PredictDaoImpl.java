package com.example.DAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.KeyGenerator;
import com.example.model.PredictProperty;



public class PredictDaoImpl implements PredictDao {
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
	public double predictValue(PredictProperty p){
		ArrayList<PredictProperty> returnList = new ArrayList<>();
		StringBuilder querySelect = new StringBuilder();
		querySelect.append("select SQ_FT,NUMBER_OF_BATHROOM,NUMBER_OF_BEDROOMS,"
		+"NUMBER_OF_FLOORS,YEAR_RENOVATED,YEAR_BUILT,SALE_DATE,ZIPCODE,CITY"); 
		StringBuilder queryWhere = new StringBuilder();
		queryWhere.append("from PROPERTY where ");
		boolean flag=false;
		
		
		if(p.getSq_ft() != 0){
			//querySelect.append("SQ_FT,");
			queryWhere.append("SQ_FT != 0 ");
			flag=true;
		}
		if(p.getNo_of_bath()!=0){
			//querySelect.append("NUMBER_OF_BATHROOM,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_BATHROOM != 0 ");
		}
		if(p.getNo_of_bedrooms()!=0){
		//	querySelect.append("NUMBER_OF_BEDROOMS,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_BEDROOMS != 0 ");
		}
		if(p.getNo_of_floors()!=0){
			//querySelect.append("NUMBER_OF_FLOORS,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" NUMBER_OF_FLOORS != 0 ");
		}
		if(p.getYr_renovated()!=null){
			//querySelect.append("YEAR_RENOVATED,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" YEAR_RENOVATED != null ");
		}
		if(p.getYr_built()!=null){
			//querySelect.append("YEAR_BUILT,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" YEAR_BUILT != null ");
		}
		if(p.getSale_dt()!=null){
			querySelect.append("SALE_DATE,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" SALE_DATE != null ");
		}
		if(p.getZipcode()!= 0){
			//querySelect.append("ZIPCODE,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" ZIPCODE != 0 ");
		}
		if(p.getCity()!=null){
		//	querySelect.append("CITY,");
			if(flag)
				queryWhere.append(" and ");
			flag=true;
			queryWhere.append(" CITY != null ");
		}
		querySelect.append(queryWhere);
		jdbcTemplate.query(querySelect.toString(), new ResultSetExtractor<ArrayList<PredictProperty>>() {
            public ArrayList<PredictProperty> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                	PredictProperty prop = new PredictProperty();
                	prop.setSq_ft(rs.getFloat(1));
                	prop.setNo_of_bath(rs.getInt(2));
                	prop.setNo_of_bedrooms(rs.getInt(3));
                	prop.setNo_of_floors(rs.getInt(4));
                	prop.setYr_renovated(rs.getString(5));
                	prop.setYr_built(rs.getString(6));
                	prop.setSale_dt(rs.getDate(7));
                	prop.setZipcode(rs.getInt(8));
                	prop.setCity(rs.getString(9));
                	returnList.add(prop);
                }
                
                System.out.println("Size =  "+returnList.size());
                return returnList;
            }
        });
		double [][] featureMatrix =loadFeatureMatrix(returnList,p);
		return MultipleRegression.printPredictedPrices(featureMatrix);
	}
	public static double[][] loadFeatureMatrix(ArrayList<PredictProperty> returnList, PredictProperty p){		
		
		ArrayList<double[]> featureMatrix = new ArrayList<double[]>();
	        for (PredictProperty p1:returnList){
	        	StringBuilder line = new StringBuilder();
	            // use comma as separator
	        	if(p.getSq_ft() != 0){
	    			line.append(p1.getSq_ft()+",");
	    		}
	    		if(p.getNo_of_bath()!=0){
	    			line.append(p1.getNo_of_bath()+",");
	    		}
	    		if(p.getNo_of_bedrooms()!=0){
	    			line.append(p1.getNo_of_bedrooms()+",");
	    		}
	    		if(p.getNo_of_floors()!=0){
	    			line.append(p1.getNo_of_floors()+",");
	    		}
	    		if(p.getYr_renovated()!=null){
	    			line.append(p1.getYr_renovated()+",");
	    		}
	    		if(p.getYr_built()!=null){
	    			line.append(Double.parseDouble(p1.getYr_built())+",");
	    		}
	    		/*if(p.getSale_dt()!=null){
	    			line.append(p1.getSale_dt()+",");
	    		}*/
	    		if(p.getZipcode()!= 0){
	    			line.append(p1.getZipcode()+",");
	    		}
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