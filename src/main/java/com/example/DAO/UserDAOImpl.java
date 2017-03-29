package com.example.DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.KeyGenerator;
import com.example.model.User;

public class UserDAOImpl implements UserDAO {
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
	public User getUser(User user) {
		final User userOut = new User();
        String quer = "SELECT * from Registered_User where EMAIL = '" + user.getEmailId() + "' and USER_PASSWORD = '" + user.getPassword() + "'";
        return (User) jdbcTemplate.query(quer, new ResultSetExtractor<User>() {
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                	userOut.setId(rs.getString(1));
                	userOut.setFirstName(rs.getString(2));
                	userOut.setLastName(rs.getString(3));
                	userOut.setEmailId(rs.getString(4));
                	userOut.setPhoneNumber(rs.getString(5));
                	userOut.setPassword(rs.getString(6));
                }
                System.out.println(userOut);
                return userOut;
            }
        });
	}
	
	@Override
	public User insertUser(User user) {
		BigDecimal id = keyGenerator.generateKey("Registered_User", "reg_usr_id");
        String query = "INSERT into Registered_User values(?,?,?,?,?,?)";
        jdbcTemplate.update(query, new Object[] { id, user.getFirstName(), user.getLastName(),
        		user.getEmailId(), user.getPhoneNumber(), user.getPassword()
        	});
        user.setId(id.toString());
        return user;
	}

	
 
}
