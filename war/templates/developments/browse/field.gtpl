<% include '/templates/includes/header.gtpl' %>

	<dir class="directory bordered right">
		<% request.browseStats?.sort({ k1, k2 -> k1.title <=> k2.title } as Comparator).each { key, stats -> %>
		<a href="/developments/${request.browseField}/${key}"><li>${key.title}&nbsp;&nbsp;<span class="heading-count">(${stats.count})</span></li></a>
		<% } %>
	</dir>
	
	<div class="redirect-block bordered">
		<h1>${request.browseField?.capitalize()}</h1>
	</div>
	

<% include '/templates/includes/footer.gtpl' %>