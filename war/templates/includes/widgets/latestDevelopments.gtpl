<% def subdomain = request.properties.serverName.split(/\./).getAt(0) 
 import widgets.latestDevelopments
	def list = latestDevelopments.getLatest(subdomain)
	if (list) { %>
<div class="widget">
	<h3>Latest Developments</h3>
	<ul>
	<% list.each { dev ->%>
		<li><a href="/development/${dev.id}">${dev.title}</a></li>
	<% }%>
	</ul>
</div>
<% }%>