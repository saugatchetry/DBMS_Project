package com.example.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.example.KeyGenerator;
import com.example.model.Sell;

public class SellDAOImpl implements SellDAO{
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
	public Sell insertDetails(Sell s) {
		BigDecimal id = keyGenerator.generateKeySell("PROP_SEQ");
		BigDecimal id_feature;
		InputStream imageIs;
		try {
			if(s.getImage()!=null)
				imageIs = new FileInputStream(s.getImage());
			else imageIs=null;
			LobHandler lobHandler = new DefaultLobHandler(); 
			ArrayList<String> featureVal =new ArrayList<String>();
			ArrayList<String> features= new ArrayList<String>();
			if(s.getPropFeature()!=null){
				features = s.getPropFeature().getFeatureName();
				featureVal = s.getPropFeature().getFeatureValues();
			}
	        String query = "INSERT into property values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//	        System.out.println("id "+id);
//	        System.out.println("Values : "+ id+" , "+s.getSquareFeet()+" , "+ s.getNumberOfBedrooms()+" , "+
//    		s.getNumberOfFloors()+" , "+ s.getNumberOfBathrooms()+" , "+s.getYearRenovated()+" , "+s.getYearBuilt()+" , "+
//    		" ");
//	        System.out.println(","+"UNSOLD"+" , "+s.getDescription()+" , "+""+" , "+ s.getPrice()+" , "+s.getLatitude()+" , "+s.getLongitude()+" , "+s.getStreet()+" , "+
//    		s.getZipcode()+" , "+s.getCity()+" , "+s.getSellerId());
            jdbcTemplate.update(query, new Object[] {id,s.getSquareFeet(), s.getNumberOfBedrooms(),
            		s.getNumberOfFloors(), s.getNumberOfBathrooms(),s.getYearRenovated(),s.getYearBuilt(),
            		(s.getImage()!=null ?new SqlLobValue(imageIs, (int)s.getImage().length(), lobHandler):null),"UNSOLD",s.getDescription(),"", s.getPrice(),s.getLatitude(),s.getLongitude(),s.getStreet(),
            		s.getZipcode(),s.getCity(),s.getSellerId()
            	});
            s.setPropId(Integer.parseInt(id.toString()));
            for(int i=0; i<features.size(); i++){
            	if(featureVal.get(i)!=null){
		            id_feature = keyGenerator.generateKeySell("PROP_FEAT_SEQ");
		            query = "INSERT into property_feature values(?,?,?,?)";
			        jdbcTemplate.update(query, new Object[] {id_feature,id,features.get(i),featureVal.get(i)
		            	});
            		}
	         	}
            
            Boolean success=insertImage(s.getPath(), id.toString());
            if(!success)
            	throw new FileNotFoundException();
            s.setErrorStatus(1);
            s.setErrorMessage("");
            }
            catch(InvalidResultSetAccessException e){
            	System.out.println("Invalid thing Exception is "+e);
            }
            catch(DataAccessException e){
            	System.out.println("Exception is "+e.getCause().getMessage());
            	s.setErrorStatus(0);
            	s.setErrorMessage(e.getCause().getMessage());
            	return s;
            }
			catch (FileNotFoundException e) {
				System.out.println("File Not found " + e.getMessage());
				s.setErrorStatus(0);
            	s.setErrorMessage(e.getCause().getMessage());
				return s;
			}	
            return s;
	}
	
	public boolean insertImage(String path, String id){		
		try {
			final File image = new File(path);
			final InputStream imageIs = new FileInputStream(image);   
			LobHandler lobHandler = new DefaultLobHandler(); 
			int result = jdbcTemplate.update(
					"INSERT INTO Image (IMAGE_ID, IMAGE_DATA) VALUES (?, ?)",
					new Object[] {
							id,
							new SqlLobValue(imageIs, (int)image.length(), lobHandler),
					},
					new int[] {Types.INTEGER, Types.BLOB});
			if (result > 0){
				return true;
			}
			
			return false;
		} catch (DataAccessException e) {
			System.out.println("DataAccessException " + e.getMessage());
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("DataAccessException " + e.getMessage());
			return false;
		}		
	}


}
