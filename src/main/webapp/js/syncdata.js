class SyncData extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      spaceCount: 0,
      ticketCount : 0,
      milestoneCount : 0,
      syncStatus : "Starting Sync"
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
    var x2js = new X2JS();
    var syncStatus = "";

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
        },
        error: function(data) {
          console.log("Error counting space");
        }
      });

      $.ajax({
        type: "GET",
        url: "rest/milestone/userMilestoneCount",
        headers: {
          "Content-Type": "application/xml",
          Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
        },
        dataType: 'text',
        success: function(data) {
          var tCount = x2js.xml_str2json(data);
          var milestoneCount = tCount.spaceMilestoneCount.count;
          syncStatus = tCount.spaceMilestoneCount.sync_status;
          thisComp.setState({milestoneCount});
        },
        error: function(data) {
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
          syncStatus = tCount.spaceTicketCount.sync_status;
          thisComp.setState({ticketCount});
          thisComp.setState({syncStatus});

        },
        error: function(data) {
        }
      });
      if(syncStatus == "Sync Done"){
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
        console.log("Sync started");
      },
      error: function(data) {
        console.log("Error syncing data");
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
        <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
          <h4 class="display-4">{this.state.syncStatus}</h4>
        </div>
        <div class="container">
          <div class="card-deck mb-3 text-center">
            <div class="card mb-4 shadow-sm">
              <div class="card-header">
                <h4 class="my-0 font-weight-normal">Space Count</h4>
              </div>
              <div class="card-body">
                <h1 class="card-title pricing-card-title">{this.state.spaceCount}</h1>
              </div>
            </div>
            <div class="card mb-4 shadow-sm">
              <div class="card-header">
                <h4 class="my-0 font-weight-normal">Milestone Count</h4>
              </div>
              <div class="card-body">
                <h1 class="card-title pricing-card-title">{this.state.milestoneCount}</h1>
              </div>
            </div>
            <div class="card mb-4 shadow-sm">
              <div class="card-header">
                <h4 class="my-0 font-weight-normal">Ticket Count</h4>
              </div>
              <div class="card-body">
                <h1 class="card-title pricing-card-title">{this.state.ticketCount}</h1>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ReactDOM.render(React.createElement(SyncData), document.getElementById("app"));
