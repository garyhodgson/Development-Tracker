<% include '/templates/includes/header.gtpl' %>

<% def subdomain = request.properties.serverName.split(/\./).getAt(0) %>

	<div class="grid_2 redirect-block bordered">
		<h1>Browse</h1>
	</div>

	<div class="grid_9 directory bordered">
		<a href="/developments/browse/categories"><li>Categories</li></a>
		<a href="/developments/browse/tags"><li>Tags</li></a>
		<a href="/developments/browse/status"><li>Status</li></a>
		<a href="/developments/browse/goals"><li>Goals</li></a>
		<a href="/developments/browse/projectVendor"><li>Project/Vendor</li></a>
		<a href="/developments/browse/developmentType"><li>Development Type</li></a>
		<a href="/developments/browse/source"><li>Source</li></a>
		<a href="/developments/browse/license"><li>License</li></a>		
	</div>
	
<% include '/templates/includes/footer.gtpl' %>
