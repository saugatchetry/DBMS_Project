angular.module('myApp').controller('BuyController', ['$scope', '$http', function($scope, $http) {
	
	$scope.loadImage = function(){
		$http({
			method : "GET",
			url : "downloadImage",	       
		}).then(function(response) {			
			$scope.image = response.data;
		});
	}
}]);