var app = angular.module('login', []);

app.controller("login_controller", function ($scope, $http, $window) {
    var base_url='http://10.0.0.73:8080';
    $scope.invalidlogin = true;
    $scope.validregistration = true;
    $scope.invalidregistration = true;
    console.log("Reporting from login controller");
    $scope.signin = function () {
        var username = $scope.emailid;
        var password = $scope.password;
        console.log("Reporting from sign in function");
        if(username==""||password==""){
            $scope.invalidlogin = false;
        }
        //verifyLogin?email=j.n@gmail.com&password=abc
        var URL = base_url+"/verifyLogin?email=" + username + "&password=" + password;

        console.log(URL);

        $http({
            url: URL,
            method: "GET"
        }).success(function (data) {
            console.log("Successful login", data);
            $window.location.href = "/home";
        }).error(function (data) {
            $scope.invalidlogin = false;
        });
    };

    $scope.register = function () {
        var firstname = $scope.firstname;
        var lastname = $scope.lastname;
        var email = $scope.emailid;
        var password = $scope.password;
        console.log("Reporting from register function");

        var URL = base_url+"/registerNewUser?firstName=" + firstname + "&lastName=" + lastname + "&email=" + email + "&password=" + password;

        console.log(URL);

        $http({
            url: URL,
            method: "POST"

        }).success(function (data) {
            console.log("Successful login");
            $scope.validregistration = false;
            $scope.invalidregistration = true;
        }).error(function (data) {
            console.log(data);
            $scope.validregistration = true;
            $scope.invalidregistration = false;
        });

    };
});