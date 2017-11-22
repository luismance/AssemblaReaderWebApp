function registrationForm(props){
    return React.createElement('form', {}, React.createElement(formHeader, { label : 'Login' }), 
        React.createElement(inputText, { label : 'Username', inputType : 'text'}), 
        React.createElement(inputText, { label : 'Password', inputType : 'password'}), 
        React.createElement('div', {className : 'row'}, 
            React.createElement('div' , {className:'col-sm-6'}, React.createElement(formButton , { label : 'Login', buttonType : 'button'})),
            React.createElement('div' , {className:'col-sm-6'}, 'Not Registered?', 
                React.createElement('a' , {'href':'/AssemblaReader/registration.html'},
                    React.createElement('abbr', {}, 'Create Account')
                    )
                )
            )
        );
}

function registrationPage(props){
    return React.createElement('div', {className : 'container', 'id' : 'registration'}, 
            React.createElement('div', {className : 'row'},
                React.createElement('div', {className : 'col-md'}),
                React.createElement('div', {className : 'col-md-5'},
                    React.createElement(registrationForm)),
                React.createElement('div', {className : 'col-md'})

                )
        )
}

ReactDOM.render(React.createElement(registrationPage),document.getElementById('app'))