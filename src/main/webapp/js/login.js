var app = angular.module('myApp',['swxSessionStorage']);
app.controller('myCtrl', ['$scope', '$http', '$window', '$location', '$sessionStorage', function($scope, $http, $window, $location, $sessionStorage) {
	$scope.username = "";
	$scope.password = "";
	$scope.bearer_token = "";

	$scope.sendRequest = function() {
		var requestData = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><user><password>"+$scope.username+"</password><username>"+$scope.password +"</username></user>";
		var restUrl = $location.absUrl().split("register")[0] + "rest/user/login";
		$http.post(restUrl, requestData, {headers:{'Content-Type': 'application/xml'}}).then(function (response) {
			if (response.data)
				console.log("Post Data Submitted Successfully!");
				var x2js = new X2JS();
      			var aftCnv = x2js.xml_str2json(response.data);
      			console.log("aftCnv:"+ aftCnv.user._id);
      			$scope.userObj = aftCnv;
      			$sessionStorage.put("user",aftCnv,1);
				$window.location.href = $location.absUrl().split("register")[0] + "index.html";
		}, function (response) {
			console.log("Service not Exists");
			$scope.msg = "";
			$scope.statusval = response.status;
			$scope.statustext = response.statusText;
			$scope.headers = response.headers();
		});
	};

}]);

$('input[type="submit"]').mousedown(function() {
  $(this).css('background', '#2ecc71');
});
$('input[type="submit"]').mouseup(function() {
  $(this).css('background', '#1abc9c');
});

$('#loginform').click(function() {
  $('.login').fadeToggle('slow');
  $(this).toggleClass('green');
});

$('#navbar').load('../navbar.html');

$(document).mouseup(function(e) {
  var container = $(".login");

  if (!container.is(e.target) // if the target of the click isn't the container...
    && container.has(e.target).length === 0) // ... nor a descendant of the container
  {
    container.hide();
    $('#loginform').removeClass('green');
  }
});