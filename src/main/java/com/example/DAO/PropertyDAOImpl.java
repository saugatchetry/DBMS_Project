package com.example.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.model.Property;


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

}
