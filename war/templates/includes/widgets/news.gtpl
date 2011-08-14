<% def subdomain = request.properties.serverName.split(/\./).getAt(0) 
 import widgets.newsFeed
	def feed = newsFeed.getNews(subdomain)
	if (feed) { %>
<div class="widget">
	<h3>Latest News</h3>
	<ul>
	<% feed.entries[0..3].each { entry ->%>
		<li><a target="_blank" href="${entry.uri}">${entry.title}</a></li>
	<% }%>
	</ul>
</div>
<% }%>