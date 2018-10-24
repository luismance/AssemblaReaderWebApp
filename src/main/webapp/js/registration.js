var registerUser = function(){
    var userName = $('#username').val();
    var password = $('#password').val();
    var confirmPassword = $('#confirmpassword').val();

    var isFormValid = true;
    if(userName.length < 8 || password.length < 8){
        isFormValid = false;
        $("#registrationErrorMessage").html("<strong>Error!</strong>Username and password cannot be less than 8 characters long.");
        $("#registrationErrorMessage").show();
    }
    if(password !== confirmPassword){
        isFormValid = false;
        $("#confirmpasswordmsg").show();
    }

    if(isFormValid){
        var requestData = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><user><password>"+userName+"</password><username>"+ password +"</username></user>";
        $.ajax({
          type: "POST",
          url: "rest/user/create",
          headers: {
            "Content-Type" : "application/xml"
        },
        data: requestData,
        dataType: 'text',
        success: function(data){
            window.localStorage.setItem("userData", data);
            window.location.href = "https://api.assembla.com/authorization?client_id=baX24QXs4r56RcacwqjQXA&response_type=code";
        },
        error: function(data){
            $("#registrationErrorMessage").html("<strong>Error!</strong>"+data.responseText);
            $("#registrationErrorMessage").show();
        }
    });
    }
}

var validatePassword = function(syntheticEvent){
    var password = $('#password').val();
    var confirmPassword = $('#confirmpassword').val();
    $("#confirmpasswordmsg").hide();
    if(password !== "" && confirmPassword !== ""){
        if(password !== confirmPassword){
          $("#confirmpasswordmsg").show();
      }
  }
}

var validateUsername = function(syntheticEvent){
    var username = $('#username').val();
    $("#registrationErrorMessage").hide();
    if(username !== ""){
        $("#confirmpasswordmsg").hide();
    }
}

function registrationForm(props){
    return React.createElement('form', {}, React.createElement(FormHeader, { label : 'Register' }),
        React.createElement('div', { 'id' : 'registrationErrorMessage' , className : 'alert alert-danger', style : {display:'none'}}),
        React.createElement(InputText, { inputId : 'username' , name : 'username' , label : 'Username', inputType : 'text', textChangeFunction : validateUsername}),
        React.createElement(InputText, { inputId : 'password' , name : 'password' ,label : 'Password', inputType : 'password', textChangeFunction : validatePassword}),
        React.createElement('div', { 'id' : 'confirmpasswordmsg' , className : 'alert alert-danger', style : {display:'none'}}, 'Passwords should be the same'),
        React.createElement(InputText, { inputId : 'confirmpassword' , name : 'confirmpassword' ,label : 'Confirm Password', inputType : 'password', textChangeFunction : validatePassword}),
        React.createElement('div', {className : 'row'},
            React.createElement('div' , {className:'col-sm-6'}, React.createElement(FormButton , { label : 'Register', functionCall : registerUser, buttonType : 'button'})),
            React.createElement('div' , {className:'col-sm-6'}, 'Already Registered?',
                React.createElement('a' , {'href':'/assemblareader/login.html'},
                    React.createElement('abbr', {}, 'Login')
                    )
                )
            )
        );
}



function registrationPage(props){
    return React.createElement('div', {className : 'container', 'id' : 'registration' },
        React.createElement('div', {className : 'row align-items-center'},
            React.createElement('div', {className : 'col'}),
            React.createElement('div', {className : 'col'},
                React.createElement(registrationForm)),
            React.createElement('div', {className : 'col'})

            )
        )
}

ReactDOM.render(React.createElement(registrationPage, { onChange: function(user) { console.log(user) }}),document.getElementById('app'))
