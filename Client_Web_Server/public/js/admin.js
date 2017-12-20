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
    
    $scope.signin=function(){
        console.log("Reporting from admin sign in ");
        $window.location.href = "/admin_home";
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
