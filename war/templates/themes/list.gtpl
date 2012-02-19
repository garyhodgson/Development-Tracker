<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Themes"%></h2>
</div>

<div class="grid_12">
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