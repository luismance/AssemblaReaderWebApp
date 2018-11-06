class SyncData extends React.Component {
  constructor(props) {
  super(props);
  this.state = {
    spaceCount: 0,
    ticketCount: 0,
    milestoneCount: 0,
    ticketChangesCount: 0,
    startSync: false,
    syncStatus: "Ready to start"
  };
  this.componentDidMount = this.componentDidMount.bind(this);
  this.syncdata = this.syncdata.bind(this);
}

componentDidMount() {

  var userData = localStorage.getItem("userData");
  var userItem = JSON.parse(userData);

  if (userItem == null) {
    window.location.href = "login.html";
  }

  var thisComp = this;
  var x2js = new X2JS();
  var syncStatus = "";

  var syncRequest = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>";

  window.setInterval(function() {

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
        thisComp.setState({
          spaceCount
        });
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
        thisComp.setState({
          milestoneCount
        });
      },
      error: function(data) {}
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
        thisComp.setState({
          ticketCount
        });

      },
      error: function(data) {}
    });

    $.ajax({
      type: "GET",
      url: "rest/ticket/userTicketChangesCount",
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: 'text',
      success: function(data) {
        var tCount = x2js.xml_str2json(data);
        var ticketChangesCount = tCount.spaceTicketChangesCount.count;
        syncStatus = tCount.spaceTicketChangesCount.sync_status;
        thisComp.setState({
          ticketChangesCount
        });
      },
      error: function(data) {}
    });

    if(syncStatus != "Ready to start"){
        thisComp.setState({ startSync : true });
    }
    if (thisComp.state.startSync) {
      thisComp.setState({
        syncStatus
      });

      if (syncStatus == "Sync Done") {
        thisComp.setState({
          startSync: false
        });
        console.log("stopped sync");
        clearInterval();
      }
    }
  }, 1000);
}

syncdata() {

  console.log("Call sync data");
  var userData = localStorage.getItem("userData");
  var userItem = JSON.parse(userData);

  if (userItem == null) {
    window.location.href = "login.html";
  }
  var thisComp = this;
  var x2js = new X2JS();
  var syncStatus = "";

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
      thisComp.setState({
        startSync : true
      });
    },
    error: function(data) {
      console.log("Error syncing data")
    }
  });
}

  logout() {
    localStorage.clear();
    window.location.href = "login.html";
  }

  render() {


    return (
      <div>
        <NavBar />
        <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
          <h4 class="display-4">{this.state.syncStatus}</h4>
          <a id="btnSync" href="#" class="btn btn-primary my-2" onClick={this.syncdata} disabled={this.state.startSync}>Start Sync</a>
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
            </div>
            <div class="card-deck mb-3 text-center">
              <div class="card mb-4 shadow-sm">
                <div class="card-header">
                  <h4 class="my-0 font-weight-normal">Ticket Count</h4>
                </div>
                <div class="card-body">
                  <h1 class="card-title pricing-card-title">{this.state.ticketCount}</h1>
                </div>
              </div>
              <div class="card mb-4 shadow-sm">
                <div class="card-header">
                  <h4 class="my-0 font-weight-normal">Ticket Changes Count</h4>
                </div>
                <div class="card-body">
                  <h1 class="card-title pricing-card-title">{this.state.ticketChangesCount}</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
    );
  }
}

ReactDOM.render(React.createElement(SyncData), document.getElementById("app"));
