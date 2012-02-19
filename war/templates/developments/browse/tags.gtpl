<% include '/templates/includes/header.gtpl' %>

<% def subdomain = request.properties.serverName.split(/\./).getAt(0) %>

	<div class="grid_2 redirect-block bordered">
		<h1>Tags</h1>
	</div>

	<div class="grid_9 directory bordered">
		<% request.tags?.sort().each { tag, count ->  %>
		<a href="/developments/tags/${tag}"><li>${tag}&nbsp;&nbsp;<span class="heading-count">(${count})</span></li></a>
		<% } %>
	</div>
	
<% include '/templates/includes/footer.gtpl' %>
