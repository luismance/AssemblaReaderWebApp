class RegistrationForm extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    var userData = JSON.parse(localStorage.getItem("userData"));
    $('#username').val(userData.user.email);
  }

  registerUser() {
    var userName = $('#username').val();
    var password = $('#password').val();
    var confirmPassword = $('#confirmpassword').val();

    var isFormValid = true;
    if (userName.length < 8 || password.length < 8) {
      isFormValid = false;
      $("#registrationErrorMessage").html("<strong>Error!</strong>Username and password cannot be less than 8 characters long.");
      $("#registrationErrorMessage").show();
    }
    if (password !== confirmPassword) {
      isFormValid = false;
      $("#confirmpasswordmsg").show();
    }

    if (isFormValid) {
      var requestData = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><user><password>" + userName + "</password><username>" + password + "</username></user>";
      $.ajax({
        type: "POST",
        url: "rest/user/create",
        headers: {
          "Content-Type": "application/xml"
        },
        data: requestData,
        dataType: 'text',
        success: function(data) {
          localStorage.setItem("userData", data);
          window.location.href = "assemblareader/index.html";
        },
        error: function(data) {
          $("#registrationErrorMessage").html("<strong>Error!</strong>" + data.responseText);
          $("#registrationErrorMessage").show();
        }
      });
    }
  }

  validatePassword() {
    var password = $('#password').val();
    var confirmPassword = $('#confirmpassword').val();
    $("#confirmpasswordmsg").hide();
    if (password !== "" && confirmPassword !== "") {
      if (password !== confirmPassword) {
        $("#confirmpasswordmsg").show();
      }
    }
  }

  validateUsername() {
    var username = $('#username').val();
    $("#registrationErrorMessage").hide();
    if (username !== "") {
      $("#confirmpasswordmsg").hide();
    }
  }

  render(){
    return (
      <form>
        <FormHeader label="Register"/>
        <div id="registrationErrorMessage" className="alert alert-danger" style={{ display: 'none' }} />
        <InputText inputId="username" name="username" label="Username" inputType="text" disabled="true" />
        <InputText inputId="password" name="password" label="Password" inputType="password" />
        <div id="confirmpasswordmsg" className="alert alert-danger" style={{ display: 'none' }} >Passwords should be the same</div>
        <InputText inputId="confirmpassword" name="confirmpassword" label="Confirm Password" inputType="password" textChangeFunction={this.validatePassword} />
        <div className="col-sm-6">
          <FormButton label="Register" buttonType="button" functionCall={this.registerUser} />
        </div>
        <div className="col-sm-6">
          Already Registered?
          <a  href="/assemblareader/login.html" ><abbr>Login</abbr></a>
        </div>
      </form>
    );
  }
}

class RegistrationPage extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
  }

  render() {
    return (
      <div id="registration" className="container">
        <div className="row align-items-center">
          <div className="col">
          </div>
          <div className="col">
            <RegistrationForm />
          </div>
          <div className="col">
          </div>
        </div>
      </div>);
    }
}


ReactDOM.render(React.createElement(RegistrationPage),document.getElementById('app'));
