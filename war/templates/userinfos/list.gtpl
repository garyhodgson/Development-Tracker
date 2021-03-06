<% include '/templates/includes/header.gtpl' %>


<div class="grid_10 prefix_1">
	<h2 class="pageTitle">User Infos</h2>
</div>

<div class="grid_12">

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
			<% if (users.isUserLoggedIn() && (users.isUserAdmin() || user.userId == userinfo.userId)) { %>
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
