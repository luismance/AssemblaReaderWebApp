class NavBar extends React.Component {

  constructor(props){
    super(props);
  }

  logout() {
    localStorage.clear();
    window.location.href = "login.html";
  }

  render(){
    return (
      <div>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="index.html">
            <h5>Ticket Monitor</h5>
          </a>
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarText">
            <ul className="navbar-nav mr-auto" >
              <li class="nav-item active">
                <h6><a class="nav-link" href="syncdata.html">Sync</a></h6>
              </li>
            </ul>
            <span className="navbar-text">
              <a onClick={this.logout}>Logout</a>
            </span>
          </div>
        </nav>
      </div>
    );
  }

}
