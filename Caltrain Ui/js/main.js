var app = angular.module('main', ['ngRoute']);
app.config(['$routeProvider','$locationProvider',
     function($routeProvider,$locationProvider) {
     $routeProvider.
             when('/search', {
                 templateUrl: 'templates/search.html',
                 controller:"search_controller"
             }).
             when('/bookings', {
                 templateUrl: 'templates/bookings.html',
                 controller:"bookings_controller"
             }).when('/admin', {
                 templateUrl: 'templates/admin.html',
                 controller:"admin_controller"
             }).otherwise({
                   redirect: '/'
           });
    }]);

app.controller("search_controller",function($scope,$http){
   console.log("Reporting from Search controller"); 
   $scope.numberOfPassengers=[];
   
   
    console.log($scope.numberOfPassengers);
    $http.get('two_stop_round_trip.json').success(function (data){
		$scope.result = data;
         for(var i=0;i<$scope.result[i].tickets[0].numberOfPassengers;i++){
       $scope.numberOfPassengers.push(i);
   }
        /*for(var i=0;i<$scope.result.length;i++){
            console.log($scope.result[i].tickets[0].train.train.name);
            console.log($scope.result[i].tickets[0].train.fromStation.name);
            console.log($scope.result[i].tickets[0].train.toStation.name);
            console.log($scope.result[i].tickets[0].train.departureTime);
            console.log($scope.result[i].tickets[0].train.arrivalTime);
            console.log($scope.result[i].tickets[0].train.duration);
            console.log($scope.result[i].tickets[0].numberOfPassengers);
            
        }*/
	});
    
    
    $scope.search=function(){
        console.log("Reporting from search function");
        console.log($scope.passengers);
        console.log($scope.departure_time);
        console.log($scope.from_station);
        console.log($scope.to_station);
        console.log($scope.ticket_type);
        console.log($scope.connections);
        console.log($scope.roundtrip);
        console.log($scope.return_time);
        
        $http({
	            	url:"http://10.0.0.240:8080/api/search",
	            	method:"POST",
	            	data:{
	            		"username":$scope.username,
	            		"password":$scope.password
	            	}
	            	
	            }).success(function(data){
	            	console.log("Successful login");
	            })
    }

});

app.controller("bookings_controller",function($scope,$http){
   console.log("Reporting from bookings controller"); 
});

app.controller("admin_controller",function($scope,$http){
   console.log("Reporting from admin controller"); 
});



  