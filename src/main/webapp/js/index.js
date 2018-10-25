class TicketItem extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      violationCount: 0,
      ticketChanges: []
    };
  }

  componentDidMount() {
    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);
    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/ticket/ticketChanges?space_id=" + this.props.spaceId + "&ticket_num=" + this.props.number,
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    }).done(function(data) {
      var ticketChangesJson = x2js.xml_str2json(data);
      const ticketChanges = ticketChangesJson["ticket-comments"]["ticket-comment"];

      if (ticketChanges || ticketChanges != null) {
        thisComp.setState({ ticketChanges });
      }
    });
  }

  render() {
    var ticketChanges = "";

    if (this.state.ticketChanges instanceof Array) {
      var ticketChangesArray = this.state.ticketChanges.map((ticketChange, i) => {
        var hasViolation = ticketChange["has-violation"];
        if (ticketChange["ticket-changes"].includes("--- []")) {
          return "";
        } else {
          var formattedTicketChanges = ticketChange["ticket-changes"].replace("--- ", "");
          var finalTicketChanges = "";
          var formattedTicketChangesArray = formattedTicketChanges.split("- - ");
          for (var i = 1; i < formattedTicketChangesArray.length; i++) {
            var ticketCommentItem = formattedTicketChangesArray[i].split("- ");
            finalTicketChanges += ">" + ticketCommentItem[0].replace("_id", "").replace("_", " ") + " : " + ticketCommentItem[1] + " => " + ticketCommentItem[2];
            if (i < formattedTicketChangesArray.length) {
              finalTicketChanges += "\n";
            }
          }

          return (
            <a href="#" className={"list-group-item " + (hasViolation == "true" ? "list-group-item-danger" : "")} style={{ marginLeft: "25px"  }}>
              {finalTicketChanges}
            </a>
          );
        }
      });

      ticketChanges = ticketChangesArray;
    } else {
      var ticketChange = this.state.ticketChanges;
      var hasViolation = ticketChange["has-violation"];
      if (ticketChange["ticket-changes"].includes("--- []")) {
        ticketChange = "";
      } else {
        var formattedTicketChanges = ticketChange["ticket-changes"].replace("--- ", "");
        var finalTicketChanges = "";
        var formattedTicketChangesArray = formattedTicketChanges.split("- - ");
        for (var i = 1; i < formattedTicketChangesArray.length; i++) {
          var ticketCommentItem = formattedTicketChangesArray[i].split("- ");
          finalTicketChanges += ticketCommentItem[0].replace("\n", "") + " : " + ticketCommentItem[1].replace("\n", "") + " => " + ticketCommentItem[2].replace("\n", "");
          if (i < formattedTicketChangesArray.length) {
            finalTicketChanges += "||";
          }
        }

        return (
          <a href="#" className={"list-group-item " + (hasViolation == "true" ? "list-group-item-danger" : "")}>
            {finalTicketChanges}
          </a>
        );

        ticketChanges = ticketChange;
      }
    }

    var ticketChangesFull = <ul className="list-group">{ticketChanges}</ul>;

    var listItems = (
      <div class="panel panel-default">
        <a class="list-group-item" data-toggle="collapse" href={"#collapse" + this.props.number}>
          {this.props.summary}
          {this.props.ticketType || this.props.ticketType != null ? "[" + this.props.ticketType + "]" : ""}
          <span class="badge">{this.props.number}</span>
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
    const listItems = this.props.tickets.map((ticket, i) => (
      <TicketItem id={ticket.id} summary={ticket.summary} number={ticket.number} spaceId={ticket["space-id"]} ticketType={ticket["custom-fields"].type} />
    ));
    return (
      <div id="ticketDiv" style={{ marginTop: "10px" }}>
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
      currentSpaceId: 0
    };

    this.updateTickets = this.updateTickets.bind(this);
  }

  componentDidMount() {
    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);
    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/space/list",
      headers: {
        "Content-Type": "application/xml",
        Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
      },
      dataType: "text",
      error: function(data) {
        console.log("Error : " + JSON.stringify(data));
      }
    }).done(function(data) {
      var spacesJson = x2js.xml_str2json(data);
      const spaces = spacesJson.spaces.space.map(obj => obj);
      thisComp.setState({ spaces });

      var currentSpaceId = getURLParameter("space_id");
      if (!currentSpaceId) {
        var currentSpaceId = thisComp.state.spaces[0].id;
      }
      thisComp.setState({ currentSpaceId });
      thisComp.updateTickets();
    });
  }

  updateTickets() {
    var thisComp = this;
    var userData = localStorage.getItem("userData");
    var userItem = JSON.parse(userData);
    var x2js = new X2JS();
    var perPage = Number(getURLParameter("per_page"));
    var curPage = Number(getURLParameter("cur_page"));

    if (!perPage || (perPage != 10 && perPage != 15 && perPage != 20)) {
      perPage = 10;
    }

    if (!curPage || curPage == null) {
      curPage = 1;
    }

    localStorage.setItem("itemPerPage", perPage);
    localStorage.setItem("curPage", curPage);

    if (thisComp.state.currentSpaceId) {
      var urlRequest = "rest/ticket/list?space_id=" + thisComp.state.currentSpaceId + "&per_page=" + perPage + "&page=" + curPage;

      $.ajax({
        type: "GET",
        url: urlRequest,
        headers: {
          "Content-Type": "application/xml",
          Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
        },
        dataType: "text",
        error: function(data) {
          console.log("Error : " + JSON.stringify(data));
        }
      }).done(function(data) {
        var ticketsJson = x2js.xml_str2json(data);
        const tickets = ticketsJson.tickets.ticket.map(obj => obj);

        var getTicketCountUrl = "rest/ticket/ticketCount?space_id=" + thisComp.state.currentSpaceId;

        $.ajax({
          type: "GET",
          url: getTicketCountUrl,
          headers: {
            "Content-Type": "application/xml",
            Authorization: "Basic " + Base64.encode(userItem.user.username + ":" + userItem.user.password)
          },
          dataType: "text",
          error: function(data) {
            console.log("Error : " + JSON.stringify(data));
          }
        }).done(function(data) {
          var ticketCount = x2js.xml_str2json(data);

          console.log("Update Ticket Count : " + ticketCount.spaceTicketCount.count);
          localStorage.setItem("totalTicketCount", ticketCount.spaceTicketCount.count);
          thisComp.setState({ tickets });
        });
      });
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
    window.location.href = "/assemblareader/login.html";
  }

  render() {
    var spaceList = this.state.spaces.map((space, i) => (
      //<li className="nav-item " key={space.id} onClick={this.updateSpace.bind(this, space.id)}>
      <li className="nav-item " key={space.id}>
        <a href={"/assemblareader/index.html?space_id=" + space.id} className={"nav-link " + (this.state.currentSpaceId === space.id ? "active" : "")}>
          {space.name}
        </a>
      </li>
    ));

    var displayPerPage = (
      <div className="dropdown">
        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Tickets per page
        </button>
        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <a className="dropdown-item" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=10"}>
            10
          </a>
          <a className="dropdown-item" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=15"}>
            15
          </a>
          <a className="dropdown-item" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=20"}>
            20
          </a>
        </div>
      </div>
    );

    var curPage = Number(localStorage.getItem("curPage"));
    var perPage = Number(localStorage.getItem("itemPerPage"));
    var totalTicketCount = Number(localStorage.getItem("totalTicketCount"));
    var totalTicketCountPerPage = totalTicketCount / perPage;
    var maxPage = totalTicketCountPerPage == 0 ? 1 : totalTicketCountPerPage;
    maxPage = totalTicketCountPerPage * perPage == totalTicketCount || totalTicketCount < perPage ? maxPage : maxPage + 1;

    var paginationFirst = curPage > 1 ? curPage - 1 : 1;
    var paginationSecond = curPage == maxPage && maxPage > 2 ? curPage - 1 : curPage == 1 ? curPage + 1 : curPage;
    var paginationThird = curPage == maxPage ? curPage : curPage > 2 ? curPage + 1 : 3;

    var previousPage = curPage > 1 ? curPage - 1 : 1;
    var nextPage = curPage == maxPage ? curPage : curPage + 1;

    var firstPage = (
      <li className="page-item">
        <a className="page-link" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=" + perPage + "&cur_page=" + paginationFirst}>
          {paginationFirst}
        </a>
      </li>
    );
    var secondPage =
      totalTicketCount < perPage ? (
        ""
      ) : (
        <li className="page-item">
          <a className="page-link" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=" + perPage + "&cur_page=" + paginationSecond}>
            {paginationSecond}
          </a>
        </li>
      );
    var thirdPage =
      totalTicketCount < perPage * 2 ? (
        ""
      ) : (
        <li className="page-item" className="hidden">
          <a className="page-link" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=" + perPage + "&cur_page=" + paginationThird}>
            {paginationThird}
          </a>
        </li>
      );

    var ticketPagination = (
      <nav aria-label="Page navigation example">
        <ul className="pagination">
          <li className="page-item">
            <a className="page-link" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=" + perPage + "&cur_page=" + previousPage} aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
              <span className="sr-only">Previous</span>
            </a>
          </li>
          {firstPage}
          {secondPage}
          {thirdPage}
          <li className="page-item">
            <a className="page-link" href={"/assemblareader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=" + perPage + "&cur_page=" + nextPage} aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
              <span className="sr-only">Next</span>
            </a>
          </li>
        </ul>
      </nav>
    );

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
            <ul className="navbar-nav mr-auto" />
            <span className="navbar-text">
              <a onClick={this.logout}>Logout</a>
            </span>
          </div>
        </nav>
        <div>
          <div>
            <ul className="nav nav-tabs">{spaceList}</ul>
          </div>

          {displayPerPage}

          <ul className="list-group">
            <div id="ticketDiv" style={{ marginTop: "10px" }}>
              <TicketList tickets={this.state.tickets} spaceId={this.state.currentSpaceId} />
            </div>
          </ul>
          {ticketPagination}
        </div>
      </div>
    );
  }
}

ReactDOM.render(React.createElement(SpaceList), document.getElementById("app"));
