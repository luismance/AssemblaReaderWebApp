function inputText(props){
  return  React.createElement('div', { className : 'row' },
    React.createElement('div', { className : 'input-group form-group'},
        React.createElement('span', { className : 'input-group-addon', id : 'sizing-addon2'}, props.label ),
        React.createElement('input', {
        	'id' : props.inputId,
        	type : props.inputType ,
        	className : 'form-control',
        	'placeholder' : props.label  ,
        	'aria-label' : props.label ,
        	'aria-describedby' : 'sizing-addon2',
        	'name' : props.name,
            onChange: props.textChangeFunction
        })
    )
  );
}

function formButton(props){
    return React.createElement('button',{
    	className : 'btn btn-lg btn-primary btn-block',
    	type : props.buttonType,
    	onClick : props.functionCall
    }, props.label );
}

function formHeader(props){
    return React.createElement('h2',{ className : 'form-signin-heading'}, props.label );
}
