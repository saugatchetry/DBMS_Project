package com.example.DAO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.KeyGenerator;
import com.example.model.BuyerPreference;
import com.example.model.User;

import oracle.net.aso.i;

public class UserDAOImpl implements UserDAO {
	private JdbcTemplate jdbcTemplate;
	private KeyGenerator keyGenerator;
	private SimpleJdbcCall jdbcCall;
	
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
                	userOut.setErrorStatus(1);
                }
                else{
                	userOut.setErrorStatus(0);
                	userOut.setErrorMessage("Login error occured");
                	System.out.println("Failed to login");
                	return userOut;
                	
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
	
	@Override
	public int getTotalRecords(){
		jdbcCall =  new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("Total_rows")
				 .declareParameters(new SqlOutParameter("rec_count", Types.INTEGER));;
		//SqlParameterSource in = new MapSqlParameterSource().addValue() ;
		
		Map<String, Object> out = jdbcCall.execute();
		
		int totalRecords = (int) out.get("rec_count");
		//System.out.println("Result of procedure :- "+out.get("rec_count"));
		
		//String query = "SELECT COUNT(*) FROM Registered_User";
		return  totalRecords;
	}

	@Override
	public User insertSearchedProperty(Integer userId, Long propertyId) {
		
		String query1 = "select (nvl(query_count,0)+1) from query_searched where reg_usr_id = "+userId +" and property_id = "+propertyId;
		System.out.println("QueryT :- "+query1);
		Integer count = 0;
		try{
			count = getJdbcTemplate().queryForObject(query1,Integer.class);
		}
		catch(Exception e){
			count = 0;
		}
		System.out.println("count = "+count);
		String query = "";
		if(count == 0 || count == null){
		
		query = "INSERT into query_searched values( "+userId+","+propertyId+"," +(count+1)+")";
		}
		else{
		
		query = "UPDATE query_searched set query_count = "+ (count+1) + " where reg_usr_id = "+userId +" and property_id = "+propertyId;
				
		}
		//System.out.println("queryzzies = "+query);
		 try{
		      	System.out.println("tests query :- "+query);
		        jdbcTemplate.update(query);
		       
		 	}
		        catch(InvalidResultSetAccessException e){
		        	//System.out.println("query = "+query);
		        	System.out.println("Invalid thing Exception is "+e);
		        }
		        catch(DataAccessException e){
		        	
		        	System.out.println("Exception is "+e.getCause().getMessage());
		        	
		        	return null;
		        }
		        return null;
		
	}
	
	public boolean insertBuyerPref(BuyerPreference buyerPreference){
		System.out.println("Inserting buyer Preferences");
        String query = "INSERT into BUYER values(?,?,?,?,?)";
        try{
        jdbcTemplate.update(query, new Object[] { buyerPreference.getId(), buyerPreference.getMinArea(), buyerPreference.getMaxArea(),
        		buyerPreference.getMinBudget(), buyerPreference.getMaxBudget()
        	});        
        }
        catch(InvalidResultSetAccessException e){
        	System.out.println("Invalid thing Exception is "+e);
        }
        catch(DataAccessException e){
        	
        	System.out.println("Exception is "+e.getCause().getMessage());        	
        	return false;
        }
        return true;
	} 
}
