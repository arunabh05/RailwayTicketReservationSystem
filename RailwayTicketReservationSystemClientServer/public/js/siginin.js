var app = angular.module('login',[]);

app.controller("login_controller",function($scope,$http,$window){
	$scope.invalid=true;
	console.log("Reporting from login controller");
    $scope.signin=function(){
        var username=$scope.username;
        var password=$scope.password;
        console.log("Reporting from sign in function");

        //verifyLogin?email=j.n@gmail.com&password=abc
        var URL = "http://10.0.0.68:8080/verifyLogin?email="+username+"&password="+password;

        console.log(URL);

        $http({
            url: URL,
            method:"GET"

        }).success(function(data){
            console.log("Successful login");
            $window.location.href="/home.html"
        })
    }
    
    $scope.register=function(){
        var firstname=$scope.firstname;
        var lastname=$scope.lastname;
        var email=$scope.emailid;
        var password=$scope.password;
        console.log("Reporting from register function");

        var URL = "http://10.0.0.68:8080/registerNewUser?firstName="+firstname+"&lastName="+lastname+"&email="+email+"&password="+password;

        console.log(URL);

        $http({
            url: URL,
            method:"POST"

        }).success(function(data){
            console.log("Successful login");
        })

    }
});
