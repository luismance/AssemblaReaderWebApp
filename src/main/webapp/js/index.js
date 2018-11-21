class TicketItem extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      violationCount: 0,
      ticketChanges: []
    };

    this.componentDidMount = this.componentDidMount.bind(this);
    this.updateTicketChanges = this.updateTicketChanges.bind(this);
  }

  componentDidMount() {
    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);

    if(userItem == null){
      window.location.href = "login.html";
    }

    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/ticket/ticketChanges?space_id=" + this.props.spaceId + "&ticket_num=" + this.props.number,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success: function(data) {
        var ticketChangesJson = x2js.xml_str2json(data);
        const ticketChanges = ticketChangesJson["ticket-comments"]["ticket-comment"];
        if (ticketChanges || ticketChanges != null) {
          thisComp.setState({ ticketChanges });
        }
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });
  }

  updateTicketChanges(){
    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);

    if(userItem == null){
      window.location.href = "login.html";
    }

    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/ticket/ticketChanges?space_id=" + this.props.spaceId + "&ticket_num=" + this.props.number,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success: function(data) {
        var ticketChangesJson = x2js.xml_str2json(data);
        const ticketChanges = ticketChangesJson["ticket-comments"]["ticket-comment"];
        if (ticketChanges || ticketChanges != null) {
          thisComp.setState({ ticketChanges });
        }
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });
  }

  render() {
    var ticketChanges = "";
    var spanAsterisk = <span class='fa fa-asterisk' />;
    var spanArrow = <span class='fa fa-arrow-right' />;

    var ticketChangesArray = [];
    if (this.state.ticketChanges instanceof Array) {
      ticketChangesArray = this.state.ticketChanges;
    } else if (this.state.ticketChanges instanceof Object) {
      var ticketChange = this.state.ticketChanges;
      ticketChangesArray.push(ticketChange);
    }else{
      ticketChangesArray = [];
    }

    var ticketChangesArrayUI = ticketChangesArray.map((ticketChange, i) => {
      var hasViolation = ticketChange["has-violation"];
      if (ticketChange["ticket-changes"].includes("--- []")) {
        return "";
      } else {
        var formattedTicketChanges = ticketChange["ticket-changes"].replace("--- ", "");
        var ticketChangesHtml=document.createElement("p");
        var finalTicketChanges="";
        var formattedTicketChangesArray = formattedTicketChanges.split("- - ");
        for (var i = 1; i < formattedTicketChangesArray.length; i++) {
          var ticketCommentItem = formattedTicketChangesArray[i].split("- ");
          var tcProp = ticketCommentItem[0].replace("_id", "").replace("_", " ");
          var tcPrevValue = ticketCommentItem[1];
          var tcNewValue = ticketCommentItem[2];
          finalTicketChanges += tcProp + " : " + tcPrevValue + "=>" +tcNewValue;
          if (i < formattedTicketChangesArray.length) {
            finalTicketChanges += "\n";
          }
        }

        var updatedAtDate = new Date(ticketChange["updated-at"]);
        return (
          <a href="#" className={"list-group-item " + (hasViolation == "true" ? "list-group-item-danger" : "")} style={{ marginLeft: "25px"  }}>
            {finalTicketChanges}<span class="badge badge-danger">{(hasViolation == "true" ? ticketChange["violation-message"] : "")}</span>{updatedAtDate.toLocaleString("en-US")}
          </a>
        );
      }
    });

    ticketChanges = ticketChangesArrayUI;

    var ticketChangesFull = <ul className="list-group">{ticketChanges}</ul>;

    var priorityMessage = "";
    if(this.props.ticketObj["priority"] == 1){
      priorityMessage = "Highest";
    }else if(this.props.ticketObj["priority"] == 2){
      priorityMessage = "High";
    }else if(this.props.ticketObj["priority"] == 3){
      priorityMessage = "Normal";
    }else if(this.props.ticketObj["priority"] == 4){
      priorityMessage = "Low";
    }else if(this.props.ticketObj["priority"] == 5){
      priorityMessage = "Lowest";
    }

    var updatedAtDate = new Date(this.props.updatedAt);

    var listItems = (
      <div class="panel panel-default">
        <a class="list-group-item" data-toggle="collapse" href={"#collapse" + this.props.number} onClick={this.updateTicketChanges}>
          <div class="row">
            <div class="col-1">
              #{this.props.number}
              {this.props.ticketType || this.props.ticketType != null ? "[" + this.props.ticketType + "]" : ""}
            </div>
            <div class="col-4">
              {this.props.summary}
            </div>
            <div class="col-3">
              {this.props.ticketObj["custom-fields"].type}
            </div>
            <div class="col-1">
              {priorityMessage}
            </div>
            <div class="col-2">
              {updatedAtDate.toLocaleString("en-US")}
              {ticketChanges.length === 0 ? "" : <span class="oi oi-chevron-bottom" />}
            </div>
          </div>
        </a>
        <div class="panel-collapse collapse" id={"collapse" + this.props.number}>
          <div class="panel-body">{ticketChangesFull}</div>
        </div>
      </div>
    );

    return <div class="panel-group">{listItems}</div>;
  }
}

