<% include '/templates/includes/header.gtpl' %>

<% def subdomain = request.properties.serverName.split(/\./).getAt(0) %>

	<dir class="directory bordered right">
		<% request.tags?.each { tag, count ->  %>
		<a href="/developments/tags/${tag}"><li>${tag}&nbsp;&nbsp;<span class="heading-count">(${count})</span></li></a>
		<% } %>
	</dir>
	
	<div class="redirect-block bordered">
		<h1>Tags</h1>
	</div>
	
<% include '/templates/includes/footer.gtpl' %>
