angular.module('myApp').controller('SellController', ['$scope', '$http', function($scope, $http) {
	$scope.getCityNames = function(){
		$http({
	        method : "GET",
	        url : "getCity",	     
	    }).then(function(response) {
        	$scope.cities = response.data;
        	$scope.selectedCity = $scope.cities[0];
    	});		
	};
	
	$scope.propertyProp = {
			'WoodenFlooring' : 0,
			'Carpet' : 0,
			'Parking' : 0,
			'PetFriendly' : 0,
			'Pool' : 0,
			'WaterFront' : 0,
			'View' : 0
	};
	
}]);