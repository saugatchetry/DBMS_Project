angular.module('myApp').controller('BuyController', ['$rootScope', '$scope', '$http','toastr', function($rootScope, $scope, $http, toastr) {
	$scope.user = $rootScope.userDetails;
	
	$scope.pageNumber = 1;
	$scope.loading = false;
	
	$scope.isNavVisible = false;
	//$scope.orderByPref = undefined;

	function setOrder(){
		$scope.orderPreferences = [
			{
				label : 'Relevance',
				id: 0
			},
			
			{
				label : 'price low to high',
				id: 1
			},
			{
				label : 'price high to low',
				id: 2
			}
			];		
		
		$scope.orderByPref = $scope.orderPreferences[0].id;
	}
	
	setOrder();
	
	
	$scope.imageList = [];
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
			$scope.imageList.push(response.data);
		});
	}
	
	$scope.getPopularProperties = function(){
		$http({
			method : "GET",
			url: "getPopularProperties"
		}).then(function(response){
			//var images = ['prop1.jpg', 'prop2.jpg', 'prop3.jpg', 'prop4.jpg', 'prop5.jpg', 'prop6.png'];
			$scope.searchedProperties = response.data;
			console.log($scope.properties);
			$scope.imageList = [];
			for(var c = 1; c <= $scope.searchedProperties.length; c++){
				$scope.loadImage($scope.searchedProperties[c - 1].id);			
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
	
	$scope.changeOrder = function(order){
		$scope.orderSet = order;
		$scope.searchProperty(2);
	}
	
	$scope.searchProperty = function(isPageReset){
		$scope.isNavVisible = true;				
				
		toastr.info('The Data is being Fetched','Please Wait!', {
			  allowHtml: true, 
			  timeOut : 50000,				
			  preventDuplicates: true,
			  preventOpenDuplicates: true,
			  progressBar: true,
			});		
		if(isPageReset == 1 || isPageReset == 2){
			$scope.pageNumber = 1;
		}
		
		if(isPageReset == 1){			
			setOrder();
			$scope.orderSet = 0;			
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
	        	'orderBy' : $scope.orderSet,
	        }
	    }).then(function(response) {
	    	$scope.pageLoad = false;
	    	//$scope.searchedProperties = [];
        	$scope.searchedProperties = response.data;
        	$scope.imageList = [];
        	for(var c = 1; c <= $scope.searchedProperties.length; c++){
				$scope.loadImage($scope.searchedProperties[c-1].id);			
        	}   	
        	
        	toastr.clear();
        	
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
	  
	  $scope.provideSellerDetails = function(property){
		  console.log($rootScope.userDetails);
		  if($rootScope.userDetails == undefined){
			  $rootScope.loginModal = true;
		  }
		  else{
			  
			  $http({
			        method : "GET",
			        url : "insertSearchedProperty",
			        params : {
			        	'userId': $rootScope.userDetails.id,
			        	'propertyId' : property.id
			        }
			    }).then(function(response) {
			    	 insertBuyerPreference();
		    	});
			  			 			  
			  $scope.currentSeller = property.seller;
			  $scope.sellerDetailsModal = true;
		  }
	  }
	  
	  function insertBuyerPreference(){
		  
		  var buyPref = {
				  'id':$rootScope.userDetails.id,
				  'minArea': $scope.sqFeetSlider.minValue,
				  'maxArea': $scope.sqFeetSlider.maxValue,
				  'minBudget': $scope.priceSlider.minValue,
				  'maxBudget':$scope.priceSlider.maxValue,				  
		  }
		  
		  $http({
		        method : "POST",
		        url : "insertBuyerPref",
		        data: buyPref,
		    }).then(function(response) {
				console.log('inserted into Buyer table');
	    	});		  
	  }
	  
	  $scope.openDetailModal = function(property,index){		  	
			$scope.property = property;	
			$scope.property.image = $scope.imageList[index];
			$scope.propertyModal = true;			
		}
}]);