package com.example.DAO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder {
	
	public String encode(CharSequence passwd){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(passwd);
		return hashedPassword;
	}
}
