angular.module('myApp').controller('BuyController', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	$scope.user = $rootScope.userDetails;
	$scope.pageLoad = false;
	$scope.loadImage = function(){
		$http({
			method : "GET",
			url : "downloadImage",	       
		}).then(function(response) {			
			$scope.image = response.data;
		});
	}
	
	$scope.priceSlider = {
		    minValue: 0,
		    maxValue: 120000000,
		    options: {
		      floor: 0,
		      ceil: 10000,
		      step: 1
		    }
	};
	
	$scope.sqFeetSlider = {
		    minValue: 0,
		    maxValue: 120000000,
		    options: {
		      floor: 0,
		      ceil: 10000,
		      step: 1
		    }
	};
	
	$scope.searchProperty = function(){
		$scope.pageLoad = true;
		$http({
	        method : "GET",
	        url : "searchProperties",
	        data : {
	        	'numberOfBedrooms' : $scope.totalBedrooms,
	        	'numberOfBathrooms' : $scope.totalBathrooms,
	        	'numberOfFloors' : $scope.totalFloors,
	        	'priceFrom' : $scope.priceSlider.minValue,
	        	'priceTo' : $scope.priceSlider.maxValue,
	        	'squareFeetFrom' : $scope.sqFeetSlider.minValue,
	        	'squareFeetTo' : $scope.sqFeetSlider.maxValue
	        }
	    }).then(function(response) {
	    	$scope.pageLoad = false;
        	$scope.searchedProperties = response.data;
        	console.log('total records = '+$scope.searchedProperties.length);
    	});
	}
	  
	  $scope.bedrooms = ['1', '2', '3', '4'];
	  $scope.totalBedrooms = [];
	  $scope.bathrooms = ['1', '2', '3', '4'];
	  $scope.totalBathrooms = [];
	  $scope.floors = ['1', '2', '3', '4'];
	  $scope.totalFloors = [];
	  
	  $scope.toggleBedroomSelection = function toggleSelection(bedroom) {
	        var idx = $scope.totalBedrooms.indexOf(bedroom);
	        if (idx > -1) {
	          // is currently selected
	          $scope.totalBedrooms.splice(idx, 1);
	         }
	         else {
	           // is newly selected
	           $scope.totalBedrooms.push(bedroom);
	         }
	  };
	  
	  $scope.toggleBathroomSelection = function toggleSelection(bathroom) {
	        var idx = $scope.totalBathrooms.indexOf(bathroom);
	        if (idx > -1) {
	          // is currently selected
	          $scope.totalBathrooms.splice(idx, 1);
	         }
	         else {
	           // is newly selected
	           $scope.totalBathrooms.push(bathroom);
	         }
	  };
	  
	  $scope.toggleFloorSelection = function toggleSelection(floor) {
	        var idx = $scope.totalFloors.indexOf(floor);
	        if (idx > -1) {
	          // is currently selected
	          $scope.totalFloors.splice(idx, 1);
	         }
	         else {
	           // is newly selected
	           $scope.totalFloors.push(floor);
	         }
	  };
	  
	  $scope.provideSellerDetails = function(seller){
		  console.log($rootScope.userDetails);
		  if($rootScope.userDetails == undefined){
			  $rootScope.loginModal = true;
		  }
		  else{
			  $scope.currentSeller = seller;
			  $scope.sellerDetailsModal = true;
		  }
	  }
	
}]);