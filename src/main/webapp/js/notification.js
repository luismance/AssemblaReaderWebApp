class NotificationList extends React.Component{
  constructor(props){
    super(props);
    this.state = {
      notifications : [],
      perPage : 10,
      currPage : 1,
      totalNotifCount : 0,
      violationType : "",
      notificationsToExport : []
    }

    this.componentDidMount = this.componentDidMount.bind(this);
    this.setPerPage = this.setPerPage.bind(this);
    this.setPage = this.setPage.bind(this);
    this.exportNotifications = this.exportNotifications.bind(this);
  }

  componentDidMount(){

    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);

    if(userItem == null){
      window.location.href = "login.html";
    }

    this.callNotificationList();
  }

  callNotificationList(){
    var thisComp = this;
    var userItem = JSON.parse(localStorage.getItem("userData"));
    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/notification/list?per_page=" + thisComp.state.perPage + "&page=" + thisComp.state.currPage+"&spaceid=" + thisComp.props.spaceId+"&violation_type=" + thisComp.state.violationType,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success : function(data) {
        var notificationJson = x2js.xml_str2json(data);

        if(notificationJson.notifications.notification instanceof Array) {
          var notifications = notificationJson.notifications.notification.map(obj => obj);
          thisComp.setState({ notifications });
        }else if(notificationJson.notifications.notification instanceof Object){
          var notificationObj = notificationJson.notifications.notification;
          var notificationArray = [];
          notificationArray.push(notificationObj);
          thisComp.setState({ notifications : notificationArray });
        }else{
          thisComp.setState({ notifications : [] });
        }
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });

    $.ajax({
      type: "GET",
      url: "rest/notification/count?spaceid=" + thisComp.props.spaceId+"&violation_type=" + thisComp.state.violationType,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success : function(data) {
        var notificationJson = x2js.xml_str2json(data);
        var totalNotifCount = notificationJson.notificationCount.count;
        thisComp.setState({ totalNotifCount });
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });
  }
  setPerPage(changePerPage){
    console.log("Set Per Page : " + changePerPage);
    this.setState({
      perPage : changePerPage
    }, () => this.callNotificationList());
  }

  setPage(pageDirection){
    console.log("Set Page Direction : " + pageDirection);
    this.setState({
      currPage : pageDirection
    }, () => this.callNotificationList());
  }

  setViolation(violationType){
    console.log("Set Violation : " + violationType);
    this.setState({
      violationType
    }, () => this.callNotificationList());
  }

  addToExport(notificationId){
    console.log("Notification ID : " + notificationId);
    var currentNotifToExport = this.state.notificationsToExport;
    currentNotifToExport.push(notificationId);

    this.setState({
      notificationsToExport : currentNotifToExport
    });
  }

  exportNotifications(){
    var thisComp = this;
    var userItem = JSON.parse(localStorage.getItem("userData"));
    var x2js = new X2JS();

    console.log("Exporting notifications");
    $.ajax({
      type: "GET",
      url: "rest/notification/export?spaceid=" + thisComp.props.spaceId+"&violation_type=" + thisComp.state.violationType,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success : function(data) {
        console.log("CSV Data : " + data);
        var hiddenElement = document.createElement('a');
        hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(data);
        hiddenElement.target = '_blank';
        hiddenElement.download = 'notifications.csv';
        hiddenElement.click();
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });
  }

  render(){
    var notificationList = this.state.notifications.map((notification, i) =>(
      <div class="card bg-info text-white" style={{ marginTop: "5px", marginRight : "5px"}}>
        <div class="card-body">
          <div class="row">
            <div class="col-2">
              <b>{notification.header}</b>
            </div>
            <div class="col">
              {notification.message}
            </div>
            <div class="col-2">
              {notification["violation-type"]}
            </div>
          </div>
        </div>
      </div>
    ));

    var curPage = this.state.currPage;
    var perPage = this.state.perPage;
    var totalNotifCount = this.state.totalNotifCount;
    var totalNotifCountPerPage = Math.floor(totalNotifCount / perPage);
    var maxPage = totalNotifCountPerPage == 0 ? 1 : totalNotifCountPerPage;
    maxPage = totalNotifCountPerPage * perPage == totalNotifCount || totalNotifCount < perPage ? maxPage : maxPage + 1;

    var paginationFirst = curPage > 1 ? curPage - 1 : 1;
    var paginationSecond = curPage == maxPage && maxPage > 2 ? curPage - 1 : curPage == 1 ? curPage + 1 : curPage;
    var paginationThird = curPage == maxPage ? curPage : curPage > 2 ? curPage + 1 : 3;

    var previousPage = curPage > 1 ? curPage - 1 : 1;
    var nextPage = curPage == maxPage ? curPage : curPage + 1;

    var firstPage = (
      <li className="page-item">
        <a className="page-link" onClick={ () => this.setPage(paginationFirst) }>
          {paginationFirst}
        </a>
      </li>
    );
    var secondPage =
      totalNotifCount < perPage ? (
        ""
      ) : (
        <li className="page-item">
          <a className="page-link" onClick={() => this.setPage(paginationFirst)}>
            {paginationSecond}
          </a>
        </li>
      );
    var thirdPage =
      totalNotifCount < perPage * 2 ? (
        ""
      ) : (
        <li className="page-item" className="hidden">
          <a className="page-link"  onClick={() => this.setPage(paginationFirst)}>
            {paginationThird}
          </a>
        </li>
      );

    return (
      <div class="col-md-4">
        <div>
          <ul className="nav nav-tabs">
            <li className="nav-item"><a className="nav-link active"><h6>Notifications({this.state.totalNotifCount})</h6></a></li>
          </ul>
          <div class="row">
            <div class="col-4">
              <button className="btn btn-secondary dropdown-toggle" type="button" id="notifDropDown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={{ marginTop: "10px" }}>
                Notifications per page ({this.state.perPage})
              </button>
              <div className="dropdown-menu" aria-labelledby="notifDropDown">
                <a className="dropdown-item" onClick={() => this.setPerPage(10)}>
                  10
                </a>
                <a className="dropdown-item" onClick={() => this.setPerPage(15)}>
                  15
                </a>
                <a className="dropdown-item" onClick={() => this.setPerPage(20)}>
                  20
                </a>
              </div>
            </div>
            <div class="col-4">
              <button className="btn btn-secondary" type="button" id="exportCsv" onClick={this.exportNotifications} style={{ marginLeft: "10px", marginTop: "10px" }}>
                Export as CSV
              </button>
            </div>
            <div class="col-1">
              <button className="btn btn-secondary dropdown-toggle" type="button" id="notifDropDown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style={{ marginTop: "10px" }}>
                Violation Type ({this.state.violationType})
              </button>
              <div className="dropdown-menu" aria-labelledby="notifDropDown">
                <a className="dropdown-item" onClick={() => this.setViolation("")}>
                  All
                </a>
                <a className="dropdown-item" onClick={() => this.setViolation("SLA")}>
                  SLA
                </a>
                <a className="dropdown-item" onClick={() => this.setViolation("Workflow")}>
                  Workflow
                </a>
              </div>
            </div>
          </div>
          <div>
            {notificationList}
          </div>
        </div>
        <nav aria-label="Page navigation" style={{ marginTop: "10px" }}>
          <ul className="pagination">
            <li className="page-item">
              <a className="page-link" onClick={() => this.setPage(previousPage)} aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span className="sr-only">Previous</span>
              </a>
            </li>
            {firstPage}
            {secondPage}
            {thirdPage}
            <li className="page-item">
              <a className="page-link" onClick={() => this.setPage(nextPage)} aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span className="sr-only">Next</span>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    );
  }
}
