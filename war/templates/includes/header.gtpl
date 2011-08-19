
<% 	include '/templates/includes/htmlHeader.gtpl' 
	def hideAccess = ["/templates/access/login.gtpl", "/templates/access/first.gtpl"].contains(request.requestURI)
%>

<a href="/"><img id="dtlogo" src="/images/DTLogo-50px.png"> </a>

<header>	
	<span id="title"><a href="/">Development Tracker</a></span>
	
	<% if (!hideAccess) include '/templates/includes/access/access.gtpl' %>
</header>
	
<div class="container bordered">

	<% if (request.session.getAttribute('message') != null) { %>
	<div class="message"><%=request.session.getAttribute('message')?:''%></div>
	<% request.session.setAttribute('message',null) } %>
	