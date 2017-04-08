package com.example.DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		System.out.println("hashedPassword while login in:-"+hashedPassword);
		
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
                else{
                	user.setErrorStatus(0);
                	user.setErrorMessage("Login error occured");
                	System.out.println("Failed to login");
                	return user;
                	
                }
                System.out.println(userOut);
                
                return userOut;
            }
        });
	}
	
	@Override
	public User insertUser(User user) {
		BigDecimal id = keyGenerator.generateKey("Registered_User", "reg_usr_id");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());

		System.out.println("hashedPassword:-"+hashedPassword);
        String query = "INSERT into Registered_User values(?,?,?,?,?,?)";
        try{
        jdbcTemplate.update(query, new Object[] { id, user.getFirstName(), user.getLastName(),
        		user.getEmailId(), user.getPhoneNumber(), user.getPassword()
        	});
        user.setId(id.toString());
        user.setErrorStatus(1);
        user.setErrorMessage("");
        }
        catch(InvalidResultSetAccessException e){
        	System.out.println("Invalid thing Exception is "+e);
        }
        catch(DataAccessException e){
        	
        	System.out.println("Exception is "+e.getCause().getMessage());
        	user.setErrorStatus(0);
        	user.setErrorMessage(e.getCause().getMessage());
        	return user;
        }
        return user;
	}

	
 
}
