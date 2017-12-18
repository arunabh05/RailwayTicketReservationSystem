var app = angular.module('login',[]);

app.controller("login_controller",function($scope,$http,$window){
	$scope.invalid=true;
	console.log("Reporting from login controller");
    $scope.signin=function(){
        var username=$scope.username;
        var password=$scope.password;
        console.log("Reporting from sign in function");
        $window.location.href="/home.html"
    }
    
    $scope.register=function(){
        var firstname=$scope.firstname;
        var lastname=$scope.lastname;
        var email=$scope.emailid;
        var password=$scope.password;
        console.log("Reporting from register function");    
    }
});
