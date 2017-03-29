package com.example;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class KeyGenerator {
	private JdbcTemplate jdbcTemplate;
	 
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	public BigDecimal generateKey(String tableName, String idColumnName){
		String query = "SELECT * FROM " + tableName + " WHERE ROWNUM <=1 ORDER BY " + idColumnName + " DESC";
		BigDecimal str =  jdbcTemplate.query(query, new ResultSetExtractor<BigDecimal>() {
            public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()) {
                	return rs.getBigDecimal(idColumnName);
                }
                return null;
            }
        });
		return str.add(new BigDecimal(1));
	}
	
}
