<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Tags"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li>
	</ul>
</nav>

<div class="content">
	<table border=0 cellspacing="0" cellpadding="5px" width=50%>
		<tr>
			<th>Name</th>
			<th># Developments</th>
		</tr>
		<% request.tags?.each { tag, count -> 
		%>
		<tr>
			<td><a href="/developments/tags/${tag}">${tag}</a></td>
			<td>${count}</td>
		</tr>
		<% } %>
	</table>
</div>



<% include '/templates/includes/footer.gtpl' %>
