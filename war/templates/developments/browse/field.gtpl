<% include '/templates/includes/header.gtpl' %>

	<div class="grid_2 redirect-block bordered">
		<h1 class="responsive_headline">${request.browseFieldTitle}</h1>
	</div>
	
	<div class="grid_9">
		<div class="directory bordered">
			<% request.browseStats?.sort({ k1, k2 -> k1.title <=> k2.title } as Comparator).each { key, stats -> %>
			<a href="/developments/${request.browseField}/${key}"><li>${key.title}&nbsp;&nbsp;<span class="heading-count">(${stats.count})</span></li></a>
			<% } %>
		</div>
	</div>
	
<% include '/templates/includes/footer.gtpl' %>