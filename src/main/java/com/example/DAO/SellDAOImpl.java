package com.example.DAO;

import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.model.Sell;

public class SellDAOImpl implements SellDAO{
	
	private JdbcTemplate jdbcTemplate;
	
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	@Override
	public Sell insertDetails(Sell s) {
        String query = "INSERT into property values(prop_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   
		
		return null;
	}


}
