package com.example.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.model.User;

public class UserDAOImpl implements UserDAO {
	private JdbcTemplate jdbcTemplate;
	 
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
 
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
                return userOut;
            }
        });
	}
 
}
