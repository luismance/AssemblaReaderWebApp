var loginUser = function(){

  if(sessionStorage.getItem("userData")){
    window.location.href = "/AssemblaReader/index.html";
  }

  var userName = $('#username').val();
  var password = $('#password').val();
  var confirmPassword = $('#confirmpassword').val();

  var requestData = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><user><password>"+userName+"</password><username>"+ password +"</username></user>";
  $.ajax({
    type: "POST",
    url: "rest/user/login",
    headers: {
      "Content-Type" : "application/xml"
    },
    data: requestData,
    dataType: 'text',
    success: function(data){
      var x2js = new X2JS();
      var userJson = x2js.xml_str2json(data);
      console.log("Login Response XML: " + JSON.stringify(data));
      sessionStorage.setItem("userData", JSON.stringify(userJson));
      console.log("Login Response JSON : " + JSON.stringify(userJson));
      window.location.href = "/AssemblaReader/index.html";
    },
    error: function(data){
      $("#loginErrorMessage").html("<strong>Error!</strong><br/>"+data.responseText);
      $("#loginErrorMessage").show();
    }
  });

}

function loginForm(props){
  return React.createElement('form', {}, React.createElement(formHeader, { label : 'Login' }),
    React.createElement('div', { 'id' : 'loginErrorMessage' , className : 'alert alert-danger', style : {display:'none'}}),
    React.createElement(inputText, { inputId : 'username', label : 'Username', inputType : 'text'}),
    React.createElement(inputText, { inputId : 'password', label : 'Password', inputType : 'password'}),
    React.createElement('div', {className : 'row'},
      React.createElement('div' , {className:'col-sm-6'}, React.createElement(formButton , { label : 'Login', buttonType : 'button', functionCall : loginUser})),
      React.createElement('div' , {className:'col-sm-6'}, 'Not Registered?',
        React.createElement('a' , {'href':'/AssemblaReader/registration.html'},
          React.createElement('abbr', {}, 'Create Account')
          )
        )
      )
    );
}

function loginPage(props){
  return React.createElement('div', {className : 'container', 'id' : 'registration'},
    React.createElement('div', {className : 'row align-items-center'},
      React.createElement('div', {className : 'col'}),
      React.createElement('div', {className : 'col'},
        React.createElement(loginForm)),
      React.createElement('div', {className : 'col'})

      )
    )
}

ReactDOM.render(React.createElement(loginPage),document.getElementById('app'));
