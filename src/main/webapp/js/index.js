var app = angular.module('myApp',['swxSessionStorage']);
app.controller('myCtrl', ['$scope', '$http', '$window', '$location', '$sessionStorage', function($scope, $http, $window, $location, $sessionStorage) {
	
	var restUrl = "https://api.assembla.com/v1/spaces.xml";

	$http.jsonp(restUrl,
		{headers : {
			'Authorization': 'Bearer '+ $sessionStorage.get('user').user.bearerToken,
			'Content-Type': 'application/xml'
		}}).then(function successCallback(response) {
			if (response.data){
				$scope.spaces = response.data;

				console.log("Spaces : " + JSON.stringify($scope.spaces));

				console.log("Success : " + JSON.stringify(response.data));
			console.log("Success : " + JSON.stringify(response.status));
			console.log("Success : " + JSON.stringify(response.statusText));
    // called asynchronously if an error occurs
			}
		}, function errorCallback(response, status, xhjr) {
			console.log("Fail : " + JSON.stringify({data : response}));
			console.log("Fail : " + JSON.stringify({data : response.data}));
			console.log("Fail : " + JSON.stringify(response));
			console.log("Fail : " + JSON.stringify(status));
			console.log("Fail : " + status);
			console.log("Fail : " + JSON.stringify(response.data));
			console.log("Fail : " + JSON.stringify(response.status));
			console.log("Fail : " + JSON.stringify(response.statusText));
			var x2js = new X2JS();
      		var aftCnv = x2js.xml_str2json(response.data);
      		console.log("aftCnv:"+ JSON.stringify(aftCnv));
			var res = JSON.parse(aftCnv);
			console.log("Fail : " + JSON.stringify(res));
			console.log("Fail : " + JSON.stringify(res.data));
			console.log("Fail : " + JSON.stringify(res.status));
			console.log("Fail : " + JSON.stringify(res.statusText));

			angular.forEach(response.children, function(child){
            console.log(response);
            console.log("Child data : " + child.data);
        	});
    // called asynchronously if an error occurs
    // or server returns response with an error status.
});

	}]);