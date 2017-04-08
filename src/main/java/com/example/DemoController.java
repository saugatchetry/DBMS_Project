package com.example;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.PropertyBean;
import com.example.business.UserBean;
import com.example.model.Property;
import com.example.model.User;

@RestController
class DemoController2{
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
	UserBean userBean = (UserBean) applicationContext.getBean("userBean");
	PropertyBean propertyBean = (PropertyBean)applicationContext.getBean("propertyBean");
	
	@RequestMapping(value = "/login",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public User secondPage(@RequestBody User user){
		User userOutput = userBean.getUser(user);
		return userOutput;
		
		
		/*anitha ranganathan*/
	}
	
	@RequestMapping(value = "/signUp",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public User signUp(@RequestBody User user){
		User userOutput = userBean.insertUser(user);
		return userOutput;
	}
	
	
	@RequestMapping(value = "/getPopularProperties",method=RequestMethod.GET,produces="application/json")
	public ArrayList<Property> getPopularProperties(){
		ArrayList<Property> popularProperty = propertyBean.getProperty();
		return popularProperty;
	}
}
