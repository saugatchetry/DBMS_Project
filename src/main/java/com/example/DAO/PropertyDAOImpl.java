package com.example.DAO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.tree.RowMapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.example.model.Property;
import com.example.model.PropertyFeature;
import com.example.model.PropertySearch;
import com.example.model.User;


public class PropertyDAOImpl implements PropertyDAO{
	private JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public ArrayList<Property> getProperty() {
		
		final Property property = new Property();
		String quer = "select Property.property_id, YEAR_BUILT, SQ_FT, PRICE,NUMBER_OF_BEDROOMS,NUMBER_OF_BATHROOM, PROPERTY.NUMBER_OF_FLOORS from (select property_id as p , sum(query_count) as counter from query_searched group by PROPERTY_ID order by counter DESC ), PROPERTY where Property.property_id = p  and rownum < 7";
		return (ArrayList<Property>) jdbcTemplate.query(quer, new ResultSetExtractor<ArrayList<Property>>() {
            public ArrayList<Property> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	ArrayList<Property> returnList = new ArrayList<>();
                while (rs.next()) {
                	Property prop = new Property();
                	prop.setId(rs.getLong(1));
                	prop.setYearBuilt(rs.getString(2));
                	prop.setSquareFeet(rs.getFloat(3));
                	prop.setPrice(rs.getFloat(4));
                	prop.setNumberOfBedrooms(rs.getInt(5));
                	prop.setNumberOfBathrooms(rs.getInt(6));
                	prop.setNumberOfFloors(rs.getInt(7));
                	returnList.add(prop);
                }
                
                System.out.println("Size =  "+returnList.size());
                return returnList;
            }
        });
		
		
	}
	
	@Override
	public ArrayList<Property> getSearchedProperties(PropertySearch propertySearch) {
		final Property searchedProperty = new Property();
		
		ArrayList<Integer> bedrooms = propertySearch.getTotalBedrooms();
		ArrayList<Integer> bathrooms = propertySearch.getTotalBathrooms();
		ArrayList<Integer> floors = propertySearch.getTotalFloors();
		int startingPrice = propertySearch.getStartingPrice();
		int endingPrice = propertySearch.getEndingPrice();
		int startingSqFt = propertySearch.getStartingSqFt();
		int endingSqFt = propertySearch.getEndingSqFt();
		int pageNo= propertySearch.getCurrentPageNumber();
		if(startingPrice==0)
			startingPrice=1;
		
		
		boolean flag=false;
		boolean flagB=false;
		ArrayList<ArrayList<Integer>> a = new ArrayList<>();
		a.add(bedrooms);
		a.add(bathrooms);
		a.add(floors);
		StringBuilder query = new StringBuilder("SELECT * FROM (	"
				+ "SELECT * FROM ("
				+ "SELECT P.* , S.* ,ROWNUM R FROM PROPERTY P INNER JOIN REGISTERED_USER S ON S.REG_USR_ID = P.SELLER_ID WHERE ");
		StringBuilder query2 ;
		//Collections.sort(bedrooms);
		for(int j=0 ; j<a.size();j++){
			query2 = new StringBuilder();
			System.out.println("size of list = "+j+" = "+a.get(j).size());
			if(a.get(j).size()<6 && a.get(j).size()>1){
				if(flagB)
					query.append(" AND ");
					query2.append("(");
				for(int i = 1; i <a.get(j).size();i++){
					flagB=true;
					if(j==0){
						if(flag)
							query2.append(" OR ");
						flag=true;
						query2.append("NUMBER_OF_BEDROOMS = "+a.get(j).get(i));
					}
					else if(j == 1){
						if(flag)
							query2.append(" OR ");
						flag=true;
						query2.append("NUMBER_OF_BATHROOM = "+a.get(j).get(i));
					}
					else{
						if(flag)
							query2.append(" OR ");
						flag=true;
						query2.append("NUMBER_OF_FLOORS = "+a.get(j).get(i));
					}

				}
				query2.append(")");
			}
			flag =false;
			
			query.append(query2);					
		}		
		
		if(flagB)
			query.append(" AND ");
		
		if(propertySearch.getZipcode() > 10000){
			query.append(" zipcode=" + propertySearch.getZipcode() + " AND ");
		}	
		
		query.append("( SQ_FT BETWEEN "+startingSqFt+" AND "+endingSqFt+") AND ( PRICE BETWEEN  "+startingPrice+" AND "+endingPrice+")");
		query.append("order by price) A ) WHERE R<= ("+pageNo+"*10) and R >= (("+pageNo+"-1)*10+1)");
		
		
		System.out.println("query build is "+query);		
		
		
		return (ArrayList<Property>) jdbcTemplate.query(query.toString(), new ResultSetExtractor<ArrayList<Property>>() {
            public ArrayList<Property> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	ArrayList<Property> returnList = new ArrayList<>();
                while (rs.next()) {
                	Property prop = new Property();
                	prop.setId(rs.getLong(1));
                	prop.setYearBuilt(rs.getString(7));
                	prop.setSquareFeet(rs.getFloat(2));
                	prop.setPrice(rs.getFloat(12));
                	prop.setNumberOfBedrooms(rs.getInt(3));
                	prop.setNumberOfBathrooms(rs.getInt(5));
                	prop.setNumberOfFloors(rs.getInt(4));
                	//System.out.println("NumofFloorrs = "+rs.getInt(7));
                	User seller = new User();
                	seller.setId(rs.getString("REG_USR_ID"));
                	seller.setEmailId(rs.getString("EMAIL"));
                	seller.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                	seller.setFirstName(rs.getString("FIRST_NAME"));
                	seller.setLastName(rs.getString("LAST_NAME"));
                	prop.setSeller(seller);
                	
                	prop.setPropertyFeatures(getPropertyFeatures(rs.getLong(1)));
                	
                	returnList.add(prop);
                }

                return returnList;
            }});
		
		
		
		
		
}  
	
	
	public String getPropertyFeatures(long id){
		
		
		String query2 = "SELECT rtrim (xmlagg (xmlelement(e,FEATURE_NAME||',')).extract ('//text()'), ' ') AS STR FROM  PROPERTY_FEATURE where property_id = "+id;
		//String s2 =  jdbcTemplate.queryForObject(query2, String.class);
		String s="";
		String quer = "SELECT  FEATURE_NAME , FEATURE_VALUES FROM PROPERTY_FEATURE WHERE PROPERTY_ID = "+id;
		List<Map<String, Object>> rs = this.jdbcTemplate.queryForList(quer);
		for(Map r1: rs){
		
			if(r1.get("FEATURE_NAME").equals("FURNISHTYPE"))
				s+=r1.get("FEATURE_VALUES")+",";
			else
				if(r1.get("FEATURE_VALUES").equals("1"))
					s+=r1.get("FEATURE_NAME")+",";
		}
		//System.out.println("HEllo u fucking drained me out "+ s);
		return s;
	}

	@Override
	public ArrayList<String> getCities() {
		String quer = "select * from cities";
		return (ArrayList<String>) jdbcTemplate.query(quer, new ResultSetExtractor<ArrayList<String>>() {
            public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	ArrayList<String> returnList = new ArrayList<>();
                while (rs.next()) {
                	String s="";
                	s+=rs.getString(1)+','+rs.getString(2);
                	returnList.add(s);
                }
                
                System.out.println("Size =  "+returnList.size());
                return returnList;
            }
        });
		
		//String query = "SELECT * FROM PROPERTY WHERE "
		//return null;
	}

	@Override
	public boolean insertImage(byte[] imageArr){		
		try {
			//final File image = new File("C:\\Users\\Nishant\\Desktop\\spring17\\DBMSPROJ\\DBMS_Project\\src\\main\\resources\\static\\img\\prop2.jpg");
			//final InputStream imageIs = new FileInputStream(image);
			InputStream imageIs = new ByteArrayInputStream(imageArr);
			LobHandler lobHandler = new DefaultLobHandler(); 
			int result = jdbcTemplate.update(
					"INSERT INTO Image (IMAGE_ID, IMAGE_DATA) VALUES (?, ?)",
					new Object[] {
							90026,
							new SqlLobValue(imageIs, (int)imageArr.length, lobHandler),
					},
					new int[] {Types.INTEGER, Types.BLOB});
			if (result > 0){
				return true;
			}
			
			return false;
		} catch (DataAccessException e) {
			System.out.println("DataAccessException " + e.getMessage());
			return false;
		}		
	}
	
	public boolean insertImageByFile(){		
		try {
			final File image = new File("C:\\Users\\Nishant\\Desktop\\spring17\\DBMSPROJ\\DBMS_Project\\src\\main\\resources\\static\\img\\prop2.jpg");
			final InputStream imageIs = new FileInputStream(image);			
			LobHandler lobHandler = new DefaultLobHandler(); 
			int result = jdbcTemplate.update(
					"INSERT INTO Image (IMAGE_ID, IMAGE_DATA) VALUES (?, ?)",
					new Object[] {
							90026,
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	@Override
	public List<byte[]> getImages(String imageId){  
		String queryString = "Select * from Image where IMAGE_ID = " + imageId;
		LobHandler lobHandler = new DefaultLobHandler();
		return jdbcTemplate.query(queryString,new ResultSetExtractor<List<byte[]>>(){  
			@Override  
			public List<byte[]> extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  

				List<byte[]> list=new ArrayList<byte[]>();  
				while(rs.next()){  					
					list.add(lobHandler.getBlobAsBytes(rs,"IMAGE_DATA"));  
				}  
				return list;  
			}  
		});  
	}

	@Override
	public ArrayList<Property> getRecentProperties(String userId) {
		final Property property = new Property();
		String quer = "select Property.property_id, YEAR_BUILT, SQ_FT, PRICE,NUMBER_OF_BEDROOMS,NUMBER_OF_BATHROOM, PROPERTY.NUMBER_OF_FLOORS from (select property_id as p , sum(query_count) as counter from query_searched where reg_usr_id = "+ userId +"  group by PROPERTY_ID order by counter DESC ), PROPERTY where Property.property_id = p  and rownum < 7";
		return (ArrayList<Property>) jdbcTemplate.query(quer, new ResultSetExtractor<ArrayList<Property>>() {
            public ArrayList<Property> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	ArrayList<Property> returnList = new ArrayList<>();
                while (rs.next()) {
                	Property prop = new Property();
                	prop.setId(rs.getLong(1));
                	prop.setYearBuilt(rs.getString(2));
                	prop.setSquareFeet(rs.getFloat(3));
                	prop.setPrice(rs.getFloat(4));
                	prop.setNumberOfBedrooms(rs.getInt(5));
                	prop.setNumberOfBathrooms(rs.getInt(6));
                	prop.setNumberOfFloors(rs.getInt(7));
                	returnList.add(prop);
                }
                
                System.out.println("Size =  "+returnList.size());
                return returnList;
            }
        });
	}

}
