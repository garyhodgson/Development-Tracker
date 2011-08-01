
<% include '/templates/includes/htmlHeader.gtpl' %>

<% 	def hideAccess = ["/templates/access/login.gtpl", "/templates/access/first.gtpl"].contains(request.requestURI)
	def startLink = hideAccess?'/start/default':'/'
%>

<a href="<%=startLink%>"><img id="ohlogo" src="/images/OpenHardware.png" width="50"> </a>

<header>	
	<span id="title"><a href="<%=startLink%>">Development Tracker</a></span>
	
	<% if (!hideAccess) include '/templates/includes/access/access.gtpl' %>
</header>
	
<div class="container bordered">


<% session = session?:request.getSession(true) %>

	<% if (session.getAttribute('message') != null) { %>
	<div class="message"><%=session.getAttribute('message')?:''%></div>
	<% session.setAttribute('message',null) } %>
	




