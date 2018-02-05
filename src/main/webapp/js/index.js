function getURLParameter(sParam)
{
  var sPageURL = window.location.search.substring(1);
  var sURLVariables = sPageURL.split('&');
  for (var i = 0; i < sURLVariables.length; i++)
  {
    var sParameterName = sURLVariables[i].split('=');
    if (sParameterName[0] == sParam)
    {
      return sParameterName[1];
    }
  }
}

var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};


class TicketList extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    const listItems = this.props.tickets.map((ticket, i) => <li className="list-group-item d-flex justify-content-between align-items-center" key={ticket.id}>{ticket.summary}<span className="badge badge-primary badge-pill">{ ticket.number }</span></li> );
    return ( <ul className="list-group"><div id="ticketDiv" style={{marginTop: '10px'}}>{listItems}</div></ul> );
  }

}

class SpaceList extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      spaces : [],
      tickets : [],
      currentSpaceId : 0
    };

    this.updateTickets = this.updateTickets.bind(this);
  }

  componentDidMount() {

    var thisComp = this;

    var userData = sessionStorage.getItem("userData");

    var userItem = JSON.parse(userData);

    var x2js = new X2JS();

    $.ajax({
      type: "GET",
      url: "rest/space/list",
      headers: {
        "Content-Type" : "application/xml",
        "Authorization" : "Basic "+ Base64.encode(userItem.user.username +":"+userItem.user.password)
      },
      dataType: 'text',
      error: function(data){
        console.log("Error : " + JSON.stringify(data));
      }
    }).done(function(data){
      var spacesJson = x2js.xml_str2json(data);
      const spaces = spacesJson.spaces.space.map(obj => obj);
      thisComp.setState({ spaces });

      var currentSpaceId = getURLParameter('space_id');
      console.log("Current Space ID : "+ currentSpaceId);
      if(!currentSpaceId){
      var currentSpaceId = thisComp.state.spaces[0].id;
      }


      thisComp.setState({ currentSpaceId });
      thisComp.updateTickets();
    });
  }

  updateTickets(){

    var thisComp = this;

    var userData = sessionStorage.getItem("userData");

    var userItem = JSON.parse(userData);

    var x2js = new X2JS();

    var perPage = getURLParameter('per_page');

    if(!perPage || (perPage != 10 && perPage != 15 && perPage != 20)){
      perPage = 10;
    }
    if(thisComp.state.currentSpaceId){
      var urlRequest = "rest/ticket/list?space_id="+ thisComp.state.currentSpaceId + "&per_page=" + perPage;

      $.ajax({
        type: "GET",
        url: urlRequest,
        headers: {
          "Content-Type" : "application/xml",
          "Authorization" : "Basic "+ Base64.encode(userItem.user.username +":"+userItem.user.password)
        },
        dataType: 'text',
        error: function(data){
          console.log("Error : " + JSON.stringify(data));
        }
      }).done(function(data){
        console.log("Update Tickets");
        var ticketsJson = x2js.xml_str2json(data);
        const tickets = ticketsJson.tickets.ticket.map(obj => obj);
        thisComp.setState({ tickets });
      });
    }else{
      console.log("Current Space ID Undefined!");
    }
  }

  updateSpace(currentSpaceId){
    console.log("Selected Space");
    this.setState({currentSpaceId});
    this.updateTickets();
  }

  render() {
    var spaceList = this.state.spaces.map(
      (space, i) =>
      //<li className="nav-item " key={space.id} onClick={this.updateSpace.bind(this, space.id)}>
      <li className="nav-item " key={space.id}>
      <a  href={"/AssemblaReader/index.html?space_id=" + space.id} className={"nav-link " + (this.state.currentSpaceId === space.id ? 'active' : '')}>
      { space.name }
      </a>
      </li> );

      var displayPerPage =
      <div className="dropdown">
        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Tickets per page
        </button>
        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <a className="dropdown-item" href={"/AssemblaReader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=10"}>10</a>
          <a className="dropdown-item" href={"/AssemblaReader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=15"}>15</a>
          <a className="dropdown-item" href={"/AssemblaReader/index.html?space_id=" + this.state.currentSpaceId + "&per_page=20"}>20</a>
        </div>
      </div>;

      var ticketList = this.state.tickets.map(
        (ticket, i) =>
        <li className="list-group-item d-flex justify-content-between align-items-center" key={ticket.id}>
        { ticket.summary }
        <span className="badge badge-primary badge-pill">
        { ticket.number }
        </span>
        </li> );

        var ticketPagination =
        <nav aria-label="Page navigation example">
          <ul className="pagination">
            <li className="page-item">
              <a className="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span className="sr-only">Previous</span>
              </a>
            </li>
            <li className="page-item"><a className="page-link" href="#">1</a></li>
            <li className="page-item"><a className="page-link" href="#">2</a></li>
            <li className="page-item"><a className="page-link" href="#">3</a></li>
            <li className="page-item">
              <a className="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span className="sr-only">Next</span>
              </a>
            </li>
          </ul>
        </nav>;



        return (
          <div>
            <div>
              <ul className="nav nav-tabs">{spaceList}</ul>
            </div>

            {displayPerPage}

            <ul className="list-group">
              <div id="ticketDiv" style={{marginTop: '10px'}}>
                {ticketList}
              </div>
            </ul>

            {ticketPagination}
          </div>
        );
      }

    }



    function indexPage(props){
      return React.createElement('div', {className : 'container-fluid', 'id' : 'spaces' },
      React.createElement('h3', {}, 'Spaces'),
      React.createElement(SpaceList));
    }

    ReactDOM.render(React.createElement(indexPage),document.getElementById('app'));
