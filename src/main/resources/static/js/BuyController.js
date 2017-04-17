angular.module('myApp').controller('BuyController', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	$scope.user = $rootScope.userDetails;
	
	$scope.pageNumber = 1;
		
	var imageList = [];
	$scope.nextPage = function(){
		$scope.pageNumber = $scope.pageNumber + 1;
		$scope.searchProperty(0);
	}
	
	$scope.prevPage = function(){
		if ($scope.pageNumber > 1){
			$scope.pageNumber = $scope.pageNumber - 1;
		}
		$scope.searchProperty(0);
	}
	
	$scope.pageLoad = false;
	$scope.loadImage = function(imageId){
		$http({
			method : "GET",
			url : "downloadImage/" + imageId,
		}).then(function(response) {			
			imageList.push(response.data);
		});
	}
	
	$scope.getPopularProperties = function(){
		$http({
			method : "GET",
			url: "getPopularProperties"
		}).then(function(response){
			var images = ['prop1.jpg', 'prop2.jpg', 'prop3.jpg', 'prop4.jpg', 'prop5.jpg', 'prop6.png'];
			$scope.searchedProperties = response.data;
			console.log($scope.properties);
			for(var c = 1; c <= $scope.searchedProperties.length; c++){
				$http({
					method : "GET",
					url : "downloadImage/" + c,
				}).then(function(response) {			
					$scope.searchedProperties[c-1].image = response.data;
				});
        	}        	        
		})
	} 
	
	$scope.getPopularProperties();
	
	$scope.priceSlider = {
		    minValue: 0,
		    maxValue: 8000000,
		    options: {
		      floor: 0,
		      ceil: 10000,
		      step: 1
		    }
	};
	
	$scope.sqFeetSlider = {
		    minValue: 0,
		    maxValue: 14000,
		    options: {
		      floor: 0,
		      ceil: 10000,
		      step: 1
		    }
	};
	
	$scope.searchedProperties = [];
	
	$scope.searchProperty = function(isPageReset){
		if(isPageReset == 1){
			$scope.pageNumber = 1;
		}
		$scope.pageLoad = true;
		$http({
	        method : "POST",
	        url : "searchProperties",
	        data : {
	        	'totalBedrooms' : $scope.totalBedrooms,
	        	'totalBathrooms' : $scope.totalBathrooms,
	        	'totalFloors' : $scope.totalFloors,
	        	'startingPrice' : $scope.priceSlider.minValue,
	        	'endingPrice' : $scope.priceSlider.maxValue,
	        	'startingSqFt' : $scope.sqFeetSlider.minValue,
	        	'endingSqFt' : $scope.sqFeetSlider.maxValue,
	        	'currentPageNumber':$scope.pageNumber,
	        	'zipcode' : $scope.zipcode,
	        }
	    }).then(function(response) {
	    	$scope.pageLoad = false;
	    	//$scope.searchedProperties = [];
        	$scope.searchedProperties = response.data;
        	imageList = [];
        	for(var c = 1; c <= $scope.searchedProperties.length; c++){
        		$scope.loadImage(c);
        	}
        	
        	for(var c = 0; c < $scope.searchedProperties.length; c++){
        		$scope.searchedProperties[c].image = imageList[c];
        	}
        	
        	console.log('total records = '+$scope.searchedProperties.length);
    	});
	}
	  
	  $scope.bedrooms = ['1', '2', '3', '4'];
	  $scope.totalBedrooms = ['0'];
	  $scope.bathrooms = ['1', '2', '3', '4'];
	  $scope.totalBathrooms = ['0'];
	  $scope.floors = ['1', '2', '3', '4'];
	  $scope.totalFloors = ['0'];
	  
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