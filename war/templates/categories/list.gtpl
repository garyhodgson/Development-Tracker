
<% include '/templates/includes/header.gtpl' %>

<script>
jQuery(function() {	
	jQuery("table tr:even").addClass("oddrow");
});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Categories"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
	</ul>
</nav>

<div class="content">
	<table border=0 cellspacing="0" cellpadding="5px" width=50%>
		<tr>
			<th>Name</th>
			<th>Developments</th>
		</tr>
		<% request.categoryStats?.each { id, stats ->
		%>
		<tr>
			<td><a href="/developments/categories/${id}">${stats.title}</a>
			</td>
			<td>${stats.count}</td>
		</tr>
		<% } %>
	</table>
</div>

<% include '/templates/includes/footer.gtpl' %>