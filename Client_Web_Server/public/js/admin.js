var app = angular.module('admin', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider',
     function ($routeProvider, $locationProvider) {
        $routeProvider.
        when('/reset', {
            templateUrl: 'templates/reset.html',
            controller: "reset_controller"
        }).
        when('/cancel_train', {
            templateUrl: 'templates/cancel_train.html',
            controller: "cancel_train_controller"
        }).
        when('/system_reports', {
            templateUrl: 'templates/system_reports.html',
            controller: "system_reports_controller"
        }).otherwise({
            redirect: '/'
        });
    }]);

app.controller("admin_controller", function ($scope, $http, $window) {
   console.log("Reporting from admin controller");
   var base_url="http://10.0.0.73:8080";
    $scope.signin=function(){
        console.log("Reporting from admin sign in ");
        console.log("Username=",$scope.username);
        console.log("password=",$scope.password);
        var url=base_url+"/admin/login?username="+$scope.username+"&password="+$scope.password;
        
        $http({
            url: url,
            method: "POST"
        }).success(function (data) {
            console.log("Successful login", data);
            $window.location.href = "/admin_home";
        }).error(function (data) {
            console.log("error");
            $window.location.href = "/admin_home";
        });
        
       
    }
});

app.controller("reset_controller", function ($scope, $http) {
   console.log("Reporting from reset controller"); 
});

app.controller("cancel_train_controller", function ($scope, $http) {
   console.log("Reporting from cancel_train controller"); 
});

app.controller("system_reports_controller", function ($scope, $http) {
   console.log("Reporting from system_reports controller"); 
});
