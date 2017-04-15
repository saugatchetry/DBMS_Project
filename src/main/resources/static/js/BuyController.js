angular.module('myApp').controller('BuyController', ['$scope', '$http', function($scope, $http) {
	
	$scope.loadImage = function(){
		$http({
			method : "GET",
			url : "downloadImage",	       
		}).then(function(response) {			
			$scope.image = response.data;
		});
	} 
	
	$scope.fruits = ['apple', 'orange', 'pear', 'naartjie'];

	  // Selected fruits
	  $scope.selection = ['apple', 'pear'];

	  // Toggle selection for a given fruit by name
	  $scope.toggleSelection = function toggleSelection(fruitName) {
	    var idx = $scope.selection.indexOf(fruitName);

	    // Is currently selected
	    if (idx > -1) {
	      $scope.selection.splice(idx, 1);
	    }

	    // Is newly selected
	    else {
	      $scope.selection.push(fruitName);
	    }
	  }
	
}]);