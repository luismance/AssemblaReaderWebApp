class SpaceList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      spaceCount: 0,
      ticketCount : 0
    };
    this.componentDidMount = this.componentDidMount.bind(this);
  }

  componentDidMount() {

    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);

    if(userItem == null){
      window.location.href = "login.html";
    }

    var thisComp = this;
    var currSpaceCount = 0;
    var currTicketCount = 0;
    var stopSpaceCount = false;
    var stopTicketCount = false;
    var x2js = new X2JS();

    var ticketCounterBuffer=0;
    window.setInterval(function(){

      $.ajax({
        type: "GET",
        url: "rest/space/spaceCount",
        headers: {
          "Content-Type": "application/xml",
          Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
        },
        dataType: 'text',
        success: function(data) {
          var sCount = x2js.xml_str2json(data);
          var spaceCount = sCount.spaceCount.count;
          thisComp.setState({spaceCount});
          stopSpaceCount = spaceCount == currSpaceCount;
          currSpaceCount = spaceCount;
        },
        error: function(data) {
          window.alert("Error counting space");
        }
      });

      $.ajax({
        type: "GET",
        url: "rest/ticket/userTicketCount",
        headers: {
          "Content-Type": "application/xml",
          Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
        },
        dataType: 'text',
        success: function(data) {
          var tCount = x2js.xml_str2json(data);
          var ticketCount = tCount.spaceTicketCount.count;
          thisComp.setState({ticketCount});
          stopTicketCount = ticketCount == currTicketCount;

          if(stopTicketCount){
            ticketCounterBuffer++;
          }else{
            ticketCounterBuffer=0;
          }
          currTicketCount = ticketCount;
        },
        error: function(data) {
          window.alert("Error counting ticket");
        }
      });
      console.log("space "+ stopSpaceCount + ",ticket "+stopTicketCount+",buffer "+ticketCounterBuffer);
      if(ticketCounterBuffer > 60){
        console.log("stopped sync");
        clearInterval();
        window.location.href = "index.html";
      }
    }, 1000);
  }



  syncdata(){
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);
    var syncRequest = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>";
    $.ajax({
      type: "POST",
      url: "rest/sync/syncdata",
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      data: syncRequest,
      dataType: 'text',
      success: function(data) {
        window.alert("Sync started");
      },
      error: function(data) {
        window.alert("Error syncing data");
      }
    });
  }

  logout() {
    localStorage.clear();
    window.location.href = "/assemblareader/login.html";
  }

  render() {


    return (
      <div>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="#">
            Ticket Monitor
          </a>
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarText">
            <ul className="navbar-nav mr-auto" >
              <li class="nav-item active">
                <a class="nav-link" onClick={this.syncdata}>Sync</a>
              </li>
            </ul>
            <span className="navbar-text">
              <a onClick={this.logout}>Logout</a>
            </span>
          </div>
        </nav>
        <div>
          <h1>{this.state.spaceCount}</h1>
          <h2>{this.state.ticketCount}</h2>
        </div>
      </div>
    );
  }
}

ReactDOM.render(React.createElement(SpaceList), document.getElementById("app"));
