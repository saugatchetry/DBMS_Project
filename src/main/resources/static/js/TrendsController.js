angular.module('myApp').controller(
		'TrendsController',
		[
				'$scope',
				'$http',
				function($scope, $http) {

					$scope.lineChartLabels = new Array();
					$scope.lineChartData = new Array();
					$scope.lineChartLabels.push(0);
					$scope.lineChartData.push(0);
					$scope.lineChartSeries = [ 'Average Price' ];

					$scope.refreshZipcodeMap = function() {
						$http({
							method : "GET",
							url : "getAveragePriceByZipcode",
							params : {
								'zipcode' : $scope.zipcode
							}
						}).then(
								function(response) {
									$scope.lineChartLabels = new Array();
									$scope.lineChartData = new Array();
									angular.forEach(response.data, function(
											value, key) {
										$scope.lineChartLabels.push(key);
										$scope.lineChartData.push(value);
									});
								});
					}

					$scope.getZipcodeStatistics = function() {
						$http({
							method : "GET",
							url : "getPricesGroupedByZipcode"
						}).then(
								function(response) {
									$scope.barChartLabels = new Array();
									$scope.barChartData = new Array();
									var list1 = new Array();
									var list2 = new Array();
									var list3 = new Array();
									var list4 = new Array();
									$scope.barChartData.push(list1);
									$scope.barChartData.push(list2);
									$scope.barChartData.push(list3);
									$scope.barChartData.push(list4);
									angular.forEach(response.data, function(
											value, key) {
										$scope.barChartLabels.push(key);
										$scope.barChartData[0].push(value[0]);
										$scope.barChartData[1].push(value[1]);
										$scope.barChartData[2].push(value[2]);
										$scope.barChartData[3].push(value[3]);
									});
								});
					}

					$scope.getCityWidePrices = function() {
						console.log($scope.cityName);
						$http({
							method : "GET",
							url : "getPricesByCity",
							params : {
								'cityName' : $scope.cityName
							}
						}).then(
								function(response) {
									$scope.cityWideLineLabels = new Array();
									$scope.cityWideLineData = new Array();
									var list1 = new Array();
									var list2 = new Array();
									var list3 = new Array();
									$scope.cityWideLineData.push(list1);
									$scope.cityWideLineData.push(list2);
									$scope.cityWideLineData.push(list3);
									angular.forEach(response.data, function(
											value, key) {
										$scope.cityWideLineData[0]
												.push(value[0]);
										$scope.cityWideLineData[1]
												.push(value[1]);
										$scope.cityWideLineData[2]
												.push(value[2]);
										$scope.cityWideLineLabels.push(key);
									});
								});
					}
					$scope.cityWideLineLabels = new Array();
					$scope.cityWideLineData = new Array();
					$scope.cityWideLineLabels.push(0);
					$scope.cityWideLineData.push(0);

					$scope.priceVsOptions = [ 'Region', 'Zipcode' ];

					$scope.barChartSeries = [ 'Min Price', 'Mean Price',
							'Median Price', 'Max Price' ];

					$scope.pieChartLabels = [ "Download Sales",
							"In-Store Sales", "Mail-Order Sales" ];
					$scope.pieChartData = [ 300, 500, 100 ];

					$scope.horizBarLabels = [ '2006', '2007', '2008', '2009',
							'2010', '2011', '2012' ];
					$scope.horizBarSeries = [ 'Buys', 'Sells' ];

					$scope.horizBarData = [ [ 65, 59, 80, 81, 56, 55, 40 ],
							[ 28, 48, 40, 19, 86, 27, 90 ] ];

					$scope.cityWideLineSeries = [ 'Mean Price', 'Median Price',
							'Max Price' ];

					$scope.getZipcodeStatistics();

					$scope.carpet = false, $scope.woodenFlooring = false,
							$scope.waterfront = false;
					$scope.view = false;
					$scope.furnishType = 'None';

					$scope.getFeatureWidePrices = function() {
						$http({
							method : "GET",
							url : "getFeatureWidePrices",
							params : {
								'carpet' : $scope.carpet,
								'woodenFlooring' : $scope.woodenFlooring,
								'waterFront' : $scope.waterfront,
								'view' : $scope.view,
								'furnishType' : $scope.furnishType
							}
						}).then(
								function(response) {
									$scope.featureWiseLabels = new Array();
									$scope.featureWiseData = new Array();
									$scope.featureWiseLabels = [
											'Average Price', 'Median Price',
											'Max Price', 'Min Price' ];
									$scope.featureWiseData = response.data;

								});
					}
					
					$scope.deltaLineChartLabels = [ 0 ];
					$scope.deltaLineChartData = [ 0 ];
					
					$scope.refreshDeltaMap = function(){
						$http({
							method : "GET",
							url : "getDeltaByYear",
							params : {
								'zipcode' : $scope.zipcodeDelta
							}
						}).then(
								function(response){
									$scope.deltaLineChartLabels = new Array();
									$scope.deltaLineChartData = new Array();
									angular.forEach(response.data, function(
											value, key) {
										$scope.deltaLineChartLabels.push(key);
										$scope.deltaLineChartData.push(value);
									});
								});
					};
					
					$scope.deltaLineChartSeries = ['Price Rise/Fall'];
					$scope.months = ['01','02','03','04','05','06','07','08','09','10','11','12'];
					
					$scope.getSalesByZipcode = function(){
						var fromDate = $scope.fromYear + $scope.fromMonth;
						var toDate = $scope.toYear + $scope.toMonth;
						$http({
							method : "GET",
							url : "getSalesByZipcode",
							params : {
								'zipcodes' : $scope.zipcodes,
								'fromDate' : fromDate,
								'toDate' : toDate
							}
						}).then(
								function(response){
									$scope.salesZipcodeLabels = new Array();
									$scope.salesZipcodeData = new Array();
									angular.forEach(response.data, function(
											value, key) {
										$scope.salesZipcodeLabels.push(key);
										$scope.salesZipcodeData.push(value);
									});
						});
					};
					
					$scope.pieHeight = window.innerHeight * 0.095;
					$scope.pieWidth = window.innerWidth * 0.095;
					
				}]);