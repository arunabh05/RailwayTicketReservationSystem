var app = angular.module('main', ['ngRoute']);
app.config(['$routeProvider', '$locationProvider',
     function ($routeProvider, $locationProvider) {
        $routeProvider.
        when('/search', {
            templateUrl: 'templates/search.html',
            controller: "search_controller"
        }).
        when('/bookings', {
            templateUrl: 'templates/bookings.html',
            controller: "bookings_controller"
        }).when('/admin', {
            templateUrl: 'templates/admin.html',
            controller: "admin_controller"
        }).otherwise({
            redirect: '/'
        });
    }]);

app.controller("search_controller", function ($scope, $http, $filter, $window) {
    console.log("Reporting from Search controller");
    $scope.bookingsuccessful = true;


    console.log($scope.numberOfPassengers);
    /*$http.get('two_stop_round_trip.json').success(function (data){
		$scope.result = data;
         for(var i=0;i<$scope.result[i].tickets[0].numberOfPassengers;i++){
       $scope.numberOfPassengers.push(i);

   }
        /!*for(var i=0;i<$scope.result.length;i++){
            console.log($scope.result[i].tickets[0].train.train.name);
            console.log($scope.result[i].tickets[0].train.fromStation.name);
            console.log($scope.result[i].tickets[0].train.toStation.name);
            console.log($scope.result[i].tickets[0].train.departureTime);
            console.log($scope.result[i].tickets[0].train.arrivalTime);
            console.log($scope.result[i].tickets[0].train.duration);
            console.log($scope.result[i].tickets[0].numberOfPassengers);





            
        }*!/
	});*/


    $scope.search = function () {
        console.log("Reporting from search function");
        console.log($scope.passengers);
        console.log($scope.departure_time);
        console.log($scope.from_station);
        console.log($scope.to_station);
        console.log($scope.ticket_type);
        console.log($scope.connections);
        console.log($scope.roundtrip);
        console.log($scope.return_time);
        $scope.name = {};
        $scope.train_data;
        //$scope.bookingsuccessful=true;

        if ($scope.departure_time == undefined || $scope.from_station == undefined || $scope.to_station == undefined || $scope.ticket_type == undefined || $scope.connections == undefined)
            return null;

        $scope.numberOfPassengers = [];
        for (var i = 0; i < $scope.passengers; i++) {
            $scope.numberOfPassengers.push(i);
        }



        var departureDate = $scope.departure_time.toString();


        var departure_month = ($scope.departure_time.getMonth() + 1);
        var departure_date = (departureDate.split(' ')[2]);
        var departure_year = (departureDate.split(' ')[3]);
        var departure_time = (departureDate.split(' ')[4]);


        if ($scope.roundtrip == undefined)
            var URL = "http://10.0.0.73:8080/api/search?departureTime=" + departure_time + "&fromStation=" + $scope.from_station + "&toStation=" + $scope.to_station + "&connection=" + $scope.connections + "&dateOfJourney=" + departure_year + "-" + departure_month + "-" + departure_date;

        else {
            var returnDate = $scope.return_time.toString();
            var return_month = ($scope.return_time.getMonth() + 1);
            var return_date = (returnDate.split(' ')[2]);
            var return_year = (returnDate.split(' ')[3]);
            var return_time = (returnDate.split(' ')[4]);

            var URL = "http://10.0.0.73:8080/api/search?departureTime=" + departure_time + "&fromStation=" + $scope.from_station + "&toStation=" + $scope.to_station +
                "&connection=" + $scope.connections + "&dateOfJourney=" + departure_year + "-" + departure_month + "-" + departure_date +
                "ticketType=" + $scope.ticket_type + "&roundTrip=" + $scope.roundtrip + "&returnDate=" + return_year + "-" + return_month + "-" + return_date + "&returnTime=" + return_time;

        }

        console.log("final check");
        console.log(URL);

        $http({
            url: URL,
            method: "POST"

        }).success(function (data) {
            console.log(data);
            $scope.result = null;
            $scope.result = data;
            console.log("Successful login");
        })
    }


    $scope.book = function (data) {
        $scope.train_data = data;
        console.log($scope.train_data);
    }


    $scope.confirmBooking = function () {

        console.log("phh", $scope.train_data);
        console.log("hello", $scope.name);
        console.log("hello", $scope.name.length);
        for (var key in $scope.name) {
            if ($scope.name.hasOwnProperty(key)) {
                console.log("test", $scope.name[key]);
            }
        }

        var URL = "http://10.0.0.73:8080/api/transaction?userId=8";

        $http({
            url: URL,
            method: "POST",
            data: $scope.train_data


        }).success(function (data) {
            console.log("Booking successful");
            $scope.bookingsuccessful = false;
            $window.location.assign = "/search";

        }).error(function (data) {
            console.log("Booking unsuccessful");
        });

    }

    $scope.searchRoute = function () {
        console.log("Rerouting to search page");
        //$window.location.assign="/search";
        $window.location.reload();
    }



});

app.controller("bookings_controller", function ($scope, $http) {
    console.log("Reporting from bookings controller");
});

app.controller("admin_controller", function ($scope, $http) {
    console.log("Reporting from admin controller");
});