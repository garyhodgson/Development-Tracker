<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Themes"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
		<br>
		<% if (users.isUserLoggedIn()) { %>
			<a href="/theme/add"><li>New Theme &hellip;</li></a>
			<br>
		<% } %>
		
		<a href="/themes/latest"><li>Latest</li></a>
		<br>
		<a href="/themes/latest/feed.rss"><li>RSS</li></a>
	</ul>
</nav>

<div class="content" >
		<table border=0 cellspacing="0" cellpadding="5px">
			<tr>
				<th width="90%">Title</th>
				<th width="10%">Developments</th>
			</tr>
			<% request.themes?.each { theme -> %>
			<tr>
				<td width="40%"><a href="/theme/${theme.id}">${theme.title}</a></td>
				<td width="10%" class="list-smaller-text">${theme.developmentIds?.size()?:0}</td>
			</tr>
			<% } %>
		</table>
		<% if (request.themes) include '/templates/includes/paging.gtpl' %>
		
</div>

<% include '/templates/includes/footer.gtpl' %>