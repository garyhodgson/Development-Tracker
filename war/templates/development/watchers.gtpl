<% 	def development = request.getAttribute('development') 
	def developmentWatchers = request.getAttribute('developmentWatchers') %>

<% include '/templates/includes/header.gtpl' %>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Development"%></h2>
</div>

<div class="grid_2">
	<div class="development-thumb">
	<% if (development.thumbnailServingUrl){ %>
		<a class="nohint" href="${development.thumbnailServingUrl}" target="_blank"><img src="${development.thumbnailServingUrl}"></a>
	<% } else { %>
		<p class="noimage" >No Image Available</p>
	<% } %>
	</div>
	
	<% if (development.signs){ %>	
		<%if (!development.thumbnailServingUrl){%>
		<% } %>
		<div class="signs">
			<% development.signs.each { sign ->%>
				<img src="/images/signs/50/${sign.image}" title="${sign.title}">
			<% } %>
		</div>
	<% } %>
	
	<% if (development.notice){ %>
		<div class="notice site-default-bg">${development.notice}</div>
	<% } %>
</div>		

<div class="grid_9">
	<% if (developmentWatchers?.isEmpty()){ %>
		<h3>Nobody is watching yet.</h3>
	<% } else { %>
	<table class="watchersTable">
		<% developmentWatchers.each { watcher -> %>
			<tr>
				<td><a class="nohint" href="/userinfo/${watcher.username}"><img src="http://www.gravatar.com/avatar/${watcher.getGravatarHash()}?s=50&d=mm" />
				<br>
				${watcher.username}
				</td>
			</tr>
		<% } %>
	</table>
	<% } %>	
</div>

<div id="actions" class="grid_1">
	<ul>
		<a href="/development/<%=development.id%>"><li>Back</li> </a> 
		<br>
		
		<% if (user) { %> 
			<% if ((session.userinfo?.watchedDevelopments?:[]).contains(development.id)) { %>
				<a href="/development/unwatch/<%=development.id%>"><li>Unwatch</li> </a> 
			<% } else { %> 
				<a href="/development/watch/<%=development.id%>"><li>Watch</li> </a> 
			<% } %> <br> 
			<%if (users.isUserAdmin() || session.userinfo?.username == development.createdBy) { %>
				<a href="/development/edit/<%=development.id%>"><li>Edit</li> </a>
			<% } %> 
		<% } %>		
	</ul>
</div>

<% include '/templates/includes/footer.gtpl' %>