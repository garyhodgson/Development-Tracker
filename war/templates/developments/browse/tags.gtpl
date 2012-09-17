<% include '/templates/includes/header.gtpl' %>

<% def subdomain = request.properties.serverName.split(/\./).getAt(0) %>

	<div class="grid_2 redirect-block bordered">
		<h1 class="responsive_headline">Tags</h1>
	</div>
	
	<div class="grid_9">
		<div class="directory bordered">
			<% request.tags?.sort({it.key.toLowerCase()}).each { tag, count ->  %>
			<a class="nohintatall" href="/developments/tags/${tag}"><li class="hover-link" style="display: inline-block;">${tag}&nbsp;&nbsp;<span class="heading-count">(${count})</span></li></a>
			<% } %>
		</div>
	</div>
	
<% include '/templates/includes/footer.gtpl' %>
