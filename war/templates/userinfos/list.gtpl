<% include '/templates/includes/header.gtpl' %>

<h2 class="pageTitle">User Infos</h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
	
		<% if (users.isUserLoggedIn()) { %>
			<% if (users.isUserAdmin() || user.userId == userinfo.userId) { %>
				<a href="/userinfo/edit/<%=userinfo.username%>"><li>Edit</li></a>
			<% } %>	
		<% } %>	
			
	</ul>
</nav>

<div class="content">

	<% if (request.userinfos != null) { %>

	<table border=0 cellspacing="0" cellpadding="5px">
		<tr>
			<th>Username</th>
			<th>Registered</th>
			<% if (users.isUserLoggedIn()) { %>
			<th></th>
			<% } %>
		</tr>
		<% request.userinfos.each { userinfo -> %>
		<tr>
			<td><a href="/userinfo/${userinfo.username}">${userinfo.username}</a>
			</td>
			<td>${userinfo.created}</td>
			<% if (users.isUserLoggedIn() && (users.isUserAdmin() || users.currentUser.userId == userinfo.key.id)) { %>
			<td><input class="action" type="button" onclick="javascript:location='/userinfo/edit/<%=userinfo.username%>'"
				value="Edit"
			></td>
			<% } else { %>
			<td></td>
			<% } %>
			<% } %>
		</tr>

	</table>
	<% include '/templates/includes/paging.gtpl' %>
	<% } %>

</div>

<% include '/templates/includes/footer.gtpl' %>
