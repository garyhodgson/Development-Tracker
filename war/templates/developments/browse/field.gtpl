<% include '/templates/includes/header.gtpl' %>

	<dir class="directory bordered right">
		<% request.browseStats?.each { id, stats -> %>
		<a href="/developments/${request.browseField}/${id}"><li>${stats.title}&nbsp;&nbsp;<span class="heading-count">(${stats.count})</span></li></a>
		<% } %>
	</dir>
	
	<div class="redirect-block bordered">
		<h1>${request.browseField?.capitalize()}</h1>
	</div>
	

<% include '/templates/includes/footer.gtpl' %>