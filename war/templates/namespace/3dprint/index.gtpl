<% include '/templates/includes/header.gtpl' %>

	<dir class="directory bordered right">
		<a href="/developments"><li>Developments</li></a>
		<a href="/categories"><li>Categories</li></a>
		<a href="/tags"><li>Tags</li></a>
		<a href="/activities"><li>Recent Activities</li></a>
		
		<%if (users.isUserLoggedIn() && session.userinfo?.username){ %>
		<br>
		<a href="/userinfo/${session.userinfo.username}"><li>My Development Tracker</li></a>
		<% } %>
	</dir>

	<div class="redirect-block bordered">
		<%def subdomain = request.properties.serverName.split(/\./).getAt(0) %>
		<h1><%=(subdomain=="reprap")?"RepRap&hellip;":"${subdomain.capitalize()}&hellip;"%></h1>
	</div>
	
	<br>

	<div class="">
	<%if (subdomain=="reprap") {%>
		<img width="26%" alt="Prusa Mendel" src="/images/PrusaMendel_Mono.png">
	<% } else if (subdomain=="makerbot") {%>
		<img width="26%" alt="ThingOMatic" src="/images/ThingOMatic.png">
	<% } %>
	</div>

	<br clear="both">

<% include '/templates/includes/footer.gtpl' %>
