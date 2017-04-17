package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.PredictBean;
import com.example.business.PropertyBean;
import com.example.business.SellBean;
import com.example.business.TrendsBean;
import com.example.business.UserBean;
import com.example.model.PredictProperty;
import com.example.model.Property;
import com.example.model.PropertySearch;
import com.example.model.Sell;
import com.example.model.User;

@RestController
class DemoController2{
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
	UserBean userBean = (UserBean) applicationContext.getBean("userBean");
	PropertyBean propertyBean = (PropertyBean)applicationContext.getBean("propertyBean");
	PredictBean predictBean = (PredictBean)applicationContext.getBean("predictBean");//Anitha changes
	SellBean sellBean = (SellBean)applicationContext.getBean("sellBean");//Anitha changes
	TrendsBean trendsBean = (TrendsBean)applicationContext.getBean("trendsBean");
	
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
	@RequestMapping(value = "/selling",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public Sell insertDetails(@RequestBody Sell prop){
		System.out.println(prop.getParking());
		System.out.println(prop.getCarpet());		
		Sell sellOutput = sellBean.insertDetails(prop);
		return sellOutput;
	}
	/*Anitha changes end*/
	@RequestMapping(value = "/estimateValue",method=RequestMethod.POST, consumes="application/json",produces="application/json")
	public PredictProperty getEstimate(@RequestBody PredictProperty p){
		PredictProperty propertyWithEstimatedValue = predictBean.predictValue(p);
		double currentEstimatedPrice = propertyWithEstimatedValue.getEstimatedValue();
		StringBuilder suggestions = new StringBuilder();
		if(p.getWaterFront() == 1){
			currentEstimatedPrice += 0.067*currentEstimatedPrice;
		}
		
		if(p.getWoodenFlooring() == 1){
			currentEstimatedPrice += 0.017*currentEstimatedPrice;
		}
		
		if(p.getCarpet() == 1){
			currentEstimatedPrice += 0.020*currentEstimatedPrice;
		}else{
			suggestions.append("\n" +"Applying Carpet on Floor can increase the value by: $" + Math.round(0.020*currentEstimatedPrice));			
		}
		
		if(p.getParking() == 1){
			currentEstimatedPrice += 0.039*currentEstimatedPrice;			
		}else{
			suggestions.append("\n" +"Giving Parking Facility can increase the value by: $" + Math.round(0.029*currentEstimatedPrice));
		}
		
		if(p.getPool() == 1){
			currentEstimatedPrice += 0.025*currentEstimatedPrice;
		}
		
		if(p.getView() == 1){
			currentEstimatedPrice += 0.085*currentEstimatedPrice;
		}
		
		if(p.getView() == 1){
			currentEstimatedPrice += 0.085*currentEstimatedPrice;
		}
		
		if(p.getFurnishStatus().equals("Furnished")){
			currentEstimatedPrice += 0.04*currentEstimatedPrice;
		}else if(p.getFurnishStatus().equals("SemiFurnished")){
			currentEstimatedPrice += 0.02*currentEstimatedPrice;
		}else{
			suggestions.append("\n" +"Furnishing and Renovation can increase the value by: $" + Math.round(0.03*currentEstimatedPrice)) ;
			currentEstimatedPrice -= 0.01*currentEstimatedPrice;
		}
		propertyWithEstimatedValue.setSuggestions(new String(suggestions));
		propertyWithEstimatedValue.setEstimatedValue(Math.round(currentEstimatedPrice));
						
		return propertyWithEstimatedValue;
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

	@RequestMapping(value="/getTotalRecords",method=RequestMethod.GET)
	public int showAllRecords(){
		System.out.println("Show all records called");
		int total = userBean.getTotalRecords();
		System.out.println("Total Records = "+total);
		return total;
	}

	@RequestMapping(value = "/insertImageByFile",method=RequestMethod.GET,produces="application/json")
	public boolean imsertImageData(){
		Boolean insertionResult = propertyBean.insertImageByFile();
		return insertionResult;
	}
	
	/*
	 * END-Point to search properties based on selected features
	 */
//	@RequestMapping(value = "/searchProperties",method=RequestMethod.POST,consumes="application/json",produces="application/json")
//	public ArrayList<Property> getSearchedProperties(@RequestBody Property property){
//		System.out.println("Controller hit");
//		ArrayList<Property> searchedProperty = propertyBean.getSearchedProperties(property);
//		return searchedProperty;
//	}
	
	/*
	 * END-Point to search properties based on selected features
	 */
	@RequestMapping(value = "/searchProperties",method=RequestMethod.POST,consumes="application/json",produces="application/json")
	public ArrayList<Property> getSearchedProperties(@RequestBody PropertySearch property){
		System.out.println("Controller hit");
		ArrayList<Property> searchedProperty = propertyBean.getSearchedProperties(property);
		return searchedProperty;
	}
	
	/*@RequestMapping(value = "/downloadImage", method = RequestMethod.POST, produces = "image/jpeg")
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
    }*/
	
	@RequestMapping(value = "/downloadImage/{imageId}", method = RequestMethod.GET, produces = "image/jpeg")
    public ResponseEntity<String> getPDF(@PathVariable(value="imageId") String imageid) {        
        try {        	        	
        	List<byte[]> images = propertyBean.getImages("1");  
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
	
	@RequestMapping(value = "/getAveragePriceByZipcode", method=RequestMethod.GET, produces = "application/json")
	public HashMap<String,String> getAveragePriceByZipcode(@RequestParam("zipcode") Integer zipcode){
		return trendsBean.getAveragePriceByZipcode(zipcode);
	}
	
	@RequestMapping(value = "/getPricesGroupedByZipcode", method=RequestMethod.GET, produces = "application/json")
	public HashMap<String,ArrayList<String>> getPricesGroupedByZipcode(){
		return trendsBean.getPricesGroupedByZipcode();
	}
	
	
	@RequestMapping(value = "/insertSearchedProperty", method=RequestMethod.GET, produces = "application/json")
	public User insertSearchedProperty(@RequestParam("userId") Integer userId,@RequestParam("propertyId") Long propertyId){
		System.out.println("Hit kiya");
		return userBean.insertSearchedProperty(userId,propertyId);
	}
	
	@RequestMapping(value = "/getPricesByCity", method=RequestMethod.GET, produces = "application/json")
	public HashMap<String,ArrayList<String>> getPricesByCity(@RequestParam("cityName") String cityName){
		return trendsBean.getPricesByCity(cityName);
	}
	
	@RequestMapping(value = "/getFeatureWidePrices", method=RequestMethod.GET, produces="application/json")
	public ArrayList<String> getFeatureWidePrices(@RequestParam("carpet") String carpet, @RequestParam("woodenFlooring") String woodenFlooring, @RequestParam("waterFront") String waterFront,
			@RequestParam("view") String view, @RequestParam("furnishType") String furnishType){
		return trendsBean.getFeatureWidePrices(carpet, woodenFlooring, waterFront, view, furnishType);
	}
	
	@RequestMapping(value = "/getDeltaByYear", method=RequestMethod.GET, produces="application/json")
	public LinkedHashMap<String,String> getDeltaByYear(@RequestParam("zipcode") String zipcode){
		return trendsBean.getDeltaByYear(zipcode);
	}
	
	@RequestMapping(value = "/getSalesByZipcode", method=RequestMethod.GET, produces="application/json")
	public HashMap<String, String> getSalesByZipcode(@RequestParam("zipcodes") String zipcodes, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate){
		return trendsBean.getSalesByZipcode(zipcodes, fromDate, toDate);
	}
	
}