class TicketList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      spaceId: 0
    };
  }

  render() {
    var listItems = this.props.tickets.map((ticket, i) => (
      <TicketItem id={ticket.id} ticketObj={ticket} summary={ticket.summary} number={ticket.number} spaceId={ticket["space-id"]} ticketType={ticket["ticket-type"]} updatedAt={ticket["updated-at"]} />
    ));
    return (
      <div id="ticketDiv" style={{ margin : "10px" }}>
        {listItems}
      </div>
    );
  }
}

class SpaceList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      spaces: [],
      tickets: [],
      currentSpaceId: 0,
      sortBy:'',
      violationFilter:'',
      priorityFilter:'',
      ticketCount:10,
      currentPage:1,
      totalTicketCount:0
    };

    this.componentDidMount = this.componentDidMount.bind(this);
    this.updateTickets = this.updateTickets.bind(this);

  }

  componentDidMount() {

    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);

    if(userItem == null){
      window.location.href = "login.html";
    }

    var thisComp = this;
    var userItem = JSON.parse(localStorage.getItem("userData"));
    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/space/list",
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      success : function(data) {

        var spacesJson = x2js.xml_str2json(data);
        const spaces = spacesJson.spaces.space.map(obj => obj);
        thisComp.setState({ spaces });

        var currentSpaceId = getURLParameter("space_id");
        if (!currentSpaceId) {
          currentSpaceId = spaces[0].id;
        }
        thisComp.setState({ currentSpaceId });
        thisComp.updateTickets();
      },
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    });
  }

  sortTickets(sortBy){
    if(this.state.sortBy.includes("-desc")){
      sortBy = sortBy + "-asc";
    }else{
      sortBy = sortBy + "-desc";
    }
    console.log("Sort Tickets By : " + sortBy);
    this.setState({
      sortBy
    }, () => this.updateTickets());
  }

  filterPriority(priorityLevel){
    console.log("priority filter : " + priorityLevel);
    if(priorityLevel == 0){
      priorityLevel = "";
    }
    this.setState({
      priorityFilter : priorityLevel,
      currentPage : 1
    }, () => this.updateTickets());
  }

  updateTicketCount(ticketCount){
    console.log("ticket count : " + ticketCount);
    this.setState({
      ticketCount,
      currentPage : 1
    }, () => this.updateTickets());
  }

  updateCurrentPage(currentPage){
    console.log("current page : " + currentPage);
    this.setState({
      currentPage
    }, () => this.updateTickets());
  }

  updateTickets() {

    $("[aria-expanded=true]").click();

    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);
    var x2js = new X2JS();

    if (thisComp.state.currentSpaceId) {
      var urlRequest = "rest/ticket/list?space_id=" + thisComp.state.currentSpaceId + "&per_page=" + this.state.ticketCount + "&page=" + this.state.currentPage + "&sort_by=" + this.state.sortBy+"&violation_type=" + this.state.violationFilter+"&priority=" + this.state.priorityFilter;

      $.ajax({
        type: "GET",
        url: urlRequest,
        headers: {
          "Content-Type": "application/xml",
          Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
        },
        dataType: "text",
        success : function(data) {
          var ticketsJson = x2js.xml_str2json(data);
          if (ticketsJson.tickets === undefined || ticketsJson.tickets.length == 0) {
            thisComp.setState({
              tickets : [],
              totalTicketCount : 0
            });
          }else{
            const tickets = ticketsJson.tickets.ticket.map(obj => obj);
            var getTicketCountUrl = "rest/ticket/ticketCount?space_id=" + thisComp.state.currentSpaceId + "&violation_type=" + thisComp.state.violationFilter + "&priority=" + thisComp.state.priorityFilter;
            $.ajax({
              type: "GET",
              url: getTicketCountUrl,
              headers: {
                "Content-Type": "application/xml",
                Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
              },
              dataType: "text",
              success : function(data) {
                var ticketCount = x2js.xml_str2json(data);

                console.log("Update Ticket Count : " + ticketCount.spaceTicketCount.count);

                thisComp.setState({
                  tickets,
                  totalTicketCount : ticketCount.spaceTicketCount.count
                });
              },
              error: function(data) {
                console.log("Error : " + JSON.stringify(data));
              }
            });
          }
        },
        error: function(data) {
          console.log("Error : " + JSON.stringify(data));
        }
      }).done();
    } else {
      console.log("Current Space ID Undefined!");
    }
  }

  updateSpace(currentSpaceId) {
    this.setState({ currentSpaceId });
    this.updateTickets();
  }

  logout() {
    localStorage.clear();
    window.location.href = "login.html";
  }

  render() {
    var spaceList = this.state.spaces.map((space, i) => (
      <li className="nav-item " key={space.id}>
        <a href={"index.html?space_id=" + space.id} className={"nav-link " + (this.state.currentSpaceId === space.id ? "active" : "")}>
          {space.name}
        </a>
      </li>
    ));

    var totalTicketCountPerPage = Math.floor(this.state.totalTicketCount / this.state.ticketCount);
    var maxPage = totalTicketCountPerPage == 0 ? 1 : totalTicketCountPerPage;
    maxPage = totalTicketCountPerPage * this.state.ticketCount == this.state.totalTicketCount || this.state.totalTicketCount < this.state.ticketCount ? maxPage : maxPage + 1;

    var paginationFirst = this.state.currentPage > 1 ? this.state.currentPage - 1 : 1;
    var paginationSecond = this.state.currentPage == maxPage && maxPage > 2 ? this.state.currentPage - 1 : this.state.currentPage == 1 ? this.state.currentPage + 1 : this.state.currentPage;
    var paginationThird = this.state.currentPage == maxPage ? this.state.currentPage : this.state.currentPage > 2 ? this.state.currentPage + 1 : 3;

    var previousPage = this.state.currentPage > 1 ? this.state.currentPage - 1 : 1;
    var nextPage = this.state.currentPage == maxPage ? this.state.currentPage : this.state.currentPage + 1;

    var firstPage = (
      <li className="page-item">
        <a className="page-link" onClick={() => this.updateCurrentPage(paginationFirst)}>
          {paginationFirst}
        </a>
      </li>
    );
    var secondPage =
      this.state.totalTicketCount < this.state.ticketCount ? (
        ""
      ) : (
        <li className="page-item">
          <a className="page-link" onClick={() => this.updateCurrentPage(paginationSecond)}>
            {paginationSecond}
          </a>
        </li>
      );
    var thirdPage =
      this.state.totalTicketCount < this.state.ticketCount * 2 ? (
        ""
      ) : (
        <li className="page-item" className="hidden">
          <a className="page-link" onClick={() => this.updateCurrentPage(paginationThird)}>
            {paginationThird}
          </a>
        </li>
      );

    var sortByMap = new Map();
    sortByMap.set("last_updated", "Last Updated");
    sortByMap.set("milestone", "Milestone");
    sortByMap.set("ticket_num", "Ticket #");
    sortByMap.set("priority", "Priority");

    var ticketFilters = (
      <div className="row" style={{ margin: "10px"}}>
        <div className="dropdown">
          <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Tickets per page ({this.state.ticketCount})
          </button>
          <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a className="dropdown-item" onClick={() => this.updateTicketCount(10)}>
              10
            </a>
            <a className="dropdown-item" onClick={() => this.updateTicketCount(15)}>
              15
            </a>
            <a className="dropdown-item" onClick={() => this.updateTicketCount(20)}>
              20
            </a>
          </div>
        </div>
        <div className="dropdown"  style={{ marginLeft: "10px"}}>
          <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Sort By ({sortByMap.get(this.state.sortBy.split("-")[0])})
          </button>
          <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a className="dropdown-item" onClick={() => this.sortTickets("last_updated")}>
              Last Updated
            </a>
            <a className="dropdown-item" onClick={() => this.sortTickets("milestone")}>
              Milestone
            </a>
            <a className="dropdown-item" onClick={() => this.sortTickets("ticket_num")}>
              Ticket #
            </a>
            <a className="dropdown-item" onClick={() => this.sortTickets("priority")}>
              Priority
            </a>
          </div>
        </div>
        <div className="dropdown"  style={{ marginLeft: "10px"}}>
          <button className="btn btn-secondary dropdown-toggle" type="button" id="ddViolationType" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Priority({this.state.priorityFilter})
          </button>
          <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a className="dropdown-item" onClick={() => this.filterPriority(0)}>
              None
            </a>
            <a className="dropdown-item" onClick={() => this.filterPriority(1)}>
              Highest
            </a>
            <a className="dropdown-item" onClick={() => this.filterPriority(2)}>
              High
            </a>
            <a className="dropdown-item" onClick={() => this.filterPriority(3)}>
              Normal
            </a>
            <a className="dropdown-item" onClick={() => this.filterPriority(4)}>
              Low
            </a>
            <a className="dropdown-item" onClick={() => this.filterPriority(5)}>
              Lowest
            </a>
          </div>
        </div>
      </div>
    );
    var noSpacesMessage = (
      <div id="noAccess"class="panel panel-danger" style={{ margin : "10px"}}>
        <a class="list-group-item" data-toggle="collapse">
          <div class="panel-heading">Cannot access spaces!</div>
        </a>
      </div>
    );

    var noAccess = (
      <div>
        {ticketFilters}
        <div id="noAccess"class="panel panel-danger" style={{ margin : "10px"}}>
          <a class="list-group-item" data-toggle="collapse">
            <div class="panel-heading">No tickets to display</div>
          </a>
        </div>
      </div>
    );

    var ticketDisplay =(
      <div>
        {ticketFilters}
        <div>
          <ul className="list-group">
            <div id="ticketDiv">
              <TicketList tickets={this.state.tickets} spaceId={this.state.currentSpaceId} />
            </div>
          </ul>
        </div>
        <div style={{ margin: "10px"}}>
          <nav aria-label="Page navigation">
            <ul className="pagination">
              <li className="page-item">
                <a className="page-link" onClick={() => this.updateCurrentPage(previousPage)} aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                  <span className="sr-only">Previous</span>
                </a>
              </li>
              {firstPage}
              {secondPage}
              {thirdPage}
              <li className="page-item">
                <a className="page-link" onClick={() => this.updateCurrentPage(nextPage)} aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                  <span className="sr-only">Next</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    );
    var currentSpaceId = getURLParameter("space_id");

    if (!currentSpaceId && this.state.spaces.length > 0) {
      currentSpaceId = this.state.spaces[0].id;
    }

    return (
      <div>
        <NavBar />
        <div>
          <div class="row">
            <div class="col-md-8">
              <div>
                <div>
                  <ul className="nav nav-tabs">{spaceList}</ul>
                </div>
                <div className="panel-group">
                  {this.state.spaces.length === 0 ? noSpacesMessage : (this.state.tickets.length === 0 ? noAccess : ticketDisplay)}
                </div>
              </div>
            </div>
            <NotificationList spaceId={currentSpaceId}/>
          </div>
        </div>
      </div>
    );
  }
}

ReactDOM.render(React.createElement(SpaceList), document.getElementById("app"));
