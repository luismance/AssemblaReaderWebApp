class LoginForm extends React.Component {

  constructor(props){
    super(props);
  }

  componentDidMount(){
    console.log("User Data : " + JSON.stringify(sessionStorage.getItem("userData")));
  }

  loginUser(){

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
        sessionStorage.setItem("userData", JSON.stringify(userJson));
        window.location.href = "/AssemblaReader/index.html";
      },
      error: function(data){
        $("#loginErrorMessage").html("<strong>Error!</strong><br/>"+data.responseText);
        $("#loginErrorMessage").show();
      }
    });
  }

  render(){
    return (
      <div id="registration" className="container">
        <div className="row align-items-center">
          <div className="col">
          </div>
          <div className="col">
            <form>
              <FormHeader label="Login" />
              <div id="loginErrorMessage" className="alert alert-danger" style={{display:"none"}} />
              <InputText inputId="username" label="Username" inputType="text" />
              <InputText inputId="password" label="Password" inputType="password" />
              <div className="col-sm-6">
                <FormButton label="Login" buttonType="button" functionCall={this.loginUser} />
              </div>
              <div className="col-sm-6">
                Not Registered?
                <a href="/AssemblaReader/registration.html"><abbr>Register with Assembla</abbr></a>
              </div>
            </form>
          </div>
          <div className="col">
          </div>
        </div>
      </div>
    );
  }

}


ReactDOM.render(React.createElement(LoginForm),document.getElementById('app'));
