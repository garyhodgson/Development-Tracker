<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Setups"%></h2>
</div>

<div class="grid_12">
	<table border=0 cellspacing="0" cellpadding="5px">
		<tr>
			<th width="30%">Title</th>
			<th width="40%">Description</th>
			<th width="20%">Owner</th>
			<th width="10%">Parts</th>
		</tr>
		<% request.kits?.each { kit -> %>
		<tr>
			<td width="30%"><a href="/setup/${kit.id}">${kit.title}</a></td>
			<td width="40%" class="list-smaller-text">${kit.description? org.apache.commons.lang.StringUtils.abbreviate(kit.description, 200) : ''}</td>
			<td width="20%"><a href="/userinfo/${kit.ownerUsername}">${kit.ownerUsername}</a></td>
			<td width="10%" class="list-smaller-text">${kit.parts?.size()?:0}</td>
		</tr>
		<% } %>
	</table>
	<% if (request.themes) include '/templates/includes/paging.gtpl' %>	
</div>

<% include '/templates/includes/footer.gtpl' %>