angular.module('myApp').controller('SellController', ['$rootScope', '$scope', '$http','toastr', function($rootScope,$scope, $http, toastr) {
	function getCities(){
		$scope.loading = true;
		$http({
	        method : "GET",
	        url : "getCities",	     
	    }).then(function(response) {
        	$scope.cities = response.data;
        	$scope.selectedCity = $scope.cities[0];
        	$scope.loading = false;
    	});		
	};
	
	$scope.uploadedImage = ""
	$scope.isFormValid = false;
	setAdvanceDeatures();
	
	getCities();
	
	 if (navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(function(position){
		      $scope.$apply(function(){
		    	  $scope.lat = Math.round((position.coords.latitude)*100)/100;
		    	  $scope.longitude = Math.round((position.coords.longitude)*100)/100;
		        $scope.coords = $scope.lat + " " + $scope.longitude;
		      });
		    });
	 }
	
	 
	function setAdvanceDeatures(){
		$scope.propertyProp = {
				'woodenFlooring' : 0,
				'carpet' : 0,
				'parking' : 0,
				'petFriendly' : 0,
				'pool' : 0,
				'waterFront' : 0,
				'view' : 0
		};
	}
	
	function clearAll(){
		$scope.livingArea = '';
		$scope.bedroom = '';
		$scope.floors = '';
		$scope.bathroom = '';
		$scope.yearRenovated = '';
		$scope.yearBuilt = '';
		$scope.imageData = '';
		$scope.comments = '';
		$scope.expectedRate = '';
		$scope.lat = '';
		$scope.longitude = '';
		$scope.street = '';
		$scope.zipcode = '';
		$scope.selectedCity = '';
		$scope.isNegotiable = '';
		$scope.furnishType = '';
		$scope.coords = '';
	}
		
	$scope.submitSellerDetails = function(){
		if ($scope.sellDetails.$valid){
			alert($rootScope.userDetails);
			if($rootScope.userDetails !== undefined || $rootScope.userDetails !== null){
				var sellPropDet = {
						'squareFeet':$scope.livingArea,
						'numberOfBedrooms':$scope.bedroom,
						'numberOfFloors':$scope.floors,
						'numberOfBathrooms':$scope.bathroom,
						'yearRenovated':$scope.yearRenovated,
						'yearBuilt':$scope.yearBuilt,
						'status':null,
						'path' : $scope.imageData,
						'description':$scope.comments,
						'saleDate': null,
						'price':$scope.expectedRate,
						'latitude':$scope.lat,
						'longitude':$scope.longitude,
						'street':$scope.street,
						'zipcode':$scope.zipcode,
						'city': $scope.selectedCity,
						'sellerId':$rootScope.userDetails.id,						
						'negotiable':$scope.isNegotiable,
						'woodenFlooring':$scope.propertyProp.woodenFlooring,
						'carpet':$scope.propertyProp.carpet,
						'parking':$scope.propertyProp.parking,
						'petFriendly':$scope.propertyProp.petFriendly,
						'pool':$scope.propertyProp.pool,
						'waterFront':$scope.propertyProp.waterFront,
						'view':$scope.propertyProp.view,
						'furnishType': $scope.furnishType,
				}				
				$http({
			        method : "POST",
			        url : "selling",
			        data : sellPropDet
			    }).then(function(response) {
			    	var sellerData = response.data;
		        	toastr.success('Details saved for propertyid:' + sellerData.propId);
		        	clearAll();
		        	setAdvanceDeatures();		        	
		    	});																				
			}
			else{
				toastr.error('User must be looged in', 'Please Try Again');
			}
		}else{
			toastr.error('Form is Invalid', 'Please Fill Details Properly');		
		}		
	};
	
	$scope.$watch('editItem._attachments_uri.image', function(newValue, oldValue){
	    console.log('being watched oldValue:', oldValue, 'newValue:', newValue);
	    $scope.imageData = newValue;
	   /* $http({
	        method : "POST",
	        url : "insertImage",
	        data : JSON.stringify(newValue)
	    }).then(function(response) {
        	alert('Details saved');
    	});		*/
	  }, true);	
}]);