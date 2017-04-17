angular.module('myApp').controller('EstimateController', ['$scope', '$http','toastr', function($scope, $http, toastr) {

	$scope.propertyProp = {
			'woodenFlooring' : 0,
			'carpet' : 0,
			'parking' : 0,
			'petFriendly' : 0,
			'pool' : 0,
			'waterFront' : 0,
			'view' : 0
	};
	
	$scope.estimatedBox = "";
	$scope.furnishType = "Furnished";
	
	$scope.submitEstimateDetails = function(){
		
		if ($scope.estimateDetails.$valid){			
			toastr.info('Price is being Calculated','Please Wait!', {
				  allowHtml: true, 
				  timeOut : 50000,				
				  preventDuplicates: true,
				  preventOpenDuplicates: true,
				  progressBar: true,
				});		
						
			var sellPropDet = {
					'squareFeet':$scope.livingArea,
					'numberOfBedrooms':$scope.bedroom,
					'numberOfFloors':$scope.floors,
					'numberOfBathrooms':$scope.bathroom,
					'yearRenovated':$scope.yearRenovated,
					'yearBuilt':$scope.yearBuilt,
					'zipcode': $scope.zipcode,
					'woodenFlooring':$scope.propertyProp.woodenFlooring,
					'carpet':$scope.propertyProp.carpet,
					'parking':$scope.propertyProp.parking,
					'petFriendly':$scope.propertyProp.petFriendly,
					'pool':$scope.propertyProp.pool,
					'waterFront':$scope.propertyProp.waterFront,
					'view':$scope.propertyProp.view,
					'furnishStatus': $scope.furnishType,
			}				
			$http({
		        method : "POST",
		        url : "estimateValue",
		        data : sellPropDet
		    }).then(function(response) {
		    	var propertyData = response.data;
		    	var displayString = 'The Estimated Price of Your Property : $' + propertyData.estimatedValue + propertyData.suggestions;
		    	$scope.estimatedBox = displayString;
				toastr.clear();		    		    	        	
	    	});						
		}else{
			toastr.error('Form is Invalid', 'Please Fill Details Properly');		
		}		
	};
	
	
}]);