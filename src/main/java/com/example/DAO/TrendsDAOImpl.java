package com.example.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.model.Property;

public class TrendsDAOImpl implements TrendsDAO {
	private JdbcTemplate jdbcTemplate;
	
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public HashMap<String, String> getAveragePriceByZipcode(Integer zipcode){
    	final HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		String quer = "select avg(price),year_built from property where zipcode = " + zipcode + " group by year_built having avg(price) >0";
		return (HashMap<String, String>) jdbcTemplate.query(quer, new ResultSetExtractor<HashMap<String, String>>() {
            public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	HashMap<String, String> returnList = new HashMap<String, String>();
                while (rs.next()) {
                	returnList.put(rs.getString(2),rs.getString(1));
                }
                return returnList;
            }
        });
		
    }
    
    public HashMap<String, ArrayList<String>> getPricesGroupedByZipcode(){
		String quer = "select * from (select avg(price),min(price),max(price),median(price),zipcode from property group by zipcode having avg(price) > 0 and min(price)>0 and max(price)>0 and median(price)>0 and avg(price)<> min(price) and min(price)<> max(price) and max(price)<> median(price)) where rownum < 21";
		return (HashMap<String, ArrayList<String>>) jdbcTemplate.query(quer, new ResultSetExtractor<HashMap<String, ArrayList<String>>>() {
            public HashMap<String, ArrayList<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	HashMap<String, ArrayList<String>> returnList = new HashMap<String, ArrayList<String>>();
                while (rs.next()) {
                	ArrayList<String> valueList = new ArrayList<String>();
                	valueList.add(rs.getString(2));
                	valueList.add(rs.getString(1));
                	valueList.add(rs.getString(4));
                	valueList.add(rs.getString(3));
                	returnList.put(rs.getString(5),valueList);
                }
                return returnList;
            }
        });
    }
    
    
    public HashMap<String, ArrayList<String>> getPricesByCity(String cityName){
		String quer = "select round(max(price),2), round(avg(price),2),  round(median(price),2), zipcode from property group by (city, zipcode) having city like '" + cityName + "' and avg(price) <> max(price) order by max(price) desc";
		return (HashMap<String, ArrayList<String>>) jdbcTemplate.query(quer, new ResultSetExtractor<HashMap<String, ArrayList<String>>>() {
            public HashMap<String, ArrayList<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	HashMap<String, ArrayList<String>> returnList = new HashMap<String, ArrayList<String>>();
                while (rs.next()) {
                	ArrayList<String> valueList = new ArrayList<String>();
                	valueList.add(rs.getString(1));
                	valueList.add(rs.getString(2));
                	valueList.add(rs.getString(3));
                	returnList.put(rs.getString(4),valueList);
                }
                return returnList;
            }
        });
    }
}
