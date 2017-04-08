package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.UserBean;
import com.example.model.User;

@RestController
class DemoController2{
	
	@RequestMapping(value = "/secondPage",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public User secondPage(@RequestBody User user){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
		UserBean userBean = (UserBean) applicationContext.getBean("userBean");
		User userOutput = userBean.getUser(user);
		return userOutput;
		
		
		/*anitha ranganathan*/
	}
}
