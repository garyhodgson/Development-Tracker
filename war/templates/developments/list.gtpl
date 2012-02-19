<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<div class="grid_10 prefix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Developments"%></h2>
</div>

<div class="grid_1 rss_link">
	<a href="/developments/latest/feed.rss"><img src="/images/rss.png" alt="RSS Feed" /></a>
</div>

<div class="grid_12">
	<table border=0 cellspacing="0" cellpadding="5px">
		<tr>
			<th width="30%">Title</th>
			<th width="50%">Description</th>
			<th width="10%">Source</th>
			<th width="10%">Status</th>
		</tr>
		<% request.developments?.each { development -> %>
		<tr>
			<td width="30%"><a href="/development/${development.id}">${development.title}</a></td>
			<td width="50%" class="list-smaller-text">${development.description? org.apache.commons.lang.StringUtils.abbreviate(development.description, 200) : ''}</td>

			<td width="10%" class="list-smaller-text">
			<% if (development.sourceURL) {%>
				<a href="${development.sourceURL}" title="${development.sourceURL}">${development.source?:''}</a>
			<% } else { %>
				${development.source?:''}
			<% } %>					
			</td>
			<% def status = development.status? development.status.title : '' %>
			<td width="10%" class="list-smaller-text">${status}</td>
		</tr>
		<% } %>
	</table>
	<% if (request.developments) include '/templates/includes/paging.gtpl' %>
</div>	

<% include '/templates/includes/footer.gtpl' %>