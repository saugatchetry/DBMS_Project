angular.module('myApp').controller('SellController', ['$rootScope', '$scope', '$http','toastr', function($rootScope,$scope, $http, toastr) {
	function getCities(){
		$http({
	        method : "GET",
	        url : "getCities",	     
	    }).then(function(response) {
        	$scope.cities = response.data;
        	$scope.selectedCity = $scope.cities[0];
    	});		
	};
	
	$scope.uploadedImage = ""
	$scope.isFormValid = false;
	
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
	
	$scope.propertyProp = {
			'WoodenFlooring' : 0,
			'Carpet' : 0,
			'Parking' : 0,
			'PetFriendly' : 0,
			'Pool' : 0,
			'WaterFront' : 0,
			'View' : 0
	};
	
	$scope.submitSellerDetails = function(){
		if ($scope.sellDetails.$valid){
			if($rootScope.userDetails.id !== undefined || $rootScope.userDetails.id !== null){
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
						'propFeature': $scope.propertyProp																				
				}
				
				$http({
			        method : "POST",
			        url : "sell",
			        data : sellPropDet
			    }).then(function(response) {
		        	alert('Details saved');
		    	});																				
			}			
		}else{
			toastr.error('Form is Invalid', 'Please Fill Details Properly',{
				'position-class': 'toastr-bottom-left',
				'close-button':true,
			});		
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