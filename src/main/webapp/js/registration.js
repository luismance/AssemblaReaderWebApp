var registerUser = function(){
    var userName = $('#username').val();
    var password = $('#password').val();
    var confirmPassword = $('#confirmpassword').val();

    if(password !== confirmPassword){
        $("#confirmpasswordmsg").show();
    }else{
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
                console.log(data);
                localStorage['userData'] = data;
                window.location.href = "https://api.assembla.com/authorization?client_id=baX24QXs4r56RcacwqjQXA&response_type=code";
            },
            error: function(data){
                
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

function registrationForm(props){
    return React.createElement('form', {}, React.createElement(formHeader, { label : 'Register' }), 
        React.createElement(inputText, { inputId : 'username' , name : 'username' , label : 'Username', inputType : 'text'}), 
        React.createElement(inputText, { inputId : 'password' , name : 'password' ,label : 'Password', inputType : 'password', textChangeFunction : validatePassword}), 
        React.createElement('label', { 'id' : 'confirmpasswordmsg'}, 'Passwords should be the same'), 
        React.createElement(inputText, { inputId : 'confirmpassword' , name : 'confirmpassword' ,label : 'Confirm Password', inputType : 'password', textChangeFunction : validatePassword}),
        React.createElement('div', {className : 'row'}, 
            React.createElement('div' , {className:'col-sm-6'}, React.createElement(formButton , { label : 'Register', functionCall : registerUser, buttonType : 'button'})),
            React.createElement('div' , {className:'col-sm-6'}, 'Already Registered?', 
                React.createElement('a' , {'href':'/AssemblaReader/login.html'},
                    React.createElement('abbr', {}, 'Login')
                    )
                )
            )
        );
}



function registrationPage(props){
    return React.createElement('div', {className : 'container', 'id' : 'registration', onLoad : function(){ $("#confirmpasswordmsg").hide(); }}, 
        React.createElement('div', {className : 'row'},
            React.createElement('div', {className : 'col-md'}),
            React.createElement('div', {className : 'col-md-5'},
                React.createElement(registrationForm)),
            React.createElement('div', {className : 'col-md'})

            )
        )
}

ReactDOM.render(React.createElement(registrationPage, { onChange: function(user) { console.log(user) }}),document.getElementById('app'))