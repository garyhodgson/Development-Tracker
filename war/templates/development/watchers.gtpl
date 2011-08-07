<% 	def development = request.getAttribute('development') 
	def developmentWatchers = request.getAttribute('developmentWatchers') %>

<% include '/templates/includes/header.gtpl' %>

<h2 class="pageTitle"><%=request.pageTitle?:"Development"%></h2>

<nav>
	<ul>
		<a href="/developments"><li>Developments</li> </a>
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
</nav>

<% if (development.imageURL){ %>
<div class="development-thumb left">
	<a class="nohint" href="/development/${development.id}"><img src="${development.imageURL}"></a>
</div>
<% } %>

<div class="content ${(development.imageURL)?'thumbnailed':'' }"> 

	<% if (developmentWatchers.size == 0){ %>
		<h3>Nobody watching yet.</h3>
	<% } else { %>
	<table>
		<% developmentWatchers.each { watcher -> %>
			<tr>
				<td><a class="nohint" href="/userinfo/${watcher.username}"><img src="http://www.gravatar.com/avatar/${watcher.getGravatarHash()}?s=50&d=mm" /></a>
				<a href="/userinfo/${watcher.username}">${watcher.username}</a>
				</td>
			</tr>
		<% } %>
	</table>
	<% } %>

</div>

<% include '/templates/includes/footer.gtpl' %>