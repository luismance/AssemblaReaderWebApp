class InputText extends React.Component {
  constructor(props){
    super(props);
  }

  render(){
    return(
      <div class="form-group row">
        <label for={this.props.inputId}>{this.props.label}</label>
        <input type={this.props.inputType}
                 class="form-control"
                 aria-describedby="emailHelp"
                 placeholder={this.props.label}
                 id={this.props.inputId}
                 name={this.props.name}
                 onChange={this.props.textChangeFunction}
                 disabled={this.props.disabled==null ? false : true} />
      </div>
    );
  }
}

class FormButton extends React.Component {
  constructor(props){
    super(props);
  }

  render(){
    return (
      <button id={this.props.inputId} className="btn btn-lg btn-primary btn-block" type={this.props.buttonType} onClick={this.props.functionCall}>{this.props.label}</button>
    );
  }
}

class FormHeader extends React.Component {
  constructor(props){
    super(props);
  }

  render(){
    return (
      <h2 className="form-signin-heading">{this.props.label}</h2>
    );
  }
}
