function spaceTabs(props){

	var userData = sessionStorage.getItem("userData");
	$.ajax({
			type: "JSONP",
			url: "https://api.assembla.com/v1/spaces.json",
			dataType: "jsonp",
			jsonp: false,
			cache : true,
			headers: {
					"Authorization" : "Bearer "+ JSON.parse(userData).user.bearerToken
			},
			success: function(data){
					console.log("Success : " + JSON.stringify(data));
			},
			error: function(data){
					console.log("Error : " + JSON.stringify(data));
			}
	});

    return React.createElement('ul', {className : 'nav nav-tabs', 'id' : 'space_tabs'},
            React.createElement('li', {className : 'nav-item'}, React.createElement('a', { 'href' : '#' , className : 'nav-link active'}, 'Home') ),
            React.createElement('li', {className : 'nav-item'}, React.createElement('a', { 'href' : '#' , className : 'nav-link'}, 'Menu 1') ),
            React.createElement('li', {className : 'nav-item'}, React.createElement('a', { 'href' : '#' , className : 'nav-link'}, 'Menu 2') ),
            React.createElement('li', {className : 'nav-item'}, React.createElement('a', { 'href' : '#' , className : 'nav-link'}, 'Menu 1') )
        )
}

function indexPage(props){
	return React.createElement('div', {className : 'container-fluid', 'id' : 'spaces' },
        React.createElement('h3', {}, 'Spaces'),
        React.createElement(spaceTabs))
}

ReactDOM.render(React.createElement(indexPage),document.getElementById('app'));
