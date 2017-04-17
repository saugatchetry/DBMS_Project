package com.example.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;


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
    
    public ArrayList<String> getFeatureWidePrices(String carpet, String woodenFlooring, String waterFront, String view,	String furnishType){
		StringBuilder query = new StringBuilder("Select avg(price) , median(price) , max(price), min(price) from property where PROPERTY_ID in (");
		boolean flag = false;
		if(waterFront.equals("false") == false){
			query.append("Select PROPERTY_ID from PROPERTY_FEATURE where Upper(FEATURE_NAME) = 'WATERFRONT' and FEATURE_VALUES = '1' ");
			flag = true;
		}
		if(woodenFlooring.equals("false") == false){
			if(flag == true)
				query.append(" INTERSECT ");
			query.append(" Select property_id from PROPERTY_FEATURE where Upper(FEATURE_NAME)='CARPET' and FEATURE_VALUES = '1' ");
			flag = true;
    	}	
		if(waterFront.equals("false") == false){
			if(flag == true)
				query.append(" INTERSECT ");
			query.append(" Select PROPERTY_ID from PROPERTY_FEATURE where  Upper(FEATURE_NAME) = 'VIEW' and FEATURE_VALUES = '1' ");
			flag = true;
		}
		if(view.equals("false") == false){
			if(flag == true)
				query.append(" INTERSECT ");
			query.append(" Select property_id from PROPERTY_FEATURE where Upper(FEATURE_NAME)='WOODEN FL' and FEATURE_VALUES = '1' ");
			flag = true;
    	}
		if(furnishType.equals("false") == false){
			if(flag == true)
				query.append(" INTERSECT ");
			query.append(" Select property_id from PROPERTY_FEATURE where Upper(FEATURE_NAME)= 'FURNISHTYPE' and UPPER(FEATURE_VALUES) = Upper('" + furnishType + "')");
			flag = true;
		}
		query.append(")");
		System.out.println(query);
		
		return (ArrayList<String>) jdbcTemplate.query(query.toString(), new ResultSetExtractor<ArrayList<String>>() {
            public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	ArrayList<String> valueList = new ArrayList<String>();
                while (rs.next()) {
                	valueList.add(rs.getString(1));
                	valueList.add(rs.getString(2));
                	valueList.add(rs.getString(3));
                	valueList.add(rs.getString(4));
                }
                return valueList;
            }
        });
    }
    
    public LinkedHashMap<String, String> getDeltaByYear(String zipcode){
		String quer = "select b.* , b.value -lag(b.value) over(order by b.r) delta from(select rownum r , a.* from(select avg(price) as value  , yearS from (select price,year_built yearS from property where zipcode = " + zipcode + " ) group by yearS order by yearS) a) b order by yearS";
		return (LinkedHashMap<String, String>) jdbcTemplate.query(quer, new ResultSetExtractor<LinkedHashMap<String, String>>() {
            public LinkedHashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	LinkedHashMap<String, String> returnList = new LinkedHashMap<String, String>();
                while (rs.next()) {
                	returnList.put(rs.getString("years"), rs.getString("delta"));
                }
                return returnList;
            }
        });
    }
    
    public HashMap<String, String> getSalesByZipcode(String zipcodes, String fromDate, String toDate){
    	String quer = "select count(*) , zipcode from property where zipcode in ( " + zipcodes + " ) and (SALE_DATE between TO_DATE('" + fromDate + "01', 'yyyymmdd') AND  TO_DATE('" + toDate + "25', 'yyyymmdd')) group by (zipcode)";
    	return (HashMap<String, String>) jdbcTemplate.query(quer, new ResultSetExtractor<HashMap<String, String>>() {
            public HashMap<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
            	HashMap<String, String> returnList = new HashMap<String, String>();
                while (rs.next()) {
                	returnList.put(rs.getString("zipcode"), rs.getString(1));
                }
                return returnList;
            }
        });
    }
    
    
    
}
