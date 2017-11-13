var app = angular.module('myApp',['swxSessionStorage']);
app.controller('myCtrl', ['$scope', '$http', '$window', '$location','$sessionStorage', function($scope, $http, $window, $location, $sessionStorage) {
	$scope.username = "";
	$scope.password = "";
	$scope.confirmpassword = "";

	$scope.registerUser = function() {

		var requestData = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><user><password>"+$scope.username+"</password><username>"+$scope.password +"</username></user>";
		var restUrl = $location.absUrl().split("register")[0] + "rest/user/create";
		$http.post(restUrl, requestData, {headers:{'Content-Type': 'application/xml'}}).then(function (response) {
			if (response.data)
				console.log("Post Data Submitted Successfully!");
				var x2js = new X2JS();
      			var aftCnv = x2js.xml_str2json(response.data);
      			console.log("aftCnv:"+ aftCnv.user._id);
      			$scope.userObj = aftCnv;
      			$sessionStorage.put("user",aftCnv,1);
				$window.location.href = "https://api.assembla.com/authorization?client_id=baX24QXs4r56RcacwqjQXA&response_type=code";
		}, function (response) {
			console.log("Service not Exists");
			$scope.msg = "Service not Exists";
			$scope.statusval = response.status;
			$scope.statustext = response.statusText;
			$scope.headers = response.headers();
		});
	};

}]);