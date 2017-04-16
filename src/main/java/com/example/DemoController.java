package com.example;

import java.util.ArrayList;
import java.util.List;
import sun.misc.*;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	//PredictBean predictBean = (PredictBean)applicationContext.getBean("predictBean");//Anitha changes
	
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

	/*Anitha changes begin*/
	@RequestMapping(value = "/getCities",method=RequestMethod.GET,produces="application/json")
	public ArrayList<String> getCities(){
		ArrayList<String> popularProperty = propertyBean.getCities();
		return popularProperty;
	}

	
//	@RequestMapping(value = "/estimateValue",method=RequestMethod.POST,produces="application/json")
//	public double getEstimate(@RequestBody PredictProperty p){
//		double estimated_value = predictBean.predictValue(p);
//		return estimated_value;
//	}
	
	@RequestMapping(value = "/insertImage",method=RequestMethod.POST,produces="application/json")
	public boolean imsertImageData(@RequestBody String image){
		System.out.println(image);
		String[] parts = image.split(",");		
		byte[] arr = javax.xml.bind.DatatypeConverter.parseBase64Binary(parts[1]);
		System.out.println("Done");
		Boolean insertionResult = propertyBean.insertImage(arr);
		return insertionResult;
	}
	
	@RequestMapping(value = "/downloadImage", method = RequestMethod.POST, produces = "image/jpeg")
    public ResponseEntity<String> getPDF(@RequestBody String imageId) {        
        try {        	        	
        	List<byte[]> images = propertyBean.getImages(imageId);  
        	System.out.println("Inside this ");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/jpeg"));
            String filename = "image1.jpeg";
            headers.setContentDispositionFormData(filename, filename);
            String imageEncoded = Base64.encode(images.get(0));           
            ResponseEntity<String> response = new ResponseEntity<String>(imageEncoded,headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}
